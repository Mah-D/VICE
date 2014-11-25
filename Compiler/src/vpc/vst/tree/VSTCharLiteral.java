/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.Value;
import vpc.core.base.PrimChar;
import vpc.core.types.Type;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

/**
 * "'<char>'"
 * <p/>
 * The <code>VSTCharLiteral</code> class represents an occurrence of a character literal
 * in the original source program. The lexical token is stored in its superclass,
 * <code>VSTLiteral</code>, and the actual value of the character is stored in this
 * class.
 *
 * @author Ben L. Titzer
 */
public class VSTCharLiteral extends VSTLiteral {

    public final PrimChar.Val value;

    public VSTCharLiteral(AbstractToken tok) {
        super(tok);
        try {
            char lv = StringUtil.evaluateCharLiteral(tok.toString());
            value = PrimChar.toValue(lv);
        } catch (Exception e) {
            throw Util.unexpected(e);
        }
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
        return PrimChar.TYPE;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
