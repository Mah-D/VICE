/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Oct 7, 2007
 */
package vpc.tir.opt.rma;

import vpc.core.*;
import vpc.core.base.Function;
import vpc.core.base.Reference;
import vpc.core.decl.Field;
import vpc.core.decl.Method;
import vpc.core.sets.ConstraintSystem;
import vpc.core.types.Type;
import vpc.core.virgil.*;
import vpc.tir.*;
import vpc.tir.expr.Operator;
import vpc.tir.opt.DepthFirstOpVisitor;
import vpc.util.Ovid;

import java.util.*;

/**
 * The <code>RMAnalyzer</code> class implements Reachable Members Analysis (RMA),
 * which is similar to Rapid Type Analysis, but instead of reachable types,
 * this analysis additionally computes which members of which components and
 * classes are accessible.
 *
 * @author Ben L. Titzer
 */
public class RMSetAnalyzer implements RMResults {

    protected boolean debug;

    protected ConstraintSystem system = new ConstraintSystem();
    protected ConstraintSystem.ESet liveMethods = system.newSet("methods");
    protected ConstraintSystem.ESet liveRecords  = system.newSet("records");
    protected ConstraintSystem.ESet writtenFields  = system.newSet("f_written");
    protected ConstraintSystem.ESet readFields  = system.newSet("f_read");
    protected Map<Method.Family, Set<Method>> methodImpls = Ovid.newMap();
    protected Map<Field, Set<Value>> fieldVals = Ovid.newMap();
    protected Map<Type, TypeInfo> typeInfo = Ovid.newMap();

    protected Analyzer analyzer = new Analyzer();
    protected Program program;

    class TypeInfo {
        Type type;
        ConstraintSystem.ESet subtypes;
        ConstraintSystem.ESet methods;
        ConstraintSystem.ESet fields;
        ConstraintSystem.ESet instances;
        TypeInfo(Type t) {
            type = t;
            instances = system.newSet(t + "_ent");
            fields = system.newSet(t + "_fld");
            methods = system.newSet(t + "_mth");
            subtypes = system.newSet(t + "_sub");
            system.addProductSideEffect(instances, fields, new RecordFieldEffect());
            system.addProductSideEffect(subtypes, methods, new TypeMethodEffect());
            system.addSubsetConstraint(instances, liveRecords);
            system.addSubsetConstraint(fields, readFields);
        }
    }

    /**
     * The <code>analyzeProgram()</code> method begins the analysis by starting at the entrypoints
     * of the program and iteratively finding the reachable code and data.
     *
     * @param p the program to analyze
     */
    public void analyzeProgram(Program p) {
        program = p;
        // add a side effect that analyzes the code of each live method
        system.addSideEffect(liveMethods, new LiveMethodEffect());
        // add a side effect that analyzes the code of each live method
        system.addSideEffect(liveRecords, new LiveRecordEffect());
        // get the entry points
        getEntryPoints(p.programDecl);
        // solve the constraint system (possibly firing side effects)
        system.solve();
    }

    private TypeInfo getClassTypeInfo(VirgilClass.IType t) {
        if ( !typeInfo.containsKey(t) ) {
            TypeInfo info = new TypeInfo(t);
            typeInfo.put(t, info);

            VirgilClass.IType st = t.getParentType();
            if ( st != null ) {
                TypeInfo pinfo = getClassTypeInfo(st);
                system.addSubsetConstraint(info.subtypes, pinfo.subtypes);
                system.addSubsetConstraint(pinfo.methods, info.methods);
                system.addSubsetConstraint(pinfo.fields, info.fields);
            }
        }
        return typeInfo.get(t);
    }

    private TypeInfo getTypeInfo(Type t) {
        if ( !typeInfo.containsKey(t) ) {
            if ( V2TypeSystem.isClass(t) ) return getClassTypeInfo((VirgilClass.IType)t);
            TypeInfo info = new TypeInfo(t);
            typeInfo.put(t, info);
        }
        return typeInfo.get(t);
    }

    private void getEntryPoints(ProgramDecl pd) {
        for (ProgramDecl.EntryPoint ept : pd.entryPoints) {
            VirgilComponent d = (VirgilComponent)ept.method.getCompoundDecl();
            system.addElementConstraint(d.getRecord(), getTypeInfo(d.getCanonicalType()).instances);
            system.addElementConstraint(ept.method, liveMethods);
        }
    }

    private void postValue(Value v) {
        if (v instanceof Heap.Record) {
            Heap.Record r = (Heap.Record)v;
            system.addElementConstraint(r, getTypeInfo(r.getType()).instances);
            if ( V2TypeSystem.isArray(r.getType()) ) {
                // if this is an array instance, post all its elements.
                int max = r.getSize();
                for ( int cntr = 0; cntr < max; cntr++ ) postValue(r.getValue(cntr));
            }
        } else if (v instanceof VirgilDelegate.Val) {
            VirgilDelegate.Val d = VirgilDelegate.fromValue(v);
            system.addElementConstraint(d.record, getTypeInfo(d.record.getType()).instances);
            system.addElementConstraint(d.method, liveMethods);
        }
    }

    private <K, V> void addLazyElem(Map<K, Set<V>> map, K k, V v) {
        Set<V> set = map.get(k);
        if ( set == null ) {
            set = Ovid.newSet();
            map.put(k, set);
        }
        set.add(v);
    }

    /**
     * The <code>RMAnalyzer.Operator</code> class implements a visitor that performs specific
     * actions on the operations in the program that correspond to accessing fields
     * and invoking methods.
     */
    protected class OpVisitor implements VirgilClass.OpVisitor<Void, TIRExpr>,
                                            VirgilComponent.OpVisitor<Void, TIRExpr> {

        public Void visit(VirgilClass.GetField f, TIRExpr... e) {
            system.addElementConstraint(f.field, getTypeInfo(f.thisType).fields);
            return null;
        }

        public Void visit(VirgilClass.SetField f, TIRExpr... e) {
            system.addElementConstraint(f.field, writtenFields);
            return null;
        }

        public Void visit(VirgilClass.GetMethod f, TIRExpr... e) {
            if ( f.virtual ) system.addElementConstraint(f.method.family, getTypeInfo(f.thisType).methods);
            else system.addElementConstraint(f.method, liveMethods);
            return null;
        }

        public Void visit(VirgilComponent.GetField f, TIRExpr... e) {
            Type type = f.component.getCanonicalType();
            system.addElementConstraint(f.component.getRecord(), getTypeInfo(type).instances);
            system.addElementConstraint(f.field, getTypeInfo(type).fields);
            return null;
        }

        public Void visit(VirgilComponent.SetField f, TIRExpr... e) {
            Type type = f.component.getCanonicalType();
            system.addElementConstraint(f.component.getRecord(), getTypeInfo(type).instances);
            system.addElementConstraint(f.field, writtenFields);
            return null;
        }

        public Void visit(VirgilComponent.GetMethod f, TIRExpr... e) {
            system.addElementConstraint(f.method, liveMethods);
            return null;
        }

        public Void visit(Operator o, TIRExpr... e) {
            // default behavior is to simply do nothing
            return null;
        }
    }

    protected class Analyzer extends DepthFirstOpVisitor<Void, Object> {
        protected Analyzer() {
            super(new RMSetAnalyzer.OpVisitor());
        }

        public Void visit(TIRConst.Value c, Object env) {
            postValue(c.getValue());
            return null;
        }

        public Void visit(TIRConst.Symbol c, Object env) {
            // TODO: deal with symbols by posting the heap record.
            return null;
        }
    }

    protected class LiveMethodEffect implements ConstraintSystem.SideEffect {
        public void fire(Object o1, ConstraintSystem.ESet s1) {
            TIRRep rep = TIRUtil.getRep((Method) o1);
            TIRExpr body = rep.getBody();
            body.accept(analyzer, null);
        }
    }
    protected class LiveRecordEffect implements ConstraintSystem.SideEffect {
        public void fire(Object o1, ConstraintSystem.ESet s1) {
            Type type = ((Heap.Record)o1).getType();
            system.addElementConstraint(type, getTypeInfo(type).subtypes);
        }
    }
    protected class RecordFieldEffect implements ConstraintSystem.SideEffect {
        public void fire(Object o1, ConstraintSystem.ESet s1) {
            Object[] a = (Object[])o1;
            Heap.Record r = (Heap.Record)a[0];
            Field f = (Field)a[1];
            Value val = r.getValue(f.fieldIndex);
            postValue(val);
            addLazyElem(fieldVals, f, val);
        }
    }
    protected class TypeMethodEffect implements ConstraintSystem.SideEffect {
        public void fire(Object o1, ConstraintSystem.ESet s1) {
            Object[] a = (Object[])o1;
            VirgilClass.IType t = (VirgilClass.IType)a[0];
            Method.Family mf = (Method.Family)a[1];
            Method m = t.getDecl().resolveMethod(mf.rootMethod.getName(), program.closure);
            system.addElementConstraint(m, liveMethods);
            addLazyElem(methodImpls, mf, m);
        }

    }

    public Program getProgram() {
        return program;
    }

    public Heap getHeap() {
        return program.heap;
    }

    public Set<Method> getLiveMethods() {
        return system.getSolution(liveMethods);
    }

    public Set<Heap.Record> getLiveRecords() {
        return system.getSolution(liveRecords);
    }

    public boolean isWriteOnly(Field sf) {
        return system.getSolution(writtenFields).contains(sf) && !system.getSolution(readFields).contains(sf);
    }

    public Value getConstantMethodValue(Method method) {
        if ( method.family == null ) return new Function.Val(method);
        Set<Method> set = methodImpls.get(method.family);
        if ( set == null ) return new Function.Val(method);
        return new Function.Val(set.iterator().next());
    }

    public boolean isConstantMethod(Method method) {
        if ( method.family == null ) return true;
        Set<Method> set = methodImpls.get(method.family);
        return set == null || set.size() == 1;
    }

    public Value getConstantFieldValue(Field field) {
        Set<Value> set = fieldVals.get(field);
        if ( set == null ) return Value.BOTTOM;
        return set.iterator().next();
    }

    public boolean isConstantField(Field field) {
        Set<Value> set = fieldVals.get(field);
        return !system.getSolution(writtenFields).contains(field) && (set == null || set.size() == 1);
    }

    public Collection<Field> getFields(Type t) {
        return system.getSolution(getTypeInfo(t).fields);
    }
}
