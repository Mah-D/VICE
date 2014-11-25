/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import cck.parser.SourcePoint;
import vpc.core.types.Type;
import vpc.vst.visitor.VSTVisitor;

public class VSTParamDecl implements VSTVarDecl {

    public AbstractToken token;
    public VSTFieldDecl field;
    public VSTTypeRef type;

    public VSTParamDecl(AbstractToken tok, VSTTypeRef t) {
        token = tok;
        type = t;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public VSTTypeRef getTypeRef() {
        return type;
    }

    public AbstractToken getToken() {
        return token;
    }

    public SourcePoint getSourcePoint() {
        return token.getSourcePoint();
    }

    public String getName() {
        return token.toString();
    }

    public Type getType() {
        return type.getType();
    }

    public int hashCode() {
        return token.image.hashCode();
    }

    public boolean equals(Object o) {
        return this == o;
    }

}
