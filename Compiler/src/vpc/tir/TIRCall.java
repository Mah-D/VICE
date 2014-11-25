/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;

import cck.text.StringUtil;
import vpc.core.base.Callable;

/**
 * The <code>TIRCall</code> class in the IR represents a call to a method.
 *
 * @author Ben L. Titzer
 */
public class TIRCall extends TIRExpr {

    public final boolean delegate;
    public final TIRExpr func;
    public final TIRExpr[] arguments;

    public TIRCall(boolean b, TIRExpr e, TIRExpr[] args) {
        delegate = b;
        func = e;
        if (args == null) arguments = TIRExpr.EMPTY;
        else arguments = args;
        setType(((Callable)e.getType()).getResultType());
    }

    public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
        return v.visit(this, env);
    }

    public String toString() {
        return StringUtil.embed("$CALL", func, TIRUtil.exprsToString(arguments));
    }
}
