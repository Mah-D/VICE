/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Feb 22, 2007
 */
package vpc.util;

/**
 * The <code>Nested</code> interface helps in defining nested data
 * structures (such as type formulas and code representations). When
 * a class implements the <code>Algebraic</code> interface, it can be
 * visited and modified in interesting abstract ways.
 *
 * @author Ben L. Titzer
 */
public interface Nested<T extends Nested<T>> {

    /**
     * The <code>elements()</code> method gets the sub-elements of this
     * recursive data structure.
     * @return an array representing the sub-elements of this recursive data structure
     */
    public T[] elements();

    /**
     * The <code>rebuild()</code> method rebuilds this data structure with new
     * sub-elements.
     * @param t the new sub-elements of this data structure
     * @return a new instance of this data structure with the specified sub-elements
     */
    public T rebuild(T[] t);

}
