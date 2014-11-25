/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Feb 4, 2007
 */
package vpc.tir.stages;

import cck.util.Option;
import vpc.core.Program;
import vpc.core.csr.CSRGen;
import vpc.core.decl.Method;
import vpc.core.virgil.*;
import vpc.core.virgil.model.ObjectModel;
import vpc.sched.Stage;
import vpc.tir.*;
import vpc.tir.opt.DepthFirstTransformer;

/**
 * The <code>ObjectConversion</code> class defines a compiler pass that
 * translates object-level and array-level operations to the lower-level
 * (CSR) representation in terms of structs and pointers.
 *
 * @author Ben L. Titzer
 */
public class ObjectConversion extends Stage {

    protected final Option.Bool SUPPRESS_NULL = options.newOption("suppress-null-checks", false,
            "This option is intended for compiler performance testing only. When enabled, this " +
            "option suppresses the generation of explicit null checks in the outputted code, " +
            "resulting in unsafe execution. This option should NOT be used by application " +
            "programmers or designers in order to get better performance.");


    public void visitProgram(Program p) {
        ObjectModel om = new ObjectModel(p);
        p.csr.model = om;
        // TODO: these state fields should not be exposed here
        om.tf = new Transformer(om);
        om.processOptions(options);
        om.processClosure(p.closure);
        for ( Method m : p.closure.methods ) {
            om.rep = TIRUtil.getRep(m);
            TIRExpr nbody = om.tf.transform(om.rep.getBody(), null);
            om.rep.setBody(nbody);
        }
    }

    protected class Transformer extends DepthFirstTransformer<Object>  {

        protected ObjectModel model;

        Transformer(ObjectModel om) {
            model = om;
        }

        public TIRExpr visit(TIROperator o, Object env) {

            switch ( getOpcode(o) ) {
                case VirgilOp.NULL_CHECK:
                    if (SUPPRESS_NULL.get()) return transform(o.operands[0], env);
                    else return model.convertNullCheck(o, env);
                case VirgilOp.CLASS_GETFIELD:
                    return model.convertClassGetField(o, (VirgilClass.GetField) o.operator, env);
                case VirgilOp.CLASS_SETFIELD:
                    return model.convertClassSetField(o, (VirgilClass.SetField) o.operator, env);
                case VirgilOp.CLASS_GETMETHOD:
                    return model.convertClassGetMethod(o, (VirgilClass.GetMethod) o.operator, env);
                case VirgilOp.COMPONENT_GETFIELD:
                    return model.convertComponentGetField(o, (VirgilComponent.GetField)o.operator);
                case VirgilOp.COMPONENT_SETFIELD:
                    return model.convertComponentSetField(o, (VirgilComponent.SetField)o.operator, env);
                case VirgilOp.COMPONENT_GETMETHOD:
                    return model.convertComponentGetMethod(o, (VirgilComponent.GetMethod)o.operator);
                case VirgilOp.ARRAY_GETLENGTH:
                    return model.convertArrayGetLength(o, env);
                case VirgilOp.ARRAY_INIT:
                    return model.convertArrayInit(o);
                case VirgilOp.NEW_ARRAY:
                    return model.convertNewArray(o);
                case VirgilOp.NEW_OBJECT:
                    return model.convertNewObject(o);
                case VirgilOp.TYPE_QUERY:
                    return model.convertTypeQuery(o, (VirgilClass.TypeQuery)o.operator, env);
                case VirgilOp.TYPE_CAST:
                    return model.convertTypeCast(o, (VirgilClass.TypeCast)o.operator, env);
                case VirgilOp.ARRAY_GETELEMENT:
                    return model.convertArrayGetElement(o, env);
                case VirgilOp.ARRAY_SETELEMENT:
                    return model.convertArraySetElement(o, env);
                case VirgilOp.TUPLE_GETELEMENT:
                    return model.convertTupleGetElement(o, env);
                default:
                    return super.visit(o, env);
            }
        }

        public TIRExpr visit(TIRCall c, Object env) {
            assert c.delegate;
            return model.convertCall(c, env);
        }

        public TIRExpr visit(TIRConst.Value e, Object env) {
            return TIRUtil.$VAL(model.csr.encodeValue(e.getValue(), e.getType()));
        }

        private int getOpcode(TIROperator to) {
            Integer val = CSRGen.opMap.get(to.operator.getClass());
            if ( val == null ) return -1;
            return val;
        }
    }
}
