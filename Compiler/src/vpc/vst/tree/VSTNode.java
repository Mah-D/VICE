/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTVisitor;

/**
 * The <code>VSTNode</code> interface represents the most basic functionality
 * provided by all nodes in the syntax tree: to accept a visitor and to retrieve
 * the lexical token that it corresponds to in the source program.
 *
 * @author Ben L. Titzer
 */
public interface VSTNode {

    public <E> void accept(VSTVisitor<E> v, E env);

    public AbstractToken getToken();

}
