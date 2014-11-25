/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;


/**
 * The <code>Switch</code> class represents a multi-way branch in
 * the program. Multi-way branches are restricted so that each case
 * must be of a constant, compile-time computable value.
 *
 * @author Ben L. Titzer
 */
public class TIRSwitch extends TIRExpr {
    public final TIRExpr expr;
    public final Case[] cases;
    public final Case defcase;

    /**
     * The <code>Case</code> inner class represents a case within
     * the a switch statement. It contains a <code>Const</code> that
     * represents that value of the case statement as well as a
     * <code>Block</code> that represents the body of the case.
     */
    public static class Case {
        public final TIRConst.Value[] value;
        public final TIRExpr body;

        public Case(TIRConst.Value[] v, TIRExpr b) {
            value = v;
            body = b;
        }
    }

    public TIRSwitch(TIRExpr e, Case[] c, Case def) {
        expr = e;
        cases = c;
        defcase = def;
    }

    public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
        return v.visit(this, env);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("$SWITCH(");
        buf.append(expr);
        buf.append(") {");
        for (Case c : cases) {
            buf.append(TIRUtil.exprsToString(c.value));
            buf.append(' ');
            buf.append(c.body);
            buf.append("; ");
        }
        if (defcase != null) {
            buf.append("[] ");
            buf.append(defcase.body);
        }
        buf.append("}");
        return buf.toString();
    }
}