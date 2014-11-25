/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.base.*;
import vpc.vst.visitor.VSTVisitor;

import java.util.LinkedList;
import java.util.List;

/**
 * constructor(args) { ... }
 * <p/>
 * The <code>VSTConstructorDecl</code> class represents a constructor declaration
 * in the abstract syntax tree of the program.
 *
 * @author Ben L. Titzer
 */
public class VSTConstructorDecl extends VSTMemberDecl {

    public List<VSTParamDecl> params;
    public VSTSuperClause supclause;
    public VSTBlock block;

    public VSTConstructorDecl(AbstractToken tok, VSTSuperClause sc, List<VSTParamDecl> p) {
        super(tok, null);
        supclause = sc;
        params = p;
        // create a new type ref for the member type.
        rebuildMemberType();
    }

    public void rebuildMemberType() {
        List<VSTTypeRef> tlist = new LinkedList<VSTTypeRef>();
        tlist.add(tupleRef(token, params));
        tlist.add(new VSTTypeRef(token, PrimVoid.TYPE));
        memberType = new VSTTypeRef(token, Function.TYPECON, tlist);
    }

    public void setBlock(VSTBlock b) {
        block = b;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }
}
