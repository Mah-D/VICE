/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

public class VSTSwitchCase extends VSTBaseStmt {
    public List<VSTExpr> list;
    public VSTStmt stmt;

    public VSTSwitchCase(AbstractToken tok, List<VSTExpr> l, VSTStmt s) {
        super(tok);
        list = l;
        stmt = s;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
