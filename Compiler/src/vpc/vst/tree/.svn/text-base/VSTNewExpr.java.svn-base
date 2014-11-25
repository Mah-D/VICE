/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;

public abstract class VSTNewExpr extends VSTExpr {

    protected VSTNewExpr(AbstractToken tok) {
        super(tok);
    }

    public int getPrecedence() {
        return VSTExpr.PREC_NEW;
    }

    public boolean isStmt() {
        return true;
    }
}
