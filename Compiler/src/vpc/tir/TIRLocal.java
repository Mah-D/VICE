/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;

import cck.text.StringUtil;
import vpc.core.decl.Method;

/**
 * The <code>TIRLocal</code> class and its inner classes represent reads
 * and writes to local variables in TIR code.
 *
 * @author Ben L. Titzer
 */
public abstract class TIRLocal extends TIRExpr {

    public final Method.Temporary temp;

    protected TIRLocal(Method.Temporary t) {
        temp = t;
    }

    /**
     * The <code>Local</code> inner class represents a read from a local
     * in the original program. It contains a <code>Method.Temporary</code>
     * that indicates which temporary to read.
     */
    public static class Get extends TIRLocal {
        public Get(Method.Temporary t) {
            super(t);
            setType(t.getType());
        }

        public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
            return v.visit(this, env);
        }

        public String toString() {
            return StringUtil.embed("$GET", temp);
        }
    }

    /**
     * The <code>Local</code> inner class represents a write to a local
     * variable in the program. It contains a <code>Method.Temporary</code>
     * that represents the temporary to write.
     */
    public static class Set extends TIRLocal {
        public final TIRExpr value;

        public Set(Method.Temporary t, TIRExpr val) {
            super(t);
            value = val;
            setType(t.getType());
        }

        public String toString() {
            return StringUtil.embed("$SET", temp, value);
        }

        public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
            return v.visit(this, env);
        }
    }
}
