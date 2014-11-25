/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.base.Function;
import vpc.core.types.TypeEnv;
import vpc.core.types.TypeParam;
import vpc.vst.visitor.VSTVisitor;

import java.util.LinkedList;
import java.util.List;

/**
 * The <code>VSTMethodDecl</code> class represents a method declaration in the
 * source program. It contains the name of the method, a list of the method
 * modifiers (e.g. private, final), the parameters, and the return type.
 * It optionally contains a method body consisting of block of <code>VSTStmt</code>
 * instances.
 *
 * @author Ben L. Titzer
 */
public class VSTMethodDecl extends VSTMemberDecl {

    public VSTTypeRef returnType;
    public List<VSTParamDecl> params;
    public VSTBlock block;
    public TypeEnv typeEnv;
    public List<TypeParam> typeParams;

    public VSTMethodDecl(AbstractToken tok, List<VSTModifier> l, List<TypeParam> tpl, TypeEnv te, List<VSTParamDecl> p, VSTTypeRef rt) {
        super(tok, l);
        returnType = rt;
        typeParams = tpl;
        params = p;
        typeEnv = te;
        // create a new type ref for the member type.
        List<VSTTypeRef> tlist = new LinkedList<VSTTypeRef>();
        tlist.add(tupleRef(tok, p));
        tlist.add(rt);
        memberType = new VSTTypeRef(tok, Function.TYPECON, tlist);
    }

    public void setBlock(VSTBlock b) {
        block = b;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

}
