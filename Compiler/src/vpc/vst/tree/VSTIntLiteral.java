/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.Value;
import vpc.core.base.PrimInt32;
import vpc.core.types.Type;
import vpc.util.Maybe;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "0"
 * <p/>
 * The <code>VSTIntLiteral</code> class represents an integer literal
 * in the abstract syntax tree of the program.
 */
public class VSTIntLiteral extends VSTLiteral {

    public final AbstractToken minus;
    public final Maybe<PrimInt32.Val> value;

    public VSTIntLiteral(AbstractToken neg, AbstractToken tok) {
        super(tok);
        minus = neg;
        if ( minus != null ) {
            value = PrimInt32.Converter.convertDecimal(true, tok);
        } else {
            value = PrimInt32.Converter.convertDecimal(tok);
        }
    }

    public String toString() {
        if (isNegative()) return "-" + token.image;
        else return token.image;
    }

    public boolean isNegative() {
        return minus != null;
    }

    public PrimInt32.Val getValue() {
        return value.getValue();
    }

    public Value computeConstValue() {
        return value.getValue();
    }

    public Type getType() {
        return PrimInt32.TYPE;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }
}
