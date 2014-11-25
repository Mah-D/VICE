package vpc.util;

import java.util.*;

/**
 * The <code>HashList</code> class represents a list that also contains a <code>HashMap</code>
 * instance that can efficiently search for an element by its key. The <code>HashList</code>
 * data structure retains the order of insertion so that iteration over the elements is
 * always guaranteed. This is useful, for example, in a list of declarations within a program,
 * where the order is important, but most often, declarations will be searched for by their
 * name.
 *
 * @author Ben L. Titzer
 */
public class HashList<Key, Element> implements Iterable<Element> {

    final LinkedList<Element> list;
    final HashMap<Key, Element> map;

    public HashList() {
        list = Ovid.newLinkedList();
        map = Ovid.newHashMap();
    }

    public void add(Key str, Element e) {
        list.addLast(e);
        map.put(str, e);
    }

    public Iterator<Element> iterator() {
        return list.iterator();
    }

    public Element get(Key s) {
        return map.get(s);
    }
}
