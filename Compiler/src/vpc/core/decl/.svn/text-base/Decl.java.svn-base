/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 15, 2006
 */

package vpc.core.decl;

import cck.parser.SourcePoint;

/**
 * The <code>Decl</code> class represents a declaration of an item in the program,
 * such the declaration of a type, a field, a local variable, etc.
 *
 * @author Ben L. Titzer
 */
public interface Decl {

    /**
     * The <code>getName()</code> gets the string representation of the name of the
     * declared item.
     * @return a string representation of the name of the declared item
     */
    public String getName();

    /**
     * The <code>getSourcePoint()</code> method returns the source point at which
     * the item was declared. The source point contains information about the file,
     * line number, and column number of the declaration.
     * @return a source point which represents the location where the declaration
     * was declared
     */
    public SourcePoint getSourcePoint();
}
