/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Mar 27, 2006
 */
package vpc.core.base;

import cck.util.Util;
import vpc.core.Heap;
import vpc.core.Value;
import vpc.core.types.Type;
import vpc.core.types.TypeCon;
import vpc.tir.expr.Operator;

/**
 * @author Ben L. Titzer
 */
public class Reference {
    public static NullType NULL_TYPE = new NullType();
    public static TypeCon NULL_TYPECON = new TypeCon.Singleton(NULL_TYPE);

    /**
     * The <code>PrimNull</code> type represents the type of the null value;
     * it is assignable to all object references. It has the appropriate
     * isXXX() methods overridden to reflect its semantics.
     */
    public static class NullType extends Type.Simple {
        NullType() {
            super("null");
        }

    }

    public static class NullCheck extends Operator.Op1 {
        public NullCheck(Type t) {
            super(t, t);
        }
        public Value apply1(Value v1) throws NullCheckException {
            if ( isNull(v1) ) throw new NullCheckException();
            return v1;
        }
    }

    public static class NullCheckException extends Operator.Exception {
        public NullCheckException() {
            super("null check exception");
        }
    }

    public static boolean isNull(Value v) {
        return v == null || v == Value.BOTTOM;
    }
}
