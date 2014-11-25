/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Mar 24, 2006
 */

package vpc.core.base;

import cck.util.Util;
import vpc.core.Value;
import vpc.core.types.*;
import vpc.tir.expr.Operator;
import vpc.tir.expr.Precedence;

/**
 * The <code>PrimChar</code> class represents the character type and its values.
 *
 * @author Ben L. Titzer
 */
public class PrimChar {

    public static int CHAR_CACHE = 128;

    public static Type.Simple TYPE = new Type.Simple("char");
    public static TypeCon TYPECON = new TypeCon.Singleton(TYPE);

    static {
        // add the binops to each of the primitive types
        Precedence.register(LT.class, Precedence.PREC_LESS_THAN);
        Precedence.register(GR.class, Precedence.PREC_GREATER);
        Precedence.register(LTEQ.class, Precedence.PREC_LESS_EQUAL);
        Precedence.register(GREQ.class, Precedence.PREC_GREATER_EQUAL);
    }

    public abstract static class Compare extends Operator.Op2 {

        protected Compare() {
            super(TYPE, TYPE, PrimBool.TYPE);
        }

        public Value apply2(Value v1, Value v2) {
            return PrimBool.toValue(apply(fromValue(v1), fromValue(v2)));
        }

        public abstract boolean apply(char v1, char v2);

    }

    /**
     * The <code>Comparison.PrimChar.EQU</code> class represents an integer comparison for equality.
     */
    public static class EQU extends Compare {
        public boolean apply(char v1, char v2) {
            return v1 == v2;
        }
    }

    /**
     * The <code>Comparison.PrimChar.NEQU</code> class represents an integer comparison for inequality.
     */
    public static class NEQU extends Compare {
        public boolean apply(char v1, char v2) {
            return v1 != v2;
        }
    }

    /**
     * The <code>Comparison.PrimChar.GR</code> class represents an integer comparison for greater than.
     */
    public static class GR extends Compare {
        public boolean apply(char v1, char v2) {
            return v1 > v2;
        }
    }

    /**
     * The <code>Comparison.PrimChar.LT</code> class represents an integer comparison for less than.
     */
    public static class LT extends Compare {
        public boolean apply(char v1, char v2) {
            return v1 < v2;
        }
    }

    /**
     * The <code>Comparison.PrimChar.GREQ</code> class represents an integer comparison
     * for greater than or equal to.
     */
    public static class GREQ extends Compare {
        public boolean apply(char v1, char v2) {
            return v1 >= v2;
        }
    }

    /**
     * The <code>Comparison.PrimChar.LTEQ</code> class represents an integer comparison for less than
     * or equal to.
     */
    public static class LTEQ extends Compare {
        public boolean apply(char v1, char v2) {
            return v1 <= v2;
        }
    }

    /**
     * The <code>CHAR</code> class represents a character value.
     */
    public static class Val extends Value {
        public final char value;

        private static Val[] cache = new Val[CHAR_CACHE];

        private Val(char v) {
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
            return "char:" + (int) value;
        }
    }

    public static char fromValue(Value v) {
        if ( v == Value.BOTTOM ) return (char)0; // null (bottom) means the zero char
        if (v instanceof Val) return ((Val) v).value;
        throw Util.failure("Value of type " + v.getType() + " cannot be converted to PrimChar");
    }

    public static Val toValue(char c) {
        if (c < CHAR_CACHE) {
            Val ival = Val.cache[c];
            return ival == null ? (Val.cache[c] = new Val(c)) : ival;
        } else {
            return new Val(c);
        }
    }
}
