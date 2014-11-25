/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

public class VSTTernaryExpr extends VSTExpr {
    public VSTExpr cond;
    public VSTExpr trueExpr;
    public VSTExpr falseExpr;

    public VSTTernaryExpr(AbstractToken tok, VSTExpr c, VSTExpr t, VSTExpr f) {
        super(tok);
        cond = c;
        trueExpr = t;
        falseExpr = f;
    }

    public int getPrecedence() {
        return VSTExpr.PREC_TERNARY;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}