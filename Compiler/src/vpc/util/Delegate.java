/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Sep 14, 2006
 */

package vpc.util;

/**
 * @author Ben L. Titzer
 */
public interface Delegate<A, B> {
    public A invoke(B p);
}
