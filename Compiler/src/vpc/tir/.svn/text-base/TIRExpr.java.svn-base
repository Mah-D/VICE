/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;

import cck.parser.SourcePoint;
import vpc.core.types.Type;

/**
 * The <code>Expr</code> interface represents any expression in the
 * program that is considered to have a value. Results of method calls,
 * arithmetic, fields, etc. Note that there is a strict separation
 * in TIR design between expressions (program elements that
 * have a value) and statements (program elements that do not have a
 * value). While local variables and arithmetic expressions have values,
 * loops and conditionals do not.
 *
 * @author Ben L. Titzer
 */
public abstract class TIRExpr {

    public static final TIRExpr[] EMPTY = new TIRExpr[0];

    protected Type type;

    protected SourcePoint point;

    public abstract <R, E> R accept(TIRExprVisitor<R, E> v, E env);

    public Type getType() {
        return type;
    }

    public void setType(Type t) {
        type = t;
    }

    public SourcePoint getSourcePoint() {
        return point;
    }

    public void setSourcePoint(SourcePoint p) {
        point = p;
    }
}
