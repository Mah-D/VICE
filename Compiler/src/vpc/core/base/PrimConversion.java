/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Sep 28, 2006
 */
package vpc.core.base;

import cck.util.Arithmetic;
import vpc.core.Value;
import vpc.tir.expr.Operator;

/**
 * The <code>PrimConversion</code> definition.
 *
 * @author Ben L. Titzer
 */
public class PrimConversion {
    public static class Int32_Raw extends Operator.Op1 {
        final PrimRaw.IType result;
        final long mask;

        public Int32_Raw(PrimRaw.IType rt) {
            super(PrimInt32.TYPE, rt);
            mask = Arithmetic.getLongBitRangeMask(0, rt.width - 1);
            result = rt;
        }

        public Value apply1(Value v1) {
            return PrimRaw.toValue(result.width, PrimInt32.fromValue(v1) & mask);
        }
    }

    public static class Char_Int32 extends Operator.Op1 {
        public Char_Int32() {
            super(PrimChar.TYPE, PrimInt32.TYPE);
        }

        public Value apply1(Value v1) {
            return PrimInt32.toValue(PrimChar.fromValue(v1));
        }
    }

    public static class Int32_Char extends Operator.Op1 {
        public Int32_Char() {
            super(PrimInt32.TYPE, PrimChar.TYPE);
        }

        public Value apply1(Value v1) {
            return PrimChar.toValue((char)PrimInt32.fromValue(v1));
        }
    }

    public static class Char_Raw extends Operator.Op1 {
        final PrimRaw.IType result;
        final long mask;

        public Char_Raw(PrimRaw.IType rt) {
            super(PrimChar.TYPE, rt);
            mask = Arithmetic.getLongBitRangeMask(0, rt.width - 1);
            result = rt;
        }

        public Value apply1(Value v1) {
            return PrimRaw.toValue(result.width, PrimChar.fromValue(v1) & mask);
        }
    }

    public static class AdjustRaw extends Operator.Op1 {
        final PrimRaw.IType result;
        final long mask;

        public AdjustRaw(PrimRaw.IType t1, PrimRaw.IType t2) {
            super(t1, t2);
            result = t2;
            mask = Arithmetic.getLongBitRangeMask(0, t2.width - 1);
        }

        public Value apply1(Value v1) {
            return PrimRaw.toValue(result.width, mask & PrimRaw.fromValue(v1));
        }
    }

    public static class Raw_Char extends Operator.Op1 {

        public Raw_Char(PrimRaw.IType t1) {
            super(t1, PrimChar.TYPE);
        }

        public Value apply1(Value v1) {
            return PrimChar.toValue((char)PrimRaw.fromValue(v1));
        }
    }

    public static class Raw_Int32 extends Operator.Op1 {

        public Raw_Int32(PrimRaw.IType t1) {
            super(t1, PrimInt32.TYPE);
        }

        public Value apply1(Value v1) {
            return PrimInt32.toValue((int)PrimRaw.fromValue(v1));
        }
    }
}
