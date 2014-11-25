/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 4, 2006
 */

package vpc.test;

import cck.test.*;
import cck.text.StringUtil;
import vpc.core.*;
import vpc.core.types.Type;
import vpc.sched.*;
import vpc.tir.stages.HeapParser;
import vpc.util.Ovid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Properties;

/**
 * The <code>TIRInitHarness</code> class implements a test harness for running the initializers
 * and building an initial object heap. The test case file contains a heap with records that
 * contains fields with values. This test harness compares records on a field-by-field basis
 * to check that the two heaps match each other exactly.
 *
 * @author Ben L. Titzer
 */
public class TIRInitHarness implements TestEngine.Harness {
    private final String stages;
    private final Language language;

    public TIRInitHarness(String stages, Language language) {
        this.stages = stages;
        this.language = language;
    }

    private static class HeapMismatch extends Exception {
        final TestResult result;

        HeapMismatch(TestResult r) {
            result = r;
        }
    }

    class InitTest extends TestCase.ExpectSourceError {

        Program program;
        Heap expected;

        InitTest(String fname, Properties props) {
            super(fname, props);
        }

        public void run() throws Exception {
            program = new Program(filename, language);
            Stage[] path = Scheduler.getFixedPath(stages);
            Compilation c = new Compilation(program, path, null);
            c.addFile(filename);
            c.run();
            HeapParser parser = new HeapParser(program);
            expected = parser.parseHeap(new BufferedReader(new FileReader(filename)));
        }

        protected TestResult checkPass() {
            try {
                compareHeaps(program.heap, expected);
                return new TestResult.TestSuccess();
            } catch (HeapMismatch e) {
                return e.result;
            }
        }

        void compareHeaps(Heap h1, Heap eh) throws HeapMismatch {
            Map<Integer, Heap.Record> map1 = getRecordMap(h1);

            // compare the sizes of the heaps (number of records)
            int h1sz = map1.size();
            int h2sz = getRecordMap(eh).size();
            if (h1sz != h2sz)
                fail("heap sizes do not match" + " (" + h2sz + " != " + h1sz + ")");

            // check every record in the expected heap
            for (Heap.Record er : eh.getRecords()) {
                Heap.Record r = map1.get(er.uid);

                // is the record present in the program heap?
                if (r == null) fail("record " + er + " is not present");

                // compare layout names
                compareTypes(r, r.getType(), er.getType());

                // compare the sizes of the records
                int sz1 = r.getSize();
                int sz2 = er.getSize();
                if (sz1 != sz2) fail("record " + r + " size incorrect" + " (" + sz2 + " != " + sz1 + ")");

                // compare each field individually
                for (int cntr = 0; cntr < r.getSize(); cntr++) {
                    Value v1 = r.getValue(cntr);
                    Value v2 = er.getValue(cntr);
                    if (!compareValues(v1, v2))
                        fail("record " + r + ", field " + cntr + " has value " + qv(v1) + ", expected " + qv(v2));
                }
            }
        }

        private boolean compareValues(Value v1, Value v2) {
            return Value.compareValues(v1, v2) || v1 instanceof Heap.Record && v2 instanceof Heap.Record && ((Heap.Record) v1).uid == ((Heap.Record) v2).uid;
        }

        private void compareTypes(Heap.Record r, Type t, Type et) throws HeapMismatch {
            if (!t.equals(et))
                fail("record " + r + " has type " + StringUtil.quote(t) + ", expected " + StringUtil.quote(et));
        }

        private String qv(Value v1) {
            return StringUtil.quote(v1);
        }

        private void fail(String msg) throws HeapMismatch {
            throw new HeapMismatch(new TestResult.TestFailure(msg));
        }

        Map<Integer, Heap.Record> getRecordMap(Heap h) {
            Map<Integer, Heap.Record> map = Ovid.newMap();
            for (Heap.Record r : h.getRecords())
                map.put(r.uid, r);
            return map;
        }
    }

    public TestCase newTestCase(String fname, Properties props) throws Exception {
        return new InitTest(fname, props);
    }
}
