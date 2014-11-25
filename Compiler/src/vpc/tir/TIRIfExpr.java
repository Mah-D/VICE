/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;

import cck.text.StringUtil;


/**
 * The <code>IfStmt</code> class represents a two-way conditional
 * control structure in the program. It contains a condition that
 * is an expression and two blocks of statements, one for the true
 * case and one for the false case. Note that the tir IR stage
 * is still structured: there are no jumps, only blocks and control
 * structures.
 *
 * @author Ben L. Titzer
 */
public class TIRIfExpr extends TIRExpr {

    public TIRExpr condition;
    public TIRExpr false_target;
    public TIRExpr true_target;

    public TIRIfExpr(TIRExpr cond, TIRExpr t, TIRExpr f) {
        condition = cond;
        true_target = t;
        false_target = f;
    }

    public String toString() {
        return StringUtil.embed("$IF", condition, true_target, false_target);
    }

    public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
        return v.visit(this, env);
    }
}
