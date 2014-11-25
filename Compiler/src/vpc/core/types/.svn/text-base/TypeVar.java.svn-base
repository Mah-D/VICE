/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Feb 27, 2007
 */
package vpc.core.types;

import cck.parser.SourcePoint;

/**
 * The <code>TypeVar</code> class implements a type variable that is used for
 * substitutions in formulas and unification in type inference algorithms.
 *
 * @author Ben L. Titzer
 */
public class TypeVar extends Type {

    public final SourcePoint usePoint;
    public final TypeParam paramDecl;

    public Type binding;

    public TypeVar(SourcePoint pt, TypeParam decl) {
        super("?");
        usePoint = pt;
        paramDecl = decl;
    }

    public Type[] elements() {
        if ( binding != null ) return binding.elements();
        return Type.NOTYPES;
    }

    public Type rebuild(Type[] ne) {
        if ( binding != null ) return binding.rebuild(ne);
        return this;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    public boolean equals(Object o) {
        return o == this;
    }

}
