/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;


/**
 * The <code>VSTAppExpr</code> class represents a method application
 * in the program. A virtual method call then consists of a
 * MemberExpr embedded in an AppExpr.
 *
 * @author Ben L. Titzer
 */
public class VSTAppExpr extends VSTExpr {

    public VSTExpr func;
    public VSTTupleExpr args;

    public VSTAppExpr(VSTExpr e, VSTTupleExpr a) {
        super(e.getToken());
        func = e;
        args = a;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

    public int getPrecedence() {
        return VSTExpr.PREC_METH;
    }

    public boolean isStmt() {
        return true;
    }
}
