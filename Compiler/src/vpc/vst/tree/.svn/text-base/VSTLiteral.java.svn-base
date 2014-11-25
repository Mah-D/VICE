/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;

/**
 * The <code>VSTLiteral</code> class represents a literal in the abstract syntax tree
 * of the program.
 */
public abstract class VSTLiteral extends VSTExpr {

    protected VSTLiteral(AbstractToken tok) {
        super(tok);
        token = tok;
    }

    public int getPrecedence() {
        return VSTExpr.PREC_LIT;
    }

    public String toString() {
        return token.toString();
    }

    public boolean isComputable() {
        return true;
    }
}
