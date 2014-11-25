/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: May 17, 2007
 */

package vpc.tir.opt;

import vpc.core.*;
import vpc.core.base.Function;
import vpc.core.csr.CSRFunction;
import vpc.core.csr.CSRGen;
import vpc.core.decl.Method;
import vpc.core.virgil.*;
import vpc.tir.*;
import static vpc.tir.TIRUtil.*;
import vpc.hil.Device;

/**
 * @author Ben L. Titzer
 */
public class TIRCallShape {

    public static final int DIRECT = 0;
    public static final int VIRTUAL = 1;
    public static final int DYNAMIC = 2;
    public static final int DEVICE = 3;

    public static CallShape match(Program program, TIRCall call) {
        if ( isValue(call.func) ) {
            // a constant. pull out the record and the method
            Value val = valOf(call.func);
            if ( val == Value.BOTTOM ) {
                // this is a call on a null value, will generate exception
                return new Direct(call.func, null);
            }

            if ( val instanceof VirgilDelegate.Val ) {
                // this is a delegate call
                assert call.delegate;
                VirgilDelegate.Val deleg = VirgilDelegate.fromValue(val);
                return new Direct($REF(deleg.record), deleg.method);
            } else if ( val instanceof Function.Val ) {
                // this is not a delegate call
                assert !call.delegate;
                Method func = Function.fromValue(val);
                return new Direct(call.arguments[0], func);
            } else if ( val instanceof CSRFunction.Val ) {
                // this is not a delegate call
                assert !call.delegate;
                Method func = ((CSRFunction.Val)val).method;
                return new Direct(call.arguments[0], func);
            }
        } else {
            // not a constant. pattern match on the function.
            switch ( CSRGen.getOpcode(call.func) ) {
                case VirgilOp.CLASS_GETMETHOD: {
                    // a dispatch on a class method.
                    assert call.delegate;
                    TIROperator op = (TIROperator)call.func;
                    TIRExpr _this = op.operands[0];
                    VirgilClass.GetMethod gm = (VirgilClass.GetMethod) op.operator;

                    // is this a virtual call?
                    if (!metaDispatch(gm))
                        return new Direct(_this, gm.method);

                    if ( isValue(_this) ) {
                        // a value; resolve the actual method.
                        Heap.Record r = (Heap.Record) valOf(_this);
                        if ( r == null ) return new Direct(_this, null);
                        VirgilClass cd = VirgilClass.declOf(r.getType());
                        return new Direct(_this, cd.resolveMethod(gm.method.getName(), program.closure));
                    }
                    // TODO: could also use CHA approximation here.

                    // default: a virtual method.
                    return new Virtual(gm.thisType, _this, gm.method);
                }
                case VirgilOp.COMPONENT_GETMETHOD: {
                    // a component method call.
                    assert call.delegate;
                    TIROperator op = (TIROperator)call.func;
                    VirgilComponent.GetMethod gm = (VirgilComponent.GetMethod)op.operator;
                    return new Direct($REF(gm.component.getRecord()), gm.method);
                }
                case VirgilOp.DEVICE_INSTRUSE: {
                    TIROperator op = (TIROperator)call.func;
                    Device.InstrUse iu = (Device.InstrUse)op.operator;
                    return new Instr(iu.instr);
                }
            }
        }
        return new Dynamic(call.func);
    }

    public static boolean metaDispatch(VirgilClass.GetMethod gm) {
        return gm.virtual && gm.method.family != null;
    }

    public abstract static class CallShape {
        public final int kind;
        protected CallShape(int k) {
            kind = k;
        }
    }

    public static class Direct extends CallShape {
        public final TIRExpr thisExpr;
        public final Method method;

        protected Direct(TIRExpr t, Method m) {
            super(DIRECT);
            thisExpr = t;
            method = m;
        }
    }

    public static class Instr extends CallShape {
        public final Device.Instruction instr;

        protected Instr(Device.Instruction i) {
            super(DEVICE);
            instr = i;
        }
    }

    public static class Virtual extends CallShape {
        public final VirgilClass.IType thisType;
        public final TIRExpr thisExpr;
        public final Method method;

        protected Virtual(VirgilClass.IType tt, TIRExpr t, Method m) {
            super(VIRTUAL);
            thisType = tt;
            thisExpr = t;
            method = m;
        }
    }

    public static class Dynamic extends CallShape {
        public final TIRExpr expr;

        protected Dynamic(TIRExpr e) {
            super(DYNAMIC);
            expr = e;
        }
    }
}
