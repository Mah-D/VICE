/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created May 23, 2006
 */
package vpc.core.virgil;

import cck.util.Util;
import vpc.core.Heap;
import vpc.core.Value;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.dart.symc.SCValue;

/**
 * The <code>VirgilDelegate</code> class represents the delegate concept in
 * the Virgil language.
 *
 * @author Ben L. Titzer
 */
public class VirgilDelegate {
    /**
     * The <code>Val</code> class represents a value that is a reference to a method. This reference
     * can be to a component method (which does not include a "this" reference), or to an instance
     * method of some object (which does include a "this"--a reference to the record representing
     * the object).
     */
    public static class Val extends Value {
        public final Heap.Record record;
        public final Method method;
        public final Type[] typeEnv;

        public Val(Heap.Record r, Method m) {
            super();
            record = r;
            method = m;
            typeEnv = Type.NOTYPES;
        }

        public Type getType() {
            return method.getType();
        }

        public Val(Heap.Record r, Method m, Type[] te) {
            super();
            record = r;
            method = m;
            typeEnv = te;
        }

        public boolean equals(Object o) {
            if (o == Value.BOTTOM ) return record == null && method == null; // 0 == bottom
            if (!(o instanceof Val)) return false;
            Val meth = (Val) o;
            return meth.method == method && Heap.compareRecords(meth.record, record);
        }

        public String toString() {
            return "[" + Heap.recordName(record) + "," + method.getFullName() + "]";
        }
    }

    public static Val fromValue(Value v) {
    	v = SCValue.getConcreteValue(v); // get concrete value
        if ( v == Value.BOTTOM ) return new Val(null, null); // null (bottom) means null
        if ( v instanceof Val ) return (Val)v;
        throw Util.failure("Cannot convert " + v.getClass() + " value to delegate");
    }

    public static boolean isNull(Value v) {
        if (v == null) return true;
        if (v instanceof Val) return ((Val)v).method == null;
        return false;
    }

}
