/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * ";"
 * <p/>
 * The <code>VSTEmptyStmt</code> class represents an occurrence of the empty statement
 * within a source program. It is useful for conditionals' branches and loops'
 * bodies that are empty in the source program. Note that this is the only class
 * to return true for its <code>isEmpty</code> method.
 *
 * @author Ben L. Titzer
 */
public class VSTEmptyStmt extends VSTBaseStmt {

    public VSTEmptyStmt(AbstractToken tok) {
        super(tok);
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }


}
