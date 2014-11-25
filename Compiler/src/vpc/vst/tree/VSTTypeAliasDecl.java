/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Dec 1, 2007
 */
package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.types.TypeEnv;
import vpc.core.types.TypeRef;
import vpc.vst.visitor.VSTVisitor;

/**
 * The <code>VSTTypeAliasDecl</code> definition.
 *
 * @author Ben L. Titzer
 */
public class VSTTypeAliasDecl extends VSTTypeDecl {

    public final TypeRef typeAlias;

    public VSTTypeAliasDecl(VSTModule m, AbstractToken tok, TypeEnv te, VSTTypeRef tref) {
        super(m, tok, te);
        typeAlias = tref;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

}
