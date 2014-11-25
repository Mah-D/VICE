/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.base.Reference;
import vpc.core.types.Type;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

public class VSTNullLiteral extends VSTLiteral {

    public VSTNullLiteral(AbstractToken tok) {
        super(tok);
    }

    public Type getType() {
        return Reference.NULL_TYPE;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
