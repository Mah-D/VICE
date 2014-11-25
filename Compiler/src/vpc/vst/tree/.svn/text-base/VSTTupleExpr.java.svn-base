/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Oct 29, 2007
 */
package vpc.vst.tree;

import cck.parser.AbstractToken;
import cck.util.Util;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

/**
 * The <code>VSTAppExpr</code> class represents a method application
 * in the program. A virtual method call then consists of a
 * MemberExpr embedded in an AppExpr.
 *
 * @author Ben L. Titzer
 */
public class VSTTupleExpr extends VSTExpr {

    public List<VSTExpr> exprs;

    public VSTTupleExpr(AbstractToken t, List<VSTExpr> p) {
        super(t);
        exprs = p;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

    public int getPrecedence() {
        return VSTExpr.PREC_TUPLE;
    }

    public boolean isStmt() {
        return true;
    }
}
