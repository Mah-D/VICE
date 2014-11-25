/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;

/**
 * The <code>VSTBaseStmt</code> class represents the basic implementation of the
 * <code>VSTStmt</code> interface that provides the most common behavior and
 * state needed for implementation abstract syntax tree statements.
 *
 * @author Ben L. Titzer
 */
public abstract class VSTBaseStmt implements VSTStmt {
    public AbstractToken token;

    protected VSTBaseStmt(AbstractToken tok) {
        token = tok;
    }

    public AbstractToken getToken() {
        return token;
    }

}
