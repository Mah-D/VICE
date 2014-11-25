/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Dec 30, 2005
 */
package vpc.core;

import vpc.core.base.PrimBool;
import vpc.core.types.Type;
import vpc.tir.TIRInterpreter;
import vpc.tir.expr.Operator;

/**
 * The <code>Value</code> class represents a program value, such as an integer, boolean,
 * record reference, etc, during the execution of a program. Instances of this class represent
 * the values computed during the initialization phase of building an application where the
 * constructors from each of the components in the application are executed by the
 * <code>TIRInterpreter</code> in order to initialize component fields and build the program's
 * heap.
 * <p/>
 * </p>
 * <code>Value</code> objects are immutable, but not cached. Reference equality is <b>not</b>
 * enough for a comparison of values.
 *
 * @author Ben L. Titzer
 * @see TIRInterpreter
 */
public abstract class Value {

    public static final Value BOTTOM = null;

    protected Value() {
    }

    public abstract Type getType();

    public static boolean compareValues(Value v1, Value v2) {
        if ( v1 == v2 ) return true;
        if ( v1 == BOTTOM ) return v2.equals(v1);
        else return v1.equals(v2);
    }

    public static class Equal extends Operator.Op2 {
        public Equal(Type t) {
            super(t, t, PrimBool.TYPE);
        }
        public Value apply2(Value v1, Value v2) {
            return PrimBool.toValue(compareValues(v1, v2));
        }
    }

    public static class NotEqual extends Operator.Op2 {
        public NotEqual(Type t) {
            super(t, t, PrimBool.TYPE);
        }
        public Value apply2(Value v1, Value v2) {
            return PrimBool.toValue(!compareValues(v1, v2));
        }
    }
}
