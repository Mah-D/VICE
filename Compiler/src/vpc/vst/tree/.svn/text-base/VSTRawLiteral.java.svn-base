/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Sep 12, 2006
 */
package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.Value;
import vpc.core.base.PrimRaw;
import vpc.core.types.Type;
import vpc.util.Maybe;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "0x9"
 * <p/>
 * The <code>VSTRawLiteral</code> class represents a raw literal
 * (i.e. a binary, octal, or hexadecimal constant) in the abstract
 * syntax tree of the program.
 */
public class VSTRawLiteral extends VSTLiteral {

    public final Maybe<PrimRaw.Val> value;

    public VSTRawLiteral(AbstractToken tok) {
        super(tok);
        value = PrimRaw.Converter.convert(tok);
    }

    public PrimRaw.Val getValue() {
        return value.getValue();
    }

    public Value computeConstValue() {
        return value.getValue();
    }

    public Type getType() {
        return value.getValue().getType();
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }
}
