/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 9, 2006
 */

package vpc.core.types;

import cck.parser.AbstractToken;
import cck.parser.SourcePoint;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.util.Cache;
import vpc.util.Ovid;

import java.util.List;

/**
 * The <code>TypeRef</code> class represents a reference to a type that may
 * or may not have been resolved yet.
 *
 * @author Ben L. Titzer
 */
public class TypeRef implements TypeToken {

    public static final List<TypeRef> NOPARAMSLIST = Ovid.emptyList();
    public static final TypeRef[] NOPARAMS = new TypeRef[0];

    protected final String name;
    protected final SourcePoint point;
    protected final List<? extends TypeRef> params;
    protected TypeCon tycon;
    protected Type type;

    /**
     * The constructor for the <code>TypeRef</code> class creates a new type reference
     * with the specified token, which indicates the source point and the name of the type
     * reference.
     *
     * @param rn the token representing the type reference's name and/or source position
     */
    public TypeRef(AbstractToken rn, TypeCon tc, List<? extends TypeRef> p) {
        name = tc != null ? tc.name : rn.image;
        point = rn.getSourcePoint();
        tycon = tc;
        params = p == null ? NOPARAMSLIST : p;
    }

    /**
     * The <code>resolveType()</code> method attempts to resolve this type reference
     * within the specified type environment. If this type is already resolved, this
     * method will simply return the currently resolved type (i.e. this method will
     * not attempt to resolve the type again in the new environment). If this
     * method fails to resolve this type or one of its nested type references, it will
     * throw the <code>Unresolved</code> exception.
     * @param cache the type cache used to enforce referential equality for types
     * @param te the type environment in which to resolve type constructors
     * @return the type to which this reference refers, if it can be resolved
     * @throws Unresolved if this type or one of the nested types cannot be resolved
     * in the specified type environment
     * @throws ArityMismatch if this type or one of the nested types does not
     * match the arity (number of arguments) to the associated type constructors
     */
    public Type resolveType(Cache<Type> cache, TypeEnv te) throws Unresolved, ArityMismatch {
        if ( type != null ) return type;
        TypeCon tc = resolveTypeCon(te);
        if (tc == null) throw new Unresolved(name, point);
        Type[] params = resolveNestedTypeRefs(cache, te);
        return newType(tc, cache, params);
    }

    public Type rebuildType(Cache<Type> cache) {
        assert tycon != null;
        Type[] tp;
        if (params.isEmpty() ) {
            tp = Type.NOTYPES;
        } else {
            tp = new Type[params.size()];
            for ( int cntr = 0; cntr < tp.length; cntr++ ) {
                tp[cntr] = params.get(cntr).rebuildType(cache);
            }
        }
        return tycon.newType(cache, tp);
    }

    /**
     * The <code>resolveTypeCon</code> method attempts to resolve this type reference's
     * type constructor within the specified type environment. This method will not
     * attempt to construct a new type, and will not resolve type constructors for
     * nested type references.
     * @param te the type environment in which to resolve the type constructor
     * @return the type constructor if it can be resolved
     * @throws Unresolved if this type reference's type constructor cannot be resolved
     */
    public TypeCon resolveTypeCon(TypeEnv te) throws Unresolved {
        if ( tycon != null ) return tycon;
        return tycon = te.resolveTypeCon(name);
    }

    /**
     * The <code>resolveNestedTypeRefs()</code> method attempts to resolve the nested type
     * references of this type reference.
     * @param cache the type cache that should be used to enforce referential equality
     * @param te the type environment in which to resolve the nested type references
     * @return an array of resolved types, if all types can be resolved
     * @throws Unresolved if one of the type references cannot be resolved
     * @throws ArityMismatch if there is an argument count mismatch in constructing one
     * of the types
     */
    public Type[] resolveNestedTypeRefs(Cache<Type> cache, TypeEnv te) throws Unresolved, ArityMismatch {
        if (params.isEmpty() ) {
            return Type.NOTYPES;
        }
        Type[] r = new Type[params.size()];
        for ( int cntr = 0; cntr < r.length; cntr++ ) {
            r[cntr] = params.get(cntr).resolveType(cache, te);
        }
        return r;
    }

    /**
     * The <code>isResolved()</code> method checks whether this type reference has been
     * resolved yet.
     *
     * @return true if this type reference has been resolved to some type; false otherwise
     */
    public boolean isResolved() {
        return type != null;
    }

    /**
     * The <code>getType()</code> method returns the type that this type reference is bound to,
     * if this reference is bound. If the reference is not bound (i.e. not resolved), this method
     * will generate an error.
     *
     * @return the type that this reference is bound to, if any
     * @throws InternalError if the reference is not bound
     */
    public Type getType() {
        if (type == null) throw Util.unexpected(new Unresolved(name, point));
        return type;
    }

    /**
     * The <code>getTypeCon()</code> method returns a reference to the type constructor
     * of this type reference, if it has already been resolved.
     * @return a reference to the type constructor if this reference has been resolved,
     * null otherwise
     */
    public TypeCon getTypeCon() {
        return tycon;
    }

    /**
     * The <code>getNestedTypeRefs()</code> method gets a list of the type references
     * nested inside of this type reference.
     * @return a list of type references that are nested inside this type reference
     */
    public List<? extends TypeRef> getNestedTypeRefs() {
        return params;
    }

    /**
     * The <code>toString()</code> method converts this type reference to a canonical string
     * representation that is used to look up the type in type caches.
     *
     * @return a string representation of this type reference
     */
    public String toString() {
        if (tycon != null) {
            return tycon.render(params.toArray(TypeRef.NOPARAMS));
        }
        return name;
    }

    /**
     * The <code>getSourcePoint()</code> method returns the source point at which this type
     * reference was declared. This is used to report an error when the type cannot be resolved.
     *
     * @return the source point corresponding to this type reference
     */
    public SourcePoint getSourcePoint() {
        return point;
    }

    private Type newType(TypeCon tc, Cache<Type> cache, Type[] params) throws ArityMismatch {
        if ( tc.arity != TypeCon.UNBOUNDED && tc.arity != params.length)
            throw new ArityMismatch(name, point, tc.arity, params.length);
        return type = tc.newType(cache, params);
    }

    public static class Unresolved extends Exception {
        public final String name;
        public final SourcePoint point;
        public Unresolved(String nm, SourcePoint pt) {
            super("unresolved type "+ StringUtil.quote(nm)+" @ "+pt);
            name = nm;
            point = pt;
        }
    }

    public static class ArityMismatch extends Exception {
        public final String name;
        public final SourcePoint point;
        public final int expected;
        public final int found;
        public ArityMismatch(String nm, SourcePoint pt, int e, int f) {
            super("type param arity mismatch "+ StringUtil.quote(nm)+" @ "+pt);
            name = nm;
            point = pt;
            expected = e;
            found = f;
        }
    }

    public static TypeRef refOf(Type t) {
        if ( t == null ) return null;
        AbstractToken tok = AbstractToken.newToken(t.toString(), null);
        TypeRef tref = new TypeRef(tok, null, refOf(t.elements()));
        tref.type = t;
        return tref;
    }

    public static TypeRef refOf(TypeCon tc, Type t) {
        if ( t == null ) return null;
        AbstractToken tok = AbstractToken.newToken(t.toString(), null);
        TypeRef tref = new TypeRef(tok, null, refOf(t.elements()));
        tref.type = t;
        tref.tycon = tc;
        return tref;
    }

    public static List<TypeRef> refOf(Type[] te) {
        if ( te.length == 0 ) return NOPARAMSLIST;
        List<TypeRef> list = Ovid.newLinkedList();
        for ( Type t : te) list.add(refOf(t));
        return list;
    }

    public static <T extends TypeRef> List<T> toList(T[] t) {
        List<T> list = Ovid.newLinkedList();
        for ( T p : t) list.add(p);
        return list;
    }
}
