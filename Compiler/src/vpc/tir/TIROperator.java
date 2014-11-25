/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Mar 24, 2006
 */

package vpc.tir;

import cck.text.StringUtil;
import vpc.tir.expr.Operator;

/**
 * The <code>TIROperator</code> class in the IR represents an operator applied
 * to a list of values. An operator can be a primitive binary operator such as
 * addition, or could be an object operator like <code>get_field</code>
 * or <code>get_method</code>.
 *
 * @author Ben L. Titzer
 */
public class TIROperator extends TIRExpr {

    public final Operator operator;
    public final TIRExpr[] operands;

    public TIROperator(Operator o, TIRExpr... args) {
        operator = o;
        if (args == null) operands = TIRExpr.EMPTY;
        else operands = args;
        setType(o.getResultType());
    }

    public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
        return v.visit(this, env);
    }

    public String toString() {
        return StringUtil.embed("$OP", operator, TIRUtil.exprsToString(operands));
    }
}
