/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.Value;
import vpc.core.types.Capability;
import vpc.tir.expr.Operator;
import vpc.tir.expr.Precedence;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "expr1 <op> expr2"
 * <p/>
 * The <code>VSTBinOp</code> class represents a binary infix operation that
 * appears in the source code of a Virgil program. For example, addition,
 * subtraction, multiplication, logical operators, etc, are BinOps. The
 * identity of each operation is stored internally as a character--there
 * aren't any fancy inner classes.
 *
 * @author Ben L. Titzer
 */
public class VSTBinOp extends VSTExpr {

    public VSTExpr left, right;
    public Capability.BinOp binop;

    public VSTBinOp(AbstractToken tok, VSTExpr l, VSTExpr r) {
        super(tok);
        left = l;
        right = r;
    }

    public boolean matches(String s) {
        return token.image.equals(s);
    }

    public boolean isComputable() {
        return left.isComputable() && right.isComputable();
    }

    public Value computeConstValue() throws Operator.Exception {
        return binop.binop.apply2(left.computeConstValue(), right.computeConstValue());
    }

    public String getOp() {
        return token.image;
    }

    public int getPrecedence() {
        return Precedence.get(binop.binop.getClass());
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

    public void setBinOp(Capability.BinOp t) {
        binop = t;
    }
}
