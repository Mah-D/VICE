/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

public class VSTWhileStmt extends VSTBaseStmt {

    public VSTStmt body;
    public VSTExpr cond;

    public VSTWhileStmt(AbstractToken tok, VSTExpr e, VSTStmt s) {
        super(tok);
        cond = e;
        body = s;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }
}
