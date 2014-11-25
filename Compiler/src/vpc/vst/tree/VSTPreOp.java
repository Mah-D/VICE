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
 * The <code>VSTPreOp</code> class represents a pre-increment or
 * pre-decrement operation in the Virgil program.
 *
 * @author Ben L. Titzer
 */
public class VSTPreOp extends VSTExpr {

    public VSTExpr expr;
    public Capability.AutoOp autoop;

    public VSTPreOp(AbstractToken tok, VSTExpr e) {
        super(tok);
        expr = e;
    }

    public int getPrecedence() {
        return VSTExpr.PREC_PRE;
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

    public void setAutoOp(Capability.AutoOp b) {
        autoop = b;
    }

}
