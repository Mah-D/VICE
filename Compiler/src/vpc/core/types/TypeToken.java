/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 24, 2007
 */

package vpc.core.types;

/**
 * The <code>TypeToken</code> interface represents a unification of things
 * that refer to types, for more abstract versions of code that uses types,
 * but does not care about whether the type is resolved. For example,
 * a type token is useful for printing code, which doesn't care whether
 * the type is actually resolved or not.
 *
 * @author Ben L. Titzer
 */
public interface TypeToken {

    public String toString();
}
