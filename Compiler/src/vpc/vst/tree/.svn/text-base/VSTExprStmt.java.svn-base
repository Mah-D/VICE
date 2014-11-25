/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * The <code>VSTExprStmt</code> class represents a statement that encapsulates
 * an expression. Since some expressions can exist as their own statement, such
 * as <code>"x++"</code>, this node allows those expressions to fit into the
 * abstract syntax tree as a standalone statement.
 */
public class VSTExprStmt implements VSTStmt {
    public VSTExpr expr;
    public AbstractToken token;

    public VSTExprStmt(AbstractToken tok, VSTExpr e) {
        token = tok;
        expr = e;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

    public AbstractToken getToken() {
        return token;
    }

}
