/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import cck.parser.SourcePoint;
import vpc.core.types.Type;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "local name: type = init;
 * <p/>
 * The <code>VSTLocalVarDecl</code> class represents a local variable declaration
 * in the abstract syntax tree of the program.
 */
public class VSTLocalVarDecl extends VSTBaseStmt implements VSTVarDecl {
    public VSTTypeRef typeRef;
    public VSTExpr init;

    public VSTLocalVarDecl(AbstractToken tok, VSTTypeRef t, VSTExpr i) {
        super(tok);
        typeRef = t;
        init = i;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

    public SourcePoint getSourcePoint() {
        return token.getSourcePoint();
    }

    public String getName() {
        return getToken().toString();
    }

    public Type getType() {
        return typeRef.getType();
    }

    public int hashCode() {
        return token.image.hashCode();
    }

    public boolean equals(Object o) {
        return this == o;
    }
}
