/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.Value;
import vpc.core.base.PrimBool;
import vpc.core.types.Type;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "true | false"
 * <p/>
 * The <code>VSTBooleanLiteral</code> class represents a boolean literal (a "true"
 * or a "false") that appears in the original source program. Its superclass,
 * <code>VSTLiteral</code>, stores the actual token, while this class stores the
 * actual boolean value in a field.
 *
 * @author Ben L. Titzer
 */
public class VSTBoolLiteral extends VSTLiteral {

    public final PrimBool.Val value;

    public VSTBoolLiteral(AbstractToken tok) {
        super(tok);
        value = PrimBool.toValue("true".equals(tok.toString()));
    }

    public Value getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public Value computeConstValue() {
        return value;
    }

    public Type getType() {
        return PrimBool.TYPE;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
