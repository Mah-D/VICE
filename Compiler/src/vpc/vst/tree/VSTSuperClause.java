/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.decl.Constructor;
import vpc.core.decl.Member;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

public class VSTSuperClause extends VSTBaseStmt {

    public VSTTupleExpr args;
    public Member.Ref<Constructor> target;

    public VSTSuperClause(AbstractToken tok, VSTTupleExpr p) {
        super(tok);
        args = p;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
