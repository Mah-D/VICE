/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.decl.Field;
import vpc.core.types.Type;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

/**
 * "field name: type = value"
 * <p/>
 * The <code>VSTFieldDecl</code> class represents a field declaration in the abstract
 * syntax tree of the program.
 */
public class VSTFieldDecl extends VSTMemberDecl implements Field.FieldRep {

    public VSTExpr init;
    public VSTStmt assign;
    public final boolean isValue;

    public VSTFieldDecl(AbstractToken tok, boolean isValue, VSTTypeRef t, AbstractToken a, VSTExpr i, List<VSTModifier> l) {
        super(tok, l);
        this.isValue = isValue;
        memberType = t;
        init = i;
        if (a != null) assign = new VSTExprStmt(a, new VSTAssign(a, new VSTVarUse(tok), init));
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public Type getType() {
        return memberType.getType();
    }
}
