/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.base.PrimBool;
import vpc.core.types.Type;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * expr1 == expr2
 * <p/>
 * The <code>VSTComparison</code> is a representation of a comparison between
 * two values in the program.
 *
 * @author Ben L. Titzer
 */
public class VSTComparison extends VSTExpr {

    public VSTExpr left, right;

    public VSTComparison(AbstractToken tok, VSTExpr l, VSTExpr r) {
        super(tok);
        left = l;
        right = r;
    }

    public int getPrecedence() {
        return PREC_EQ;
    }

    public boolean isComputable() {
        return left.isComputable() && right.isComputable();
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public Type getType() {
        return PrimBool.TYPE;
    }

    public boolean isEqOp() {
        return "==".equals(token.image);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }
}
