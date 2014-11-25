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
 * The <code>VSTPostOp</code> class represents a post-increment or
 * post-decrement in the Virgil program.
 *
 * @author Ben L. Titzer
 */
public class VSTPostOp extends VSTExpr {

    public VSTExpr expr;
    public Capability.AutoOp autoop;

    public VSTPostOp(AbstractToken tok, VSTExpr e) {
        super(tok);
        expr = e;
    }

    public int getPrecedence() {
        return VSTExpr.PREC_POST;
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
