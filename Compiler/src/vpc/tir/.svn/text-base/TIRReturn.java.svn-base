/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;

import cck.text.StringUtil;

/**
 * The <code>Return</code> class represents a return from a method in
 * the program. The inner classes represent the two types of returns:
 * void and non-void.
 *
 * @author Ben L. Titzer
 */
public class TIRReturn extends TIRExpr {

    public final TIRExpr value;

    public TIRReturn(TIRExpr val) {
        value = val;
    }

    public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
        return v.visit(this, env);
    }

    public String toString() {
        return StringUtil.embed("$RET", value);
    }
}
