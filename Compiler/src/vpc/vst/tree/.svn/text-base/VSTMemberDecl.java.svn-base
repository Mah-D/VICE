/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.decl.Method;
import vpc.core.types.Mode;
import vpc.core.base.Tuple;
import vpc.util.Ovid;

import java.util.List;
import java.util.LinkedList;

/**
 * The <code>VSTMemberDecl</code> class represents a member declaration,
 * such as a field, method, or constructor, within the abstract syntax tree
 * of the program.
 */
public abstract class VSTMemberDecl implements VSTNode, Method.MethodRep {
    public AbstractToken token;
    public List<VSTModifier> modifiers;
    public Mode mode;
    public VSTTypeRef memberType;

    protected VSTMemberDecl(AbstractToken tok, List<VSTModifier> list) {
        token = tok;
        if (list == null) modifiers = Ovid.newLinkedList();
        else modifiers = list;
    }

    public String getName() {
        return token.toString();
    }

    public void setMode(Mode m) {
        mode = m;
    }

    public AbstractToken getToken() {
        return token;
    }

    public Mode getMode() {
        return mode;
    }

    protected VSTTypeRef tupleRef(AbstractToken tok, List<VSTParamDecl> p) {
        List<VSTTypeRef> tlist = new LinkedList<VSTTypeRef>();
        for (VSTParamDecl pd : p) tlist.add(pd.getTypeRef());
        return new VSTTypeRef(tok, Tuple.TYPECON, tlist);
    }
}
