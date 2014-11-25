/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.vst.visitor.VSTVisitor;

public class VSTModifier implements VSTNode {
    public AbstractToken token;

    public VSTModifier(AbstractToken tok) {
        token = tok;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        // do nothing.
    }

    public AbstractToken getToken() {
        return token;
    }
}
