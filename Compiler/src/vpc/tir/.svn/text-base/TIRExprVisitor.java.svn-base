/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;

/**
 * The <code>TIRExprVisitor</code> class implements a visitor for expressions
 * in TIR code.
 *
 * @author Ben L. Titzer
 */
public abstract class TIRExprVisitor<R, E> {

    public abstract R visit(TIRExpr e, E env);

    public R visit(TIROperator b, E env) {
        return visit((TIRExpr) b, env);
    }

    public R visit(TIRConst c, E env) {
        return visit((TIRExpr) c, env);
    }

    public R visit(TIRConst.Value c, E env) {
        return visit((TIRConst) c, env);
    }

    public R visit(TIRConst.Symbol c, E env) {
        return visit((TIRConst) c, env);
    }

    public R visit(TIRLocal r, E env) {
        return visit((TIRExpr) r, env);
    }

    public R visit(TIRLocal.Get r, E env) {
        return visit((TIRLocal) r, env);
    }

    public R visit(TIRLocal.Set l, E env) {
        return visit((TIRLocal) l, env);
    }

    public R visit(TIRCall c, E env) {
        return visit((TIRExpr) c, env);
    }

    public R visit(TIRThrow c, E env) {
        return visit((TIRExpr) c, env);
    }

    public R visit(TIRReturn r, E env) {
        return visit((TIRExpr) r, env);
    }

    public R visit(TIRIfExpr e, E env) {
        return visit((TIRExpr) e, env);
    }

    public R visit(TIRSwitch e, E env) {
        return visit((TIRExpr) e, env);
    }

    public R visit(TIRBlock e, E env) {
        return visit((TIRExpr) e, env);
    }

    public R visit(TIRBlock.Break e, E env) {
        return visit((TIRExpr) e, env);
    }

    public R visit(TIRBlock.Continue e, E env) {
        return visit((TIRExpr) e, env);
    }
}
