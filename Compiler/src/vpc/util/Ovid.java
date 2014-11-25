/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Mar 7, 2007
 */
package vpc.util;

import java.util.*;

/**
 * The <code>Ovid</code> class contains several utilities that provide a facade
 * for the Java collections framework. In some situations, this class may provide
 * self-tuning variants of the collection classes that dynamically change their
 * representations in response to the set of operations applied to them.
 *
 * @author Ben L. Titzer
 */
public class Ovid {

    public static final int WEAK = 0x1;
    public static final int HASH = 0x2;
    public static final int ARRAY = 0x4;

    public static <T> List<T> newList() {
        return new LinkedList<T>();
    }

    public static <T> List<T> emptyList() {
        return Collections.emptyList();
    }

    public static <T> LinkedList<T> newLinkedList() {
        return new LinkedList<T>();
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    public static <T> Set<T> newSet() {
        return new HashSet<T>();
    }

    public static <T> HashSet<T> newHashSet() {
        return new HashSet<T>();
    }

    public static <K, V> Map<K, V> newMap() {
        return new HashMap<K, V>();
    }

    public static <K, V> Map<K, V> newMap(int props) {
        if ( (props & WEAK) != 0) return new WeakHashMap<K, V>();
        return new HashMap<K, V>();
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    public static <K, V> Map<K, V> emptyMap() {
        return Collections.emptyMap();
    }
}
