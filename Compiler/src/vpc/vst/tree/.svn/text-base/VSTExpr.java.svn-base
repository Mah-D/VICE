/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import cck.util.Util;
import vpc.core.Value;
import vpc.core.types.Type;
import vpc.tir.expr.Operator;
import vpc.vst.visitor.VSTAccumulator;

/**
 * The <code>VSTExpr</code> interface represents an expression in the syntax
 * tree representation of the program. It contains methods that accept visitors,
 * test various properties, and allow types to be applied to expressions.
 */
public abstract class VSTExpr implements VSTNode {

    public static final int PREC_ASSIGN = 1;
    public static final int PREC_TERNARY = 2;
    public static final int PREC_COND_OR = 3;
    public static final int PREC_COND_AND = 4;
    public static final int PREC_TYPECAST = 5;
    public static final int PREC_OR = 5;
    public static final int PREC_XOR = 5;
    public static final int PREC_AND = 6;
    public static final int PREC_EQ = 7;
    public static final int PREC_TYPEQUERY = 8;
    public static final int PREC_REL = 9;
    public static final int PREC_SHIFT = 10;
    public static final int PREC_ADD = 11;
    public static final int PREC_MUL = 12;
    public static final int PREC_POST = 13;
    public static final int PREC_UNARY = 14;
    public static final int PREC_PRE = 15;
    public static final int PREC_FIELD = 16;
    public static final int PREC_ELEM = 16;
    public static final int PREC_METH = 16;

    public static final int PREC_NEW = 17;
    public static final int PREC_VAR = 17;
    public static final int PREC_LIT = 17;
    public static final int PREC_INIT = 17;
    public static final int PREC_TUPLE = 18;

    public AbstractToken token;
    public Type type;
    public Type promotionType;

    public abstract int getPrecedence();

    public abstract <V> V accept(VSTAccumulator<V> a);

    protected VSTExpr(AbstractToken tok) {
        token = tok;
    }

    public void setType(Type t) {
        type = t;
    }

    public Type getType() {
        if (type == null)
            throw Util.failure("node without type: " + getClass() + " @ " + token.getSourcePoint());
        return type;
    }

    public void setPromotionType(Type t) {
        promotionType = t;
    }

    public Type getPromotionType() {
        return promotionType;
    }

    public boolean isStmt() {
        return false;
    }

    public boolean isComputable() {
        return false;
    }

    public Value computeConstValue() throws Operator.Exception {
        throw Util.failure("not computable");
    }

    public AbstractToken getToken() {
        return token;
    }
}
