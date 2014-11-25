/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.types.*;
import vpc.util.Ovid;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

public class VSTTypeRef extends TypeRef implements VSTNode {

    public static final List<VSTTypeRef> NOPARAMSLIST = Ovid.newLinkedList();

    public AbstractToken token;

    public VSTTypeRef(AbstractToken tok, TypeCon tc, List<VSTTypeRef> params) {
        super(tok, tc, params);
        token = tok;
    }

    public VSTTypeRef(AbstractToken tok, Type tt) {
        super(tok, new TypeCon.Singleton(tt), null);
        token = tok;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public AbstractToken getToken() {
        return token;
    }
}
