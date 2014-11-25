/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

/**
 * "{expr1, expr2, ..., exprN}"
 * <p/>
 * The <code>VSTArrayInitializer</code> class represents an array initializer
 * encountered in the source program. It contains a <code>ListExpr</code>
 * that is a list of the expressions that occurred inside the initializer.
 *
 * @author Ben L. Titzer
 */
public class VSTArrayInitializer extends VSTExpr {

    public List<VSTExpr> list;

    public VSTArrayInitializer(AbstractToken tok, List<VSTExpr> l) {
        super(tok);
        list = l;
    }

    public int getPrecedence() {
        return VSTExpr.PREC_INIT;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }
}
