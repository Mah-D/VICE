/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 14, 2006
 */

package vpc.core.types;

/**
 * The <code>Typeable</code> interface represents an entity that is typeable
 * (e.g. a variable declaration or a field).
 *
 * @author Ben L. Titzer
 */
public interface Typeable {

    /**
     * The <code>getType()</code> method returns the type of this typeable entity.
     *
     * @return the type of this entity
     */
    public Type getType();
}
