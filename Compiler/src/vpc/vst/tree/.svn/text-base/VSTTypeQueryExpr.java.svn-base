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
 * "expr instanceof type"
 * <p/>
 * The <code>VSTTypeQueryExpr</code> class represents an expression that
 * dynamically tests the type of an expression; the <code>instanceof</code>
 * operation.
 */
public class VSTTypeQueryExpr extends VSTExpr {
    public VSTExpr expr;
    public VSTTypeRef type;

    public VSTTypeQueryExpr(AbstractToken tok, VSTExpr e, VSTTypeRef t) {
        super(tok);
        expr = e;
        type = t;
    }

    public int getPrecedence() {
        return VSTExpr.PREC_TYPEQUERY;
    }

    public Type getType() {
        return PrimBool.TYPE;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
