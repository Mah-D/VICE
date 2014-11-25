/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "do { ... } while ( expr1 );"
 * <p/>
 * The <code>VSTDoWhileStmt</code> class represents an occurrence of a do...while
 * loop within the source program. It contains only an expression that indicates
 * the condition which is assumed to be non-null and also a <code>Stmt</code>
 * that is also assumed to be non-null.
 *
 * @author Ben L. Titzer
 */
public class VSTDoWhileStmt extends VSTBaseStmt {

    public VSTStmt body;
    public VSTExpr cond;

    public VSTDoWhileStmt(AbstractToken tok, VSTExpr e, VSTStmt s) {
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
