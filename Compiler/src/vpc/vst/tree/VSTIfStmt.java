/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "if ( expr ) stmt else stmt"
 * <p/>
 * The <code>VSTIfStmt</code> class represents an if statement in the
 * abstract syntax tree of the program.
 */
public class VSTIfStmt extends VSTBaseStmt {

    public VSTExpr cond;
    public VSTStmt trueStmt;
    public VSTStmt falseStmt;

    public VSTIfStmt(AbstractToken tok, VSTExpr e, VSTStmt t, VSTStmt f) {
        super(tok);
        cond = e;
        trueStmt = t;
        falseStmt = f;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }
}
