/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

/**
 * "for ( expr*; expr; expr* ) s"
 * <p/>
 * The <code>VSTForStmt</code> class represents a for loop in the abstract syntax
 * tree of the program.
 */
public class VSTForStmt extends VSTBaseStmt {

    public List<VSTExpr> init;
    public VSTExpr cond;
    public List<VSTExpr> update;
    public VSTStmt body;

    public VSTForStmt(AbstractToken tok, List<VSTExpr> init, VSTExpr c, List<VSTExpr> update, VSTStmt b) {
        super(tok);
        this.init = init;
        cond = c;
        this.update = update;
        body = b;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }
}
