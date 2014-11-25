/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;

import cck.text.StringUtil;
import vpc.core.base.Reference;
import vpc.core.types.Type;

/**
 * The <code>Const</code> class in TIR represents an integer,
 * character, or string constant that is known at compile time.
 *
 * @author Ben L. Titzer
 */
public abstract class TIRConst extends TIRExpr {

    /**
     * The <code>Value</code> inner class represents a constant in the
     * program (either source level or computed statically by the
     * compiler) that is a value of a particular type.
     */
    public static class Value extends TIRConst {
        protected final vpc.core.Value value;
        public Value(vpc.core.Value v) {
            value = v;
            if ( v == vpc.core.Value.BOTTOM ) setType(Reference.NULL_TYPE);
            else setType(v.getType());
        }

        public Value(Type t, vpc.core.Value v) {
            value = v;
            setType(t);
        }

        public vpc.core.Value getValue() {
            return value;
        }

        public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
            return v.visit(this, env);
        }

        public String toString() {
            return StringUtil.embed("$VAL", String.valueOf(value));
        }
    }

    /**
     * The <code>Symbol</code> inner class represents a string constant
     * present in the program.
     */
    public static class Symbol extends TIRConst {
        protected String orig;
        protected String value;

        public Symbol(String o, String v) {
            orig = o;
            value = v;
        }

        public String value() {
            return value;
        }

        public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
            return v.visit(this, env);
        }

        public String toString() {
            return orig;
        }
    }
}
