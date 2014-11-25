/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.types.TypeEnv;
import vpc.core.types.TypeParam;
import vpc.core.virgil.VirgilClass;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

/**
 * "class { ... }"
 * <p/>
 * The <code>VSTClassDecl</code> class represents the abstract syntax tree of the
 * entire class declaration in the source program. It contains a list of the
 * fields that were declared and the methods that were declared.
 *
 * @author Ben L. Titzer
 */
public class VSTClassDecl extends VSTTypeDecl implements VirgilClass.SourceRep {

    public VSTTypeRef parent;
    public List<TypeParam> typeParams;

    public VSTClassDecl(VSTModule m, AbstractToken tok, List<TypeParam> tpl, TypeEnv te, VSTTypeRef p) {
        super(m, tok, te);
        typeParams = tpl;
        parent = p;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }
}
