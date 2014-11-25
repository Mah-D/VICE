/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 31, 2007
 */

package vpc.tir.stages;

import cck.util.Arithmetic;
import cck.util.Util;
import vpc.core.Program;
import vpc.core.base.PrimRaw;
import vpc.core.csr.CSRGen;
import vpc.core.csr.CSRProgram;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.core.virgil.VirgilOp;
import vpc.sched.Stage;
import vpc.tir.*;
import static vpc.tir.TIRUtil.*;
import vpc.tir.expr.Operator;
import vpc.tir.opt.DepthFirstTransformer;

/**
 * The <code>RawConversion</code> class implements a translation from the
 * Virgil-level raw operators, which includes bit indices and shifts within
 * arbitrary width bit windows, to lower-level bit operators such as masks
 * and shifts with fixed sized quantities.
 *
 * @author Ben L. Titzer
 */
public class RawConversion extends Stage {

    public void visitProgram(Program p) {
        p.csr = new CSRProgram(p);
        for ( Method m : p.closure.methods ) {
            visitMethod(m);
        }
    }

    public void visitMethod(Method m) {
        TIRRep mrep = getRep(m);
        Transformer visitor = new Transformer();
        TIRExpr nbody = visitor.transform(mrep.getBody(), null);
        mrep.setBody(nbody);
        m.addMethodRep(TIRRep.REP_NAME, mrep);
    }

    protected static class Transformer extends DepthFirstTransformer<Object> {
        public TIRExpr visit(TIROperator o, Object env) {

            switch ( getOpcode(o) ) {
                case CSRGen.RAW_SHL: {
                    // a << b  =>  (a << b) & mask
                    TIRExpr[] no = transform(o.operands, env);
                    return copy(o,
                            AND(widen(o.operator),
                                    SHL(widen(o.operator), no[0], no[1]),
                                    MASK(width(o.operands[0]))));
                }
                case CSRGen.RAW_SHR: {
                    // a >> b  =>  a >> b
                    TIRExpr[] no = transform(o.operands, env);
                    return copy(o, SHR(widen(o.operator), no[0], no[1]));
                }
                case VirgilOp.RAW_GETBIT: {
                    // a[b]  =>  (a >> b) & 0b1
                    TIRExpr[] no = transform(o.operands, env);
                    return copy(o,
                            AND(widen(1),
                                    SHR(widen(o.operator), no[0], no[1]),
                                    RAWVAL(widen(1), 1)));
                }
                case VirgilOp.RAW_SETBIT: {
                    //  a[b] = c  =>  (a & ~(1 << b)) | (c << b)
                    TIRExpr[] no = transform(o.operands, env);
                    int nwidth = widen(o.operator);
                    return copy(o,
                            OR(nwidth,
                            AND(nwidth, no[0],
                                    COMP(nwidth,
                                            SHL(nwidth,
                                                    $VAL(1), no[1]))),
                                    SHL(nwidth, no[2], no[1])));
                }
                case CSRGen.RAW_COMP: {
                    // ~a  =>  (~a) & mask
                    TIRExpr[] no = transform(o.operands, env);
                    return copy(o,
                            AND(widen(o.operator),
                                    COMP(widen(o.operator), no[0]),
                                    MASK(width(o.operands[0]))));
                }
                case VirgilOp.RAW_CONCAT: {
                    // a # b  =>  (a << wb) | b

                    TIRExpr[] no = transform(o.operands, env);
                    int bw = ((PrimRaw.Concat)o.operator).shift;
                    return copy(o, OR(widen(o.operator), SHL(widen(o.operator), no[0], $VAL(bw)), no[1]));
                }
                case CSRGen.CAST_RAW_RAW: {
                    TIRExpr[] no = transform(o.operands, env);
                    if ( width(o.operands[0]) <= width(o) ) {
                        // e :: w  =>  e
                        return no[0];
                    }
                    else {
                        // e :: w  =>  e & mask    if w < we
                        return copy(o, AND(widen(o.operator), no[0], MASK(width(o.operands[0]))));
                    }
                }
                case CSRGen.RAW_AND:
                case CSRGen.RAW_OR:
                case CSRGen.RAW_XOR:
                case CSRGen.CAST_RAW_INT:
                case CSRGen.CAST_RAW_CHAR:
                case CSRGen.CAST_INT_RAW:
                case CSRGen.CAST_CHAR_RAW:
                default:
                    return super.visit(o, env);
            }
        }


        private TIRExpr MASK(int w) {
            return RAWVAL(w, Arithmetic.getLongBitRangeMask(0, w - 1));
        }

        private TIRExpr RAWVAL(int w, long val) {
            return $VAL(PrimRaw.toValue(w, val));
        }

        private TIROperator AND(int w, TIRExpr na, TIRExpr nb) {
            return new TIROperator(new PrimRaw.AND(w, w), na, nb);
        }

        private TIROperator OR(int w, TIRExpr na, TIRExpr nb) {
            return new TIROperator(new PrimRaw.OR(w), na, nb);
        }

        private TIROperator SHL(int w, TIRExpr na, TIRExpr nb) {
            return new TIROperator(new PrimRaw.SHL(w), na, nb);
        }

        private TIROperator SHR(int w, TIRExpr na, TIRExpr nb) {
            return new TIROperator(new PrimRaw.SHR(w), na, nb);
        }

        private TIROperator COMP(int w, TIRExpr na) {
            return new TIROperator(new PrimRaw.Complement(w), na);
        }

        private int getOpcode(TIROperator to) {
            Integer val = CSRGen.opMap.get(to.operator.getClass());
            if ( val == null ) return -1;
            return val;
        }

        private int width(TIRExpr e) {
            Type t = e.getType();
            if ( !(t instanceof PrimRaw.IType) )
                throw Util.failure("not a raw type: "+t);
            return ((PrimRaw.IType)t).width;
        }

        private int widen(Operator op) {
            return widen(op.getResultType());
        }

        private int widen(int w) {
            if ( w <= 8 ) return 8;
            if ( w <= 16 ) return 16;
            if ( w <= 32 ) return 32;
            if ( w <= 64 ) return 64;
            throw Util.failure("internal bit size too large: "+w);
        }

        private int widen(Type t) {
            if ( !(t instanceof PrimRaw.IType) )
                throw Util.failure("not a raw type: "+t);
            return widen((PrimRaw.IType)t);
        }

        private int widen(PrimRaw.IType rt) {
            if ( rt.width <= 8 ) return 8;
            if ( rt.width <= 16 ) return 16;
            if ( rt.width <= 32 ) return 32;
            if ( rt.width <= 64 ) return 64;
            throw Util.failure("internal bit size too large: "+rt.width);
        }
    }
}
