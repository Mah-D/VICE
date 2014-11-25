/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Mar 24, 2006
 */

package vpc.core.base;

import cck.parser.AbstractToken;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.Value;
import vpc.core.types.*;
import vpc.dart.symc.SCValue;
import vpc.tir.expr.Operator;
import vpc.tir.expr.Precedence;
import vpc.util.Maybe;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * The <code>PrimInt32</code> class encapsulates the notion of 32-bit integers for various
 * parts of the compiler.
 *
 * @author Ben L. Titzer
 */
public class PrimInt32 {

    public static Type.Simple TYPE = new Type.Simple("int");
    public static TypeCon TYPECON = new TypeCon.Singleton(TYPE);
    public static int INT_VALUE_CACHE_SIZE = 1024;

    protected static Val[] cache = new Val[INT_VALUE_CACHE_SIZE * 2];

    static {
        Precedence.register(ADD.class, Precedence.PREC_ADD);
        Precedence.register(SUB.class, Precedence.PREC_ADD);
        Precedence.register(MUL.class, Precedence.PREC_MULTIPLY);
        Precedence.register(DIV.class, Precedence.PREC_MULTIPLY);
        Precedence.register(MOD.class, Precedence.PREC_MULTIPLY);
        Precedence.register(LT.class, Precedence.PREC_LESS_THAN);
        Precedence.register(GR.class, Precedence.PREC_GREATER);
        Precedence.register(LTEQ.class, Precedence.PREC_LESS_EQUAL);
        Precedence.register(GREQ.class, Precedence.PREC_GREATER_EQUAL);
        Precedence.register(NEG.class, Precedence.PREC_MINUS);

        Precedence.register(XOR.class, Precedence.PREC_BITWISE_XOR);
        Precedence.register(AND.class, Precedence.PREC_BITWISE_AND);
        Precedence.register(OR.class, Precedence.PREC_BITWISE_OR);
        Precedence.register(SHL.class, Precedence.PREC_SHIFT_LEFT);
        Precedence.register(SHR.class, Precedence.PREC_SHIFT_RIGHT);

        // register the precedence of the raw operators
        Precedence.register(PrimRaw.XOR.class, Precedence.PREC_BITWISE_XOR);
        Precedence.register(PrimRaw.AND.class, Precedence.PREC_BITWISE_AND);
        Precedence.register(PrimRaw.OR.class, Precedence.PREC_BITWISE_OR);
        Precedence.register(PrimRaw.SHL.class, Precedence.PREC_SHIFT_LEFT);
        Precedence.register(PrimRaw.SHR.class, Precedence.PREC_SHIFT_RIGHT);
        Precedence.register(PrimRaw.Complement.class, Precedence.PREC_COMP);
    }

    public abstract static class Arith extends Operator.Op2 {

        protected Arith() {
            super(TYPE, TYPE, TYPE);
        }

        public Value apply2(Value v1, Value v2) throws Operator.Exception {
            return toValue(apply(fromValue(v1), fromValue(v2)));
        }

        public abstract int apply(int v1, int v2) throws Operator.Exception;
    }

    public abstract static class ArithUnOp extends Operator.Op1 {
        protected ArithUnOp() {
            super(TYPE, TYPE);
        }

        public Value apply1(Value v1) {
            return toValue(apply(fromValue(v1)));
        }

        public abstract int apply(int v1);
    }

    /**
     * The <code>PrimInt32.ADD</code> class represents an integer addition.
     */
    public static class ADD extends Arith {
        public int apply(int v1, int v2) {
            return v1 + v2;
        }
    }

    /**
     * The <code>PrimInt32.SUB</code> class represents an integer subtraction.
     */
    public static class SUB extends Arith {
        public int apply(int v1, int v2) {
            return v1 - v2;
        }
    }

    /**
     * The <code>PrimInt32.MUL</code> class represents an integer multiplication.
     */
    public static class MUL extends Arith {
        public int apply(int v1, int v2) {
            return v1 * v2;
        }
    }

    /**
     * The <code>PrimInt32.DIV</code> class represents an integer division.
     */
    public static class DIV extends Arith {
        public int apply(int v1, int v2) throws Operator.Exception {
            if (v2 == 0) throw new DivideByZeroException();
            return v1 / v2;
        }
    }

    /**
     * The <code>PrimInt32.MOD</code> class represents an integer modulus.
     */
    public static class MOD extends Arith {
        public int apply(int v1, int v2) throws Operator.Exception {
            if (v2 == 0) throw new DivideByZeroException();
            return v1 % v2;
        }
    }

    /**
     * The <code>PrimInt32.AND</code> class represents an integer bitwise and.
     */
    public static class AND extends Arith {
        public int apply(int v1, int v2) throws Operator.Exception {
            return v1 & v2;
        }
    }

    /**
     * The <code>PrimInt32.OR</code> class represents an integer bitwise or.
     */
    public static class OR extends Arith {
        public int apply(int v1, int v2) throws Operator.Exception {
            return v1 | v2;
        }
    }

    /**
     * The <code>PrimInt32.XOR</code> class represents an integer bitwise xor.
     */
    public static class XOR extends Arith {
        public int apply(int v1, int v2) throws Operator.Exception {
            return v1 ^ v2;
        }
    }

    /**
     * The <code>PrimInt32.SHL</code> class represents an integer bitwise shift left.
     */
    public static class SHL extends Arith {
        public int apply(int v1, int v2) throws Operator.Exception {
            if (v2 >= 32) return 0;
            return v1 << v2;
        }
    }
    /**
     * The <code>PrimInt32.SHR</code> class represents an integer bitwise shift right.
     */
    public static class SHR extends Arith {
        public int apply(int v1, int v2) throws Operator.Exception {
            if (v2 >= 32) return 0;
            return v1 >>> v2;
        }
    }
    /**
     * The <code>PrimInt32.NEG</code> class represents an integer operation that
     * reverses the sign.
     */
    public static class NEG extends ArithUnOp {
        public int apply(int v1) {
            return -v1;
        }
    }

    public abstract static class Compare extends Operator.Op2 {
        protected Compare() {
            super(TYPE, TYPE, PrimBool.TYPE);
        }

        public Value apply2(Value v1, Value v2) {
            return PrimBool.toValue(apply(fromValue(v1), fromValue(v2)));
        }

        public abstract boolean apply(int v1, int v2);
    }

    /**
     * The <code>Comparison.Int.EQU</code> class represents an integer comparison for equality.
     */
    public static class EQU extends Compare {
        public boolean apply(int v1, int v2) {
            return v1 == v2;
        }
    }

    /**
     * The <code>Comparison.Int.NEQU</code> class represents an integer comparison for inequality.
     */
    public static class NEQU extends Compare {
        public boolean apply(int v1, int v2) {
            return v1 != v2;
        }
    }

    /**
     * The <code>Comparison.Int.GR</code> class represents an integer comparison for greater than.
     */
    public static class GR extends Compare {
        public boolean apply(int v1, int v2) {
            return v1 > v2;
        }
    }

    /**
     * The <code>Comparison.Int.LT</code> class represents an integer comparison for less than.
     */
    public static class LT extends Compare {
        public boolean apply(int v1, int v2) {
            return v1 < v2;
        }
    }

    /**
     * The <code>Comparison.Int.GREQ</code> class represents an integer comparison
     * for greater than or equal to.
     */
    public static class GREQ extends Compare {
        public boolean apply(int v1, int v2) {
            return v1 >= v2;
        }
    }

    /**
     * The <code>Comparison.Int.LTEQ</code> class represents an integer comparison for less than
     * or equal to.
     */
    public static class LTEQ extends Compare {
        public boolean apply(int v1, int v2) {
            return v1 <= v2;
        }
    }

    /**
     * The <code>PrimInt32.Val</code> class represents an integer value in the range
     * <code>[-2^31, 2^31 - 1]</code> encoded using standard two's complement.
     */
    public static class Val extends Value {
        public final int value;

        private Val(int v) {
            super();
            value = v;
        }

        public Type getType() {
            return TYPE;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (o == Value.BOTTOM ) return value == 0; // 0 == bottom
            return o instanceof Val && ((Val) o).value == value;
        }

        public String toString() {
            return "int:" + value;
        }

    }

    public static Val toValue(int i) {
        int index = i + INT_VALUE_CACHE_SIZE;
        if (index >= 0 && index < cache.length) {
            Val ival = cache[index];
            if (ival == null) return cache[index] = new Val(i);
            else return ival;
        } else {
            return new Val(i);
        }
    }
    public static int fromValue(Value v) {
        if ( v == Value.BOTTOM ) return 0; // null (bottom) means the zero integer
        if (v instanceof Val) return ((Val) v).value;
        throw Util.failure("Value of type " + v.getType() + " cannot be converted to Int32");
    }

    public static class DivideByZeroException extends Operator.Exception {
        public DivideByZeroException() {
            super("divide by zero exception");
        }
    }

    /**
     * The <code>PrimInt32.Converter</code> class implements a number of parsing methods for
     * converting sequences of characters into 32-bit integer values.
     */
    public static class Converter {

        public static Maybe<Val> convertDecimal(AbstractToken tok) {
            StringCharacterIterator i = new StringCharacterIterator(tok.image);
            return convertDecimal(StringUtil.peekAndEat(i, '-'), i);
        }

        public static Maybe<Val> convertDecimal(boolean negative, AbstractToken tok) {
            return convertDecimal(negative, new StringCharacterIterator(tok.image));
        }

        public static Maybe<Val> convertDecimal(boolean negative, CharacterIterator i) {
            long accum = 0;
            // allow at most 10 digits for integer literals
            for (int cntr = 0; cntr < 11; cntr++, i.next()) {
                char ch = i.current();
                if (ch == CharacterIterator.DONE) return toMaybeValue(accum);
                if (ch < '0' || ch > '9') return invalidDigit(ch);
                accum = accum * 10 + decimalDigit(negative, ch);
            }
            return tooLarge();
        }

        private static int decimalDigit(boolean negative, char ch) {
            return negative ? '0' - ch : ch - '0';
        }

        private static Maybe<Val> invalidDigit(char ch) {
            return new Maybe<Val>(new Error("invalid decimal digit: " + ch));
        }

        private static Maybe<Val> tooLarge() {
            return new Maybe<Val>(new Error("decimal constant too large"));
        }

        public static Maybe<Val> toMaybeValue(long value) {
            if (value > Integer.MAX_VALUE) return tooLarge();
            if (value < Integer.MIN_VALUE) return tooLarge();
            return new Maybe<Val>(toValue((int) value));
        }

        public static Maybe<Val> unsignedValueOf(long value) {
            if ((value & 0xFFFFFFFF00000000L) != 0) return tooLarge();
            return new Maybe<Val>(toValue((int) value));
        }

    }
}
