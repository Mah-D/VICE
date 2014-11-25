/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Mar 16, 2006
 */

package vpc.util;

import cck.util.Util;

import java.util.*;

/**
 * The <code>Hierachy</code> class implements a hierarchy (tree) for different data items;
 * e.g. classes. Each item in the tree has one parent. This class implements a number
 * of utility methods to get a hierarchy chain, recursively get children, etc.
 *
 * @author Ben L. Titzer
 */
public class Hierarchy<T> {

    protected final Map<T, T> parents = Ovid.newMap();
    protected final Map<T, List<T>> children = Ovid.newMap();

    /**
     * The <code>add()</code> method adds a new parent / child relationship to this
     * hierarchy. If the child specified already has a parent within this hierarchy,
     * this method will generate an internal error.
     *
     * @param p the parent entity of the relationship
     * @param c the child entity of the relationship
     */
    public void add(T p, T c) {
        if (parents.containsKey(c))
            throw Util.failure("Hierarchy only allows one parent for: " + c);
        parents.put(c, p);
        getChildrenList(p).add(c);
    }

    /**
     * The <code>getParent()</code> method gets the parent for the specified entity,
     * if a parent exists.
     *
     * @param t the child to get the parent entity of
     * @return a reference to the parent entity, if it exists; null otherwise
     */
    public T getParent(T t) {
        return parents.get(t);
    }

    /**
     * The <code>addRoot()</code> method adds the specified item as a root (i.e. no parent).
     *
     * @param t the parent to add as a root to this hierarchy
     */
    public void addRoot(T t) {
        getChildrenList(t);
    }

    /**
     * The <code>getChildren()</code> method gets a list of the children for the specified
     * entity. If there are no children for the specified entity, this method will return null.
     *
     * @param t the parent to get the list of children for
     * @return a reference to the list of children for the specified parent, if it exists; null
     *         if no children exist for this parent
     */
    public List<T> getChildren(T t) {
        return children.get(t);
    }

    /**
     * The <code>getChildren()</code> method gets the children for the specified
     * entity and adds them to the specified list. If there are no children for the specified
     * parent, no entities will be added to the list.
     *
     * @param t the parent to get the list of children for
     * @param l the list to which to add the children
     */
    public void getChildren(T t, List<T> l) {
        List<T> cl = getChildren(t);
        if (cl != null) {
            for (T c : cl) l.add(c);
        }
    }

    /**
     * The <code>getDescendants()</code> method gets all the children (transitively) for an
     * entity. This method is implemented by recursively adding the children to the list in
     * a breadth-first manner.
     *
     * @param t the parent for which to build a list of all the children
     * @return a list that contains all children for the specified parent
     */
    public List<T> getDescendants(T t) {
        List<T> result = Ovid.newLinkedList();
        Queue<T> queue = Ovid.newLinkedList();
        queue.offer(t);
        for (T p = queue.poll(); p != null; p = queue.poll()) {
            List<T> list = getChildren(p);
            if ( list != null ) {
                for (T c : list) {
                    result.add(c);
                    queue.offer(c);
                }
            }
        }
        return result;
    }

    /**
     * The <code>getRoot()</code> method gets the entity that is the highest parent of the
     * specified child entity. This method will return the child entity if it has no parents.
     *
     * @param t the child entity for which to get the root entity
     * @return a reference to the root entity for the specified entity
     */
    public T getRoot(T t) {
        for (T p = t; p != null; p = getParent(p)) t = p;
        return t;
    }

    /**
     * The <code>getChain()</code> method builds a chain from the root entity for the specified
     * entity to the specified entity. In this version of the method, the root entity appears first
     * in the list and the child entity appears last.
     *
     * @param t the entity for which to build the chain
     * @return a list representing the chain from the root entity to the child
     */
    public List<T> getChain(T t) {
        List<T> list = Ovid.newLinkedList();
        addParents(list, t);
        return list;
    }

    public List<T> getRoots() {
        List<T> list = Ovid.newLinkedList();
        for ( T c : children.keySet() ) {
            if ( getParent(c) == null ) list.add(c);
        }
        return list;
    }

    /**
     * The <code>getReverseChain()</code> method builds a chain from the specified entity back
     * to the root entity. In this version of the method, the root entity appears last
     * in the list and the child entity appears first.
     *
     * @param t the entity for which to build the chain
     * @return a list representing the chain from this entity to the root
     */
    public List<T> getReverseChain(T t) {
        List<T> list = Ovid.newLinkedList();
        for (T p = t; p != null; p = getParent(p)) list.add(p);
        return list;
    }

    protected void addParents(List<T> list, T t) {
        T p = getParent(t);
        if (p != null) addParents(list, p);
        list.add(t);
    }

    protected List<T> getChildrenList(T p) {
        List<T> l = children.get(p);
        if (l == null) {
            l = Ovid.newLinkedList();
            children.put(p, l);
        }
        return l;
    }

    public boolean contains(T t) {
        return children.containsKey(t);
    }

    /**
     * The <code>rebuild()</code> method rebuilds this hierarchy representation,
     * retaining only the nodes that are in the supplied collection. Any additional
     * nodes in the supplied live set that are not contained in this hierarchy
     * are added to the new hierarchy as root nodes.
     * @param live the collection of nodes to retain in the new hierarchy
     * @return a new hierarchy representation of this hierarchy, with only nodes
     * corresponding to the live set retained
     */
    public Hierarchy<T> rebuild(Collection<T> live) {
        Hierarchy<T> nh = new Hierarchy<T>();
        for ( T c : live ) {
            T p;
            for ( p = getParent(c); p != null; p = getParent(p) ) {
               if ( live.contains(p) ) break;
            }
            if ( p != null ) nh.add(p, c);
            else addRoot(c);
        }
        return nh;
    }

    /**
     * The <code>isOrphan()</code> method determines whether the specified node
     * is an orphan (i.e. has neither a parent node, nor any children nodes.
     * @param t the node to check
     * @return true if the node is an orphan; false if the node has either
     * a parent node or at least one child node
     */
    public boolean isOrphan(T t) {
        if ( getParent(t) != null ) return false;
        List<T> children = getChildren(t);
        return children == null || children.isEmpty();
    }

    /**
     * The <code>getNodes()</code> method returns a collection of all the
     * nodes in this hierarchy.
     * @return a collection that represents all the nodes in this hierarchy
     */
    public Collection<T> getNodes() {
        return children.keySet();
    }

    /**
     * The <code>rebuildWithLUB()</code> method rebuilds this hierarchy representation,
     * retaining only the nodes that are in the supplied collection and nodes that
     * are the least-upper-bound (LUB) of nodes in the live set. Any additional
     * nodes in the supplied live set that are not contained in this hierarchy
     * are added to the new hierarchy as root nodes.
     * @param live the collection of nodes to retain in the new hierarchy
     * @return a new hierarchy representation of this hierarchy, with only nodes
     * corresponding to the live set retained
     */
    public Hierarchy<T> rebuildWithLUB(Collection<T> live) {
        Hierarchy<T> nh = new Hierarchy<T>();
        for ( T root : getRoots() ) {
            boolean add = addWithLUB(root, nh, live);
            if ( add && getChildren(root).isEmpty() ) nh.addRoot(root);
        }
        return nh;
    }

    protected boolean addWithLUB(T r, Hierarchy<T> nh, Collection<T> live) {
        boolean plive = live.contains(r);
        T last = null;
        for (T c : getChildren(r)) {
            if (addWithLUB(c, nh, live)) {
                // the child is live
                if (plive) {
                    // the parent is live already
                    nh.add(r, c);
                } else if (last == null) {
                    // this is the first live child
                    last = c;
                } else {
                    // this is the second live child
                    plive = true;
                    nh.add(r, last);
                    nh.add(r, c);
                }
            }
        }
        return plive;
    }

}
