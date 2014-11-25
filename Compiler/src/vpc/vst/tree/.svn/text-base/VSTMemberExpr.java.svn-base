/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.types.Type;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "expr.member"
 * <p/>
 * The <code>VSTMemberExpr</code> class represents an access of a named
 * member of an expression in the abstract syntax tree of the program.
 */
public class VSTMemberExpr extends VSTExpr {

    public VSTExpr expr;
    public VSTBinding binding;

    public VSTMemberExpr(AbstractToken tok, VSTExpr e) {
        super(tok);
        expr = e;
    }

    public int getPrecedence() {
        return VSTExpr.PREC_FIELD;
    }

    public String getMemberName() {
        return token.toString();
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

    public Type getType() {
        if ( binding == null ) return null ;
        return binding.getType();
    }

    public void setType(Type t) {
        if ( binding != null ) binding.type = t;
        else super.setType(t);
    }
}
