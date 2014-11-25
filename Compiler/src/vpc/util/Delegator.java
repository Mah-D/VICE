/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Sep 14, 2006
 */

package vpc.util;

import java.util.LinkedList;

/**
 * The <code>Delegator</code> class implements a flexible delegator pattern
 * that delegates a method call across a number of possible objects
 * according to a search strategy.
 *
 * </p>
 * The <code>Delegator</code> works by maintaining a list of objects
 * that are searched in order. Upon invocation of the delegator, it
 * begins by calling the <code>invoke()</code> method of the first object; if that
 * call returns non-null, then that value is returned. If that call
 * returns null, then the next object's <code>invoke()</code> method is called,
 * and so on.
 *
 * </p>
 * The <code>Delegator</code> class also implements the <code>Delegate</code>
 * interface, allowing it to be one of the objects in the delegation chain.
 * This allows recursive delegation, making possible depth-first
 * delegation over a tree structure.
 *
 * @author Ben L. Titzer
 */
public class Delegator<R, P> implements Delegate<R, P> {

    protected final LinkedList<Delegate<R,P>> delegates;

    /**
     * The default constructor for a delegator takes no arguments.
     */
    public Delegator() {
        delegates = Ovid.newLinkedList();
    }

    /**
     * The <code>add()</code> method adds a new object to this delegation
     * chain at the end.
     * @param d the new delegate to add to the delegation chain
     */
    public void add(Delegate<R, P> d) {
        delegates.add(d);
    }

    /**
     * The <code>addFirst()</code> method adds a new object to this delegation
     * chain at the beginning.
     * @param d the new delegate to add to the delegation chain
     */
    public void addFirst(Delegate<R, P> d) {
        delegates.addFirst(d);
    }

    /**
     * The <code>remove()</code> method remove the specified delegate
     * from this delegation chain, if it exists.
     * @param d the delegate to remove from this chain
     */
    public void remove(Delegate<R, P> d) {
        delegates.remove(d);
    }

    /**
     * The <code>invoke()</code> method starts the delegation process. It will
     * invoke the <code>invoke()</code> methods of the delegated objects according
     * to the search strategy until the first returns non-null.
     * @param p the parameter to the invoke method
     * @return the result of first method to return non-null; null if no delegates
     * exist, or none of them returned non-null
     */
    public R invoke(P p) {
        for ( Delegate<R,P> i : delegates ) {
            R r = i.invoke(p);
            if ( r != null ) return r;
        }
        return null;
    }
}
