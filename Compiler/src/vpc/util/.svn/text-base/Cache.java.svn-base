/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Feb 22, 2007
 */
package vpc.util;

import java.util.Map;

/**
 * The <code>Cache</code> class implements a canonicalizing set using a weak
 * hash map. The canonicalizing set is used to enforce reference equality
 * for a particular set of equivalent objects. This map uses weak references
 * so that the garbage collector may reclaim the elements when they are
 * no longer reachable through normal references.
 *
 * @author Ben L. Titzer
 */
public class Cache<T> {

    protected final Map<T, T> map;

    /**
     * The default constructor for the <code>Cache</code> class creates a new cache,
     * using a <code>WeakHashMap</code> instance as the backing map.
     */
    public Cache() {
        map = Ovid.newMap(Ovid.WEAK);
    }

    /**
     * The constructor for the <code>Cache</code> class creates a new cache,
     * using the performance flags specified.
     * @param flags the performance flags for the underlying map
     */
    public Cache(int flags) {
        map = Ovid.newMap(flags);
    }

    /**
     * The <code>getCached()</code> method gets the cached version of the specified
     * item, if it exists in the cache. If the item does not exist in the cache,
     * it will be added, and the original item will be returned.
     * @param t the item for which to retrieve the cached version
     * @return the cached version of the item, or the original item if not cached
     */
    public <U extends T> U getCached(U t) {
        T c = map.get(t);
        if ( c != null ) {
            return (U)c;
        } else {
            map.put(t, t);
            return t;
        }
    }

    public Iterable<T> iterator() {
        return map.keySet();
    }
}
