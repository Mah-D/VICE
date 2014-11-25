/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "break"
 * <p/>
 * The <code>VSTBreakStmt</code> class represents an occurence of a break statement
 * in the original source program. It simply contains a reference to the lexical
 * token and serves no other purpose than completeness and double-dispatch.
 *
 * @author Ben L. Titzer
 */
public class VSTBreakStmt extends VSTBaseStmt {

    public VSTBreakStmt(AbstractToken tok) {
        super(tok);
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }


}
