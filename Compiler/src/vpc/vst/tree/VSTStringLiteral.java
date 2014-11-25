/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

public class VSTStringLiteral extends VSTLiteral {

    public final String value;

    public VSTStringLiteral(AbstractToken tok) {
        super(tok);
        try {
            value = StringUtil.evaluateStringLiteral(tok.toString());
        } catch (Exception e) {
            throw Util.unexpected(e);
        }
    }

    public String original() {
        return token.image;
    }

    public String value() {
        return value;
    }

    public String toString() {
        return value;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
