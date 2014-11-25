/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.types.TypeEnv;
import vpc.core.virgil.VirgilComponent;
import vpc.vst.visitor.VSTVisitor;

/**
 * "component { ... }"
 * <p/>
 * The <code>VSTComponentDecl</code> class represents the entire abstract syntax tree
 * of a component declaration in the original source program. It contains a list
 * of the fields and the methods that were declared in the component.
 *
 * @author Ben L. Titzer
 */
public class VSTComponentDecl extends VSTTypeDecl implements VirgilComponent.SourceRep {

    public VSTComponentDecl(VSTModule m, AbstractToken tok, TypeEnv te) {
        super(m, tok, te);
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

}
