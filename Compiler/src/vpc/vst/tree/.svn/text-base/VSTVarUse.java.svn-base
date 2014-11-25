/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.types.Type;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

public class VSTVarUse extends VSTExpr {

    public VSTBinding binding;

    public VSTVarUse(AbstractToken tok) {
        super(tok);
    }

    public int getPrecedence() {
        return VSTExpr.PREC_VAR;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

    public String getName() {
        return getToken().toString();
    }

    public Type getType() {
        if ( binding == null ) return null;
        return binding.getType();
    }

    public void setType(Type t) {
        if ( binding != null ) binding.type = t;
        type = t;
    }
}
