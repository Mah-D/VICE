/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.Value;
import vpc.core.types.Capability;
import vpc.tir.expr.Operator;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

public class VSTUnaryOp extends VSTExpr {
    public VSTExpr expr;
    public Capability.UnaryOp unaryop;

    public VSTUnaryOp(AbstractToken tok, VSTExpr e) {
        super(tok);
        expr = e;
    }

    public int getPrecedence() {
        return VSTExpr.PREC_UNARY;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

    public void setUnaryOp(Capability.UnaryOp o) {
        unaryop = o;
    }

    public boolean isComputable() {
        return expr.isComputable();
    }

    public Value computeConstValue() throws Operator.Exception {
        return unaryop.unop.apply1(expr.computeConstValue());
    }
}
