/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Dec 26, 2005
 */
package vpc.core;

import cck.parser.SourceException;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.decl.Field;
import vpc.core.decl.Variable;
import vpc.core.types.Type;
import vpc.core.virgil.VirgilDelegate;
import vpc.tir.expr.Operator;
import vpc.util.Ovid;

import java.util.*;

/**
 * The <code>Heap</code> class represents the heap for a particular program
 * that is used during the initialization phase and then outputted as part
 * of the program binary. It contains records which can be used to represent
 * objects, method tables, and other data structures in the program.
 *
 * @author Ben L. Titzer
 */
public class Heap {

    protected Set<Record> roots;
    protected int maxUid;
    public final Program program;
    public boolean closed;

    public class Snapshot {
        public final Heap heap;
        protected final Map<Record, Value[]> map;

        protected Snapshot() {
            map = Ovid.newMap();
            heap = Heap.this;
        }

        public void restore() {
            for ( Map.Entry<Record, Value[]> e : map.entrySet() ) {
                Heap.Record record = e.getKey();
                Value[] vals = e.getValue();
                for ( int cntr = 0; cntr < vals.length; cntr++ ) {
                    record.setValue(cntr, vals[cntr]);
                }
            }
        }
    }

    public interface Blueprint {
        public Type getCellType(Heap.Record rec, int cell);
        public String getCellName(Heap.Record rec, int cell);
        public Field getCellDecl(Heap.Record rec, int index);
    }

    public static class BasicBlueprint implements Blueprint {
        public final Type recordType;
        public final ArrayList<Field> fields;

        public BasicBlueprint(Type t) {
            recordType = t;
            fields = Ovid.newArrayList();
        }

        public Type getCellType(Record rec, int cell) {
            return fields.get(cell).getType();
        }

        public String getCellName(Record rec, int cell) {
            return fields.get(cell).getName();
        }

        public Field getCellDecl(Record rec, int cell) {
            return fields.get(cell);
        }

        public boolean hasField(Field f) {
            return f == fields.get(f.fieldIndex);
        }
    }

    /**
     * The <code>Layout</code> class represents the layout of a record within the heap.
     * It stores a list of fields that have names and types; the layout is used to track
     * the indices of fields and can also be used to migrate a record from one layout to another.
     */
    public class Layout {
        public Type type;
        protected final String name;
        protected ArrayList<Cell> cells;

        public Layout(String n) {
            name = n;
            cells = new ArrayList<Cell>();
        }

        public Cell findCell(Field f) {
            for (Cell c : cells) {
                if (c.field == f) return c;
            }
            return null;
        }

        public Type getCellType(int i) {
            return cells.get(i).field.getType();
        }

        public String getCellName(int i) {
            return cells.get(i).field.getName();
        }

        public Field getCellDecl(int i) {
            return cells.get(i).field;
        }

        public int addCell(Field f) {
            Cell c = new Cell(f);
            c.index = cells.size();
            cells.add(c);
            return c.index;
        }

        public Layout copy(Layout nl) {
            for (Cell f : cells)
                nl.addCell(f.field);
            return nl;
        }

        public int size() {
            return cells.size();
        }

        public String toString() {
            return name;
        }
    }

    /**
     * The <code>Record</code> class encapsulates a record within the heap; it is a
     * collection of named fields, with each field corresponding to a <code>java.lang.Object</code>
     * that represents a value. A value could be an integer, a string literal, or a reference
     * to another <code>Record</code>.
     */
    public static class Record extends Value {
        public final int uid;
        public Type type;

        protected Value[] values;

        protected Record(Type t, int u, int sz) {
            super();
            uid = u;
            type = t;
            values = new Value[sz];
        }

        public Value getValue(int index) {
            return values[index];
        }

        public void setValue(int index, Value v) {
            values[index] = v;
        }

        public int getSize() {
            return values.length;
        }

        public String toString() {
            return "#" + uid + ":" + type;
        }

        public Type getType() {
            return type;
        }
    }

    /**
     * The <code>Cell</code> class represents a cell within a layout. A field is a cell,
     * typed, storage location that has a default value. A cell instance can be used to
     * extract a value from a record by using its index.
     */
    public static class Cell implements Variable {
        public final Field field;
        protected int index;

        protected Cell(Field f) {
            field = f;
        }

        public Type getType() {
            return field.getType();
        }

        public String getName() {
            return field.getName();
        }
    }

    /**
     * The constructor for the <code>Heap</code> class creates a new with the given number of
     * maximum fields (in all records). The maximum field number is used to determine when to
     * automatically trigger garbage collection, since some program's initialization phase
     * may allocate a large amount of temporary memory.
     *
     */
    public Heap(Program p) {
        program = p;
        roots = Ovid.newSet();
    }

    /**
     * The <code>newRecordWithSize()</code> method creates a new record within this heap and returns
     * a reference. This version of the method gives the new record the next highest UID, so
     * that the UID of each record corresponds to its allocation order (i.e. 0 being first).
     *
     * @param type the type of the record
     * @param sz the size of the record, in number of fields @return a reference to a new, empty, record within this heap
     * @param uid the new unique identifier of the record
     * @return a new record with the specified layout and size
     */
    public Record allocRecord(Type type, int sz, int uid) {
        closedCheck();
        return new Record(type, uid == -1 ? maxUid++ : uid, sz);
    }

    public Record allocRecord(Type type, int size) {
        closedCheck();
        return new Record(type, maxUid++, size);
    }

    /**
     * The <code>getRecords()</code> method returns a reference to an iterable collection of
     * the records in this heap.
     *
     * @return a list of the records in this heap that can be iterated over
     */
    public Iterable<Record> getRecords() {
        return new RecordIterator(roots);
    }

    /**
     * The <code>takeSnapshot()</code> method takes a snapshot of the values of the records
     * in the specified iterable set.
     * @param r the iterable set of records for which to record a snapshot
     * @return a <code>Snapshot</code> instance that records all the values of the records
     */
    public Snapshot takeSnapshot(Iterable<Record> r) {
        Snapshot s = new Snapshot();
        for ( Record record : r ) {
            s.map.put(record, record.values.clone());
        }
        return s;
    }

    public void clearRoots() {
        roots = Ovid.newSet();
    }

    public void addRoot(Record r) {
        roots.add(r);
    }

    public Layout newLayout(String n, Type t) {
        Layout layout = new Layout(n);
        layout.type = t;
        return layout;
    }

    private static void addIfUnmarked(Record ref, Set<Record> marked, LinkedList<Record> queue) {
        if (ref != null && !marked.contains(ref)) {
            marked.add(ref);
            queue.offer(ref);
        }
    }

    private void closedCheck() {
        if ( closed ) throw new SourceException("AllocationException", null, "allocation in this heap has been disabled", StringUtil.EMPTY_STRING_ARRAY);
    }

    public static String recordName(Record record) {
        return record == null ? "#null" : record.toString();
    }

    public static boolean compareRecords(Record r1, Record r2) {
        // both null? or identical record in same heap?
        if (r1 == r2) return true;
        // one null? (implies not equal)
        if (r1 == null || r2 == null) return false;
        // compare UIDs (records from different heaps)
        return r1.uid == r2.uid;
    }

    /**
     * The <code>RecordIterator</code> class implements an iterator over a record graph.
     * This iterator will return all reachable records in the graph by iterating over
     * the references between records in a depth-first manner.
     */
    public static class RecordIterator implements Iterator<Record>, Iterable<Record> {

        private final LinkedList<Record> queue;
        private final Set<Record> marked = Ovid.newSet();

        public RecordIterator(Collection<Record> roots) {
            queue = Ovid.newLinkedList();
            queue.addAll(roots);
            marked.addAll(roots);
        }

        public Record next() {
            Heap.Record r = queue.poll();
            if ( r == null ) throw new NoSuchElementException();
            scan(r);
            return r;
        }

        public void remove() {
            throw Util.unimplemented();
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }

        private void scan(Heap.Record r) {
            for (Value v : r.values) {
                if (v instanceof Record)
                    addIfUnmarked((Record)v, marked, queue);
                else if (v instanceof VirgilDelegate.Val)
                    addIfUnmarked(((VirgilDelegate.Val) v).record, marked, queue);
            }
        }

        public Iterator<Record> iterator() {
            return this;
        }
    }

    public static class AllocationException extends Operator.Exception {
        public AllocationException() {
            super("heap allocation is disallowed");
        }
    }

    public static void rebuildRecord(Record r, Heap.Layout ol, Layout nl) {
        Value[] nv = new Value[nl.size()];
        for (Cell c : nl.cells) {
            Cell prev = ol.findCell(c.field);
            if (prev != null) nv[c.index] = r.getValue(prev.index);
            else nv[c.index] = Value.BOTTOM;
        }
        r.values = nv;
    }
}
