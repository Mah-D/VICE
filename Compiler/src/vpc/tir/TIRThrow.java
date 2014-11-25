/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 8, 2007
 */

package vpc.tir;

import cck.parser.SourcePoint;
import cck.text.StringUtil;
import vpc.tir.expr.Operator;

/**
 * The <code>TIRThrow</code> represents throw statements with TIR code.
 *
 * @author Ben L. Titzer
 */
public class TIRThrow extends TIRExpr {

    public final Class<? extends Operator.Exception> exception;
    public final SourcePoint point;

    public TIRThrow(Class<? extends Operator.Exception> except, SourcePoint pt) {
        exception = except;
        point = pt;
        setSourcePoint(pt);
    }

    public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
        return v.visit(this, env);
    }

    public String toString() {
        return StringUtil.embed("$THROW", exception);
    }

}
