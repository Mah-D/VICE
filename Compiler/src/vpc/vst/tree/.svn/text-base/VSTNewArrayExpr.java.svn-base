/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

public class VSTNewArrayExpr extends VSTNewExpr {
    public VSTTypeRef type;
    public List<VSTExpr> list;

    public VSTNewArrayExpr(AbstractToken tok, VSTTypeRef c, List<VSTExpr> d) {
        super(tok);
        type = c;
        list = d;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
