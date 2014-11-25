/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.core.types;

import vpc.util.*;

/**
 * The <code>Type</code> class represents a type present in the system,
 * whether it is a primitive type, class, component, or structure. Its
 * inner classes provide the implementations that represent the types
 * available in the Virgil programming language.
 *
 * @author Ben L. Titzer
 */
public abstract class Type implements TypeToken, Nested<Type> {

    public static final Type[] NOTYPES = new Type[0];

    public final String name;

    protected Type(String n) {
        name = n;
    }

    public String toString() {
        return name;
    }

    public Type[] elements() {
        return NOTYPES;
    }

    public abstract Type rebuild(Type[] ne);

    public int hashCode() {
        // equals => name.equals, which means name is ok for hashing
        return name.hashCode();
    }

    /**
     * The <code>Primitive</code> class represents a primitive type such as
     * a boolean, an integer, or a character.
     */
    public static class Simple extends Type {

        public Simple(String n) {
            super(n);
        }

        public Type rebuild(Type[] ne) {
            return this;
        }
    }
}
