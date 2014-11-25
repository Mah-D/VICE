/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Dec 26, 2006
 */
package vpc.tir.opt.rma;

import vpc.core.*;
import vpc.core.base.Function;
import vpc.core.csr.CSRGen;
import vpc.core.decl.Field;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.core.virgil.*;
import vpc.tir.*;
import static vpc.tir.TIRUtil.$VAL;
import static vpc.tir.TIRUtil.getRep;
import vpc.tir.opt.DepthFirstTransformer;
import vpc.util.Ovid;

import java.util.Collection;

/**
 * The <code>RMTransformer</code> definition.
 *
 * @author Ben L. Titzer
 */
public class RMTransformer {

    protected final RMResults rma;

    public RMTransformer(RMResults r) {
        rma = r;
    }

    protected void transform() {
        Program p = rma.getProgram();
        ProgramDecl pd = p.programDecl;
        if ( pd != null ) {
            // only transform the program if the declaration exists
            Closure oc = p.closure;
            Closure nc = p.closure.copy();
            nc.resetLayouts(); // clear the set of layouts
            nc.methodFamilies = Ovid.newSet();
            p.heap.clearRoots(); // clear the root set of the heap
            // layouts: remove fields (unread), (unwritten+const)
            for (VirgilComponent cd : p.closure.getComponents()) transformComponent(cd, nc);
            for (VirgilClass cd : p.closure.getClasses()) transformClass(cd, nc);
            // heap: rebuild with new layout
            for (Heap.Record r : rma.getLiveRecords()) transformRecord(r, oc, nc);
            // closure: reset the live methods
            nc.methods = rma.getLiveMethods();
            for (Method m: nc.methods) transformMethod(m, nc);
            p.closure = nc;
        }
    }

    public void transformMethod(Method m, Closure nc) {
        TIRRep mrep = getRep(m);
        Transformer transformer = new Transformer(nc);
        TIRExpr nbody = transformer.transform(mrep.getBody(), null);
        mrep.setBody(nbody);
    }

    protected void transformComponent(VirgilComponent cd, Closure nc) {
        Heap.Record rec = cd.getRecord();
        if ( rma.getLiveRecords().contains(rec) )
            rma.getHeap().addRoot(rec);

        if (!nc.componentLayouts.containsKey(cd)) {
            Type type = cd.getCanonicalType();
            Heap.Layout nl = rma.getHeap().newLayout(cd.getName(), type);
            addFields(rma.getFields(type), nl);
            nc.componentLayouts.put(cd, nl);
        }
    }

    protected void transformClass(VirgilClass cd, Closure nc) {
        if (!nc.classLayouts.containsKey(cd)) {
            Heap.Layout nl = rma.getHeap().newLayout(cd.getName(), cd.getCanonicalType());
            for ( VirgilClass c : nc.hierarchy.getChain(cd) )
                addFields(rma.getFields(c.getCanonicalType()), nl);
            nc.classLayouts.put(cd, nl);
        }
    }

    protected void addFields(Collection<Field> fields, Heap.Layout layout) {
        if (fields == null) return;
        for (Field f : fields) {
            if (!rma.isConstantField(f)) {
                // TODO: this search is linear, could result in quadratic time
                if (layout.findCell(f) == null) {
                    // if the cell is not already in the layout, add it
                    f.fieldIndex = layout.addCell(f);
                }
            }
        }
    }

    protected void transformRecord(Heap.Record r, Closure oc, Closure nc) {
        Type type = r.getType();
        if (V2TypeSystem.isClass(type)) {
            VirgilClass cd = VirgilClass.declOf(type);
            Heap.rebuildRecord(r, oc.getLayout(cd), nc.getLayout(cd));
        } else if (V2TypeSystem.isComponent(type)) {
            VirgilComponent cd = VirgilComponent.declOf(type);
            Heap.rebuildRecord(r, oc.getLayout(cd), nc.getLayout(cd));
        }
    }

    protected class Transformer extends DepthFirstTransformer<Object> {

        protected final Closure closure;

        protected Transformer(Closure cl) {
            closure = cl;
        }

        public TIRExpr visit(TIROperator o, Object env) {

            switch ( getOpcode(o) ) {
                case VirgilOp.CLASS_GETMETHOD: {
                    VirgilClass.GetMethod gm = (VirgilClass.GetMethod)o.operator;
                    if (rma.isConstantMethod(gm.method)) {
                        // devirtualize the call
                        Function.Val val = (Function.Val) rma.getConstantMethodValue(gm.method);
                        VirgilClass.GetMethod ngm = new VirgilClass.GetMethod(gm.thisType, val.method, false, gm.newTypeEnv);
                        return TIRUtil.copy(o, new TIROperator(ngm, transform(o.operands, env)));
                    } else {
                        assert gm.method.family != null;
                        closure.methodFamilies.add(gm.method.family);
                    }
                    break; // revert to default behavior
                }
                case VirgilOp.CLASS_GETFIELD: {
                    VirgilClass.GetField gf = (VirgilClass.GetField)o.operator;
                    if (rma.isConstantField(gf.field)) {
                        // return an inlined constant
                        return newValue(o, rma.getConstantFieldValue(gf.field));
                    }
                    break; // revert to default behavior
                }
                case VirgilOp.CLASS_SETFIELD: {
                    VirgilClass.SetField gf = (VirgilClass.SetField)o.operator;
                    if (rma.isWriteOnly(gf.field)) {
                        // ignore the write, but return the value
                        return transform(o.operands[1], env);
                    }
                    break; // revert to default behavior
                }
                case VirgilOp.COMPONENT_GETFIELD: {
                    VirgilComponent.GetField gf = (VirgilComponent.GetField)o.operator;
                    if (rma.isConstantField(gf.field)) {
                        // return an inlined constant
                        return newValue(o, rma.getConstantFieldValue(gf.field));
                    }
                    break; // revert to default behavior
                }
                case VirgilOp.COMPONENT_SETFIELD: {
                    VirgilComponent.SetField gf = (VirgilComponent.SetField)o.operator;
                    if ( rma.isWriteOnly(gf.field) ) {
                        // ignore the write, but return the value
                        return transform(o.operands[0], env);
                    }
                    break; // revert to default behavior
                }
            }
            return super.visit(o, env);
        }

        public TIRExpr visit(TIRConst.Value v, Object env) {
            return newValue(v, v.getValue());
        }

        private TIRExpr newValue(TIRExpr o, Value val) {
            TIRConst.Value value = $VAL(val);
            if ( val instanceof Heap.Record )
                rma.getHeap().addRoot((Heap.Record)val);
            else if ( val instanceof VirgilDelegate.Val )
                rma.getHeap().addRoot(VirgilDelegate.fromValue(val).record);
            value.setType(o.getType());
            value.setSourcePoint(o.getSourcePoint());
            return value;
        }

        private int getOpcode(TIROperator to) {
            Integer val = CSRGen.opMap.get(to.operator.getClass());
            if ( val == null ) return -1;
            return val;
        }
    }
}
