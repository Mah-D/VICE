/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 23, 2007
 */

package vpc.core.types;

import vpc.util.Cache;

/**
 * The <code>TypeCon</code> class models a type constructor, which is a function
 * that can produce new types from parameter types. For example, type constructors
 * model functions, arrays, and parameterized classes.
 *
 * @author Ben L. Titzer
 */
public abstract class TypeCon {

    public final String name;
    public final int arity;

    public static final int UNBOUNDED = -1;

    protected TypeCon(String name, int a) {
        this.name = name;
        arity = a;
    }

    /**
     * The <code>newType()</code> method constructs a new type from the given types.
     * @param cache the type cache to use in order to assure reference equality
     * @param p the list of types that are parameters to this type constructor
     * @return a new type that represents the type that results from applying this
     * type constructor to the given list of types
     */
    public abstract Type newType(Cache<Type> cache, Type... p);

    /**
     * The <code>getArity()</code> method gets the arity, or number of expected type
     * parameters, of this constructor. Some type constructors can accept an unbounded
     * number of type parameters. In this case, this method will return <code>UNBOUNDED</code>.
     * @return the number of type parameters expected by this type constructor
     */
    public int getArity() {
        return arity;
    }

    /**
     * The <code>render()</code> method creates a string representation of the type
     * that would result from applying this type constructor to the list of types
     * given.
     * @param p the list of type references to the type constructor
     * @return a canonical string representation of the type that would result
     * from applying this type constructor to the specifed parameter types
     */
    public String render(TypeRef... p) {
        assert arity == UNBOUNDED || p.length == arity;
        if ( p.length > 0 ) {
            StringBuffer buf = new StringBuffer(name);
            buf.append("<");
            for ( int cntr = 0; cntr < p.length; cntr++ ) {
                if ( cntr > 0 ) buf.append(", ");
                buf.append(p[cntr]);
            }
            buf.append(">");
            return buf.toString();
        } else {
            return name;
        }
    }

    /**
     * The <code>TypeCon.Singleton</code> class represents an implementation of the
     * <code>TypeCon</code> class that can be used to create a nullary type constructor
     * from any given type.
     */
    public static class Singleton extends TypeCon {
        public final Type type;

        public Singleton(Type t) {
            super(t.name, 0);
            type = t;
        }

        public Type newType(Cache<Type> cache, Type... p) {
            assert p.length == 0;
            return type;
        }

        public String render(TypeRef... p) {
            assert p.length == 0;
            return name;
        }
    }
}
