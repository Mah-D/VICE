/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "expr1 = expr2"
 * <p/>
 * The <code>VSTAssign</code> class represents an assignment in the source program.
 * Note that it allows any arbitrary expression to be on the left hand side
 * of the assignment, as is currently allowed by the parser grammar. The verifier
 * must check that the left hand side of an assignment is a value Lvalue.
 *
 * @author Ben L. Titzer
 */
public class VSTAssign extends VSTExpr {

    public VSTExpr value;
    public VSTExpr target;

    public VSTAssign(AbstractToken tok, VSTExpr tar, VSTExpr val) {
        super(tok);
        target = tar;
        value = val;
    }

    public int getPrecedence() {
        return VSTExpr.PREC_ASSIGN;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

    public boolean isStmt() {
        return true;
    }

}
