/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.types.Capability;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "expr1 <op>= expr2"
 * <p/>
 * The <code>CompoundAssign</code> class represents an occurrence of a compound
 * assignment expression in the source program. As with the <code>Assign</code>
 * class, this class does not enforce by construction that its left hand side
 * be an Lvalue, and thus this must be done by the verifier.
 *
 * @author Ben L. Titzer
 */
public class VSTCompoundAssign extends VSTExpr {

    public VSTExpr target;
    public VSTExpr value;
    public Capability.BinOp binop;

    public VSTCompoundAssign(AbstractToken tok, VSTExpr tar, VSTExpr val) {
        super(tok);
        target = tar;
        value = val;
    }

    public int getPrecedence() {
        return PREC_ASSIGN;
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

    public void setBinOp(Capability.BinOp b) {
        binop = b;
    }

}
