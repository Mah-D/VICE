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
 * The <code>PrimBool</code> class encapsulates the concept of a boolean
 * used throughout the compiler.
 *
 * @author Ben L. Titzer
 */
public class PrimBool {
    public static Type.Simple TYPE = new Type.Simple("bool");
    public static TypeCon TYPECON = new TypeCon.Singleton(TYPE);
    public static Val TRUE = toValue(true);
    public static Val FALSE = toValue(false);

    static {
        Precedence.register(AND.class, Precedence.PREC_LOGICAL_AND);
        Precedence.register(OR.class, Precedence.PREC_LOGICAL_OR);
        Precedence.register(NOT.class, Precedence.PREC_NOT);
    }

    public abstract static class BoolBinOp extends Operator.Op2 {
        protected BoolBinOp() {
            super(TYPE, TYPE, TYPE);
        }

        public Value apply2(Value v1, Value v2) {
            return toValue(apply(fromValue(v1), fromValue(v2)));
        }

        public abstract boolean apply(boolean v1, boolean v2);
    }

    public abstract static class BoolUnOp extends Operator.Op1 {
        protected BoolUnOp() {
            super(TYPE, TYPE);
        }

        public Value apply1(Value v1) {
            return toValue(apply(fromValue(v1)));
        }

        public abstract boolean apply(boolean v1);
    }

    /**
     * The <code>Logical.AND</code> class represents the logical and operation.
     * This operator does not implement shortcutted conditionals, because it operates on
     * values, not expressions.
     */
    public static class AND extends BoolBinOp {
        public boolean apply(boolean v1, boolean v2) {
            return v1 && v2;
        }
    }

    /**
     * The <code>Logical.OR</code> class represents the logical or operation on booleans.
     * This operator does not implement shortcutted conditionals, because it operates on
     * values, not expressions.
     */
    public static class OR extends BoolBinOp {
        public boolean apply(boolean v1, boolean v2) {
            return v1 || v2;
        }
    }

    /**
     * The <code>Logical.NOT</code> class represents a boolean negation.
     */
    public static class NOT extends BoolUnOp {
        public boolean apply(boolean v1) {
            return !v1;
        }
    }

    /**
     * The <code>BOOL</code> class represents a boolean value which can be either <code>true</code>
     * or <code>false</code>.
     */
    public static class Val extends Value {
        protected static Val TRUE = new Val(true);
        protected static Val FALSE = new Val(false);

        public final boolean value;

        private Val(boolean v) {
            super();
            value = v;
        }

        public Type getType() {
            return TYPE;
        }

        public boolean equals(Object o) {
            if ( o == Value.BOTTOM ) return !value; // false == bottom
            return o instanceof Val && ((Val) o).value == value;
        }

        public String toString() {
            return "bool:" + value;
        }
    }

    public static boolean fromValue(Value v) {
        if ( v == Value.BOTTOM ) return false; // null (bottom) means false for booleans.
        if (v instanceof Val) return ((Val) v).value;
        throw Util.failure("Value of type " + v.getType() + " cannot be converted to bool");
    }

    public static Val toValue(boolean val) {
        return val ? Val.TRUE : Val.FALSE;
    }
}
