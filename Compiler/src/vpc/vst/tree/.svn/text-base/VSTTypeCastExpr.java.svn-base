/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Aug 25, 2006
 */
package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "expr :: type"
 * <p/>
 * The <code>VSTTypeQueryExpr</code> class represents an expression that
 * dynamically queries the type of an expression, which is the equivalent of the
 * <code>instanceof</code> operation in Java.
 */
public class VSTTypeCastExpr extends VSTExpr {
    public VSTExpr expr;
    public VSTTypeRef type;

    public VSTTypeCastExpr(AbstractToken tok, VSTExpr e, VSTTypeRef t) {
        super(tok);
        expr = e;
        type = t;
    }

    public int getPrecedence() {
        return VSTExpr.PREC_TYPECAST;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
