/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Mar 1, 2006
 */

package vpc.tir.opt;

import vpc.tir.*;
import vpc.tir.expr.Operator;

/**
 * The <code>AccessAnalyzer</code> visits the code of each method to extract the writes
 * to fields and use of methods.
 */
public class DepthFirstOpVisitor<R, E> extends TIRExprVisitor<R, E> {

    private Operator.Visitor<R, TIRExpr> opVisitor;

    public DepthFirstOpVisitor(Operator.Visitor<R, TIRExpr> a) {
        opVisitor = a;
    }

    public R visit(TIRExpr e, E env) {
        return null;
    }

    public R visit(TIROperator o, E env) {
        for (TIRExpr e : o.operands) e.accept(this, env);
        // call the appropriate visit method on the operator visitor
        return o.operator.accept(opVisitor, o.operands);
    }

    public R visit(TIRCall c, E env) {
        c.func.accept(this, env);
        for (TIRExpr e : c.arguments) e.accept(this, env);
        return null;
    }

    public R visit(TIRLocal.Set l, E env) {
        l.value.accept(this, env);
        return null;
    }

    public R visit(TIRReturn r, E env) {
        r.value.accept(this, env);
        return null;
    }

    public R visit(TIRIfExpr e, E env) {
        e.condition.accept(this, env);
        e.true_target.accept(this, env);
        e.false_target.accept(this, env);
        return null;
    }

    public R visit(TIRSwitch e, E env) {
        e.expr.accept(this, env);
        for (TIRSwitch.Case c : e.cases)
            c.body.accept(this, env);
        if (e.defcase != null) e.defcase.body.accept(this, env);
        return null;
    }

    public R visit(TIRBlock e, E env) {
        for (TIRExpr i : e.list) i.accept(this, env);
        return null;
    }
}
