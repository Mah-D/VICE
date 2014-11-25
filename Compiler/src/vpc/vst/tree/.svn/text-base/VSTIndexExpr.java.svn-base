/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "expr1[expr2]"
 * <p/>
 * The <code>VSTIndexExpr</code> class represents an occurrence of an index
 * operation in the original source program. An index operation can indicate
 * the access of an array element or the access of a single bit in an integer.
 *
 * @author Ben L. Titzer
 */
public class VSTIndexExpr extends VSTExpr {
    public VSTExpr array;
    public VSTExpr index;

    public VSTIndexExpr(AbstractToken tok, VSTExpr a, VSTExpr i) {
        super(tok);
        array = a;
        index = i;
    }

    public int getPrecedence() {
        return PREC_ELEM;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
