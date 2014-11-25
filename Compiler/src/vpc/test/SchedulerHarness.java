/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Dec 14, 2006
 */

package vpc.test;

import cck.test.*;
import cck.text.StringUtil;
import vpc.core.Program;
import vpc.sched.*;

import java.io.IOException;
import java.util.*;

/**
 * The <code>SchedulerHarness</code> class implements a test harness for
 * the scheduler that computes the sequences of optimizations and transformations
 * to apply to a program. This class implements a mock registry that allows
 * the core of the scheduler algorithm to be tested.
 *
 * @author Ben L. Titzer
 */
public class SchedulerHarness implements TestEngine.Harness {

    /**
     * The <code>newTestCase()</code> method creates a new test case of the right type given the file name and
     * the properties already extracted from the file by the testing framework.
     *
     * @param fname the name of the file
     * @param props a list of properties extracted from the file
     * @return an instance of the <code>TestCase</code> class
     * @throws Exception if there is a problem creating the testcase or reading it
     */
    public TestCase newTestCase(String fname, Properties props) throws Exception {
        return new SchedulerTestCase(fname, props);
    }

    static class MockStage extends Stage {

        public void visitProgram(Program p) throws IOException {
            // do nothing.
        }
    }

    public static class SchedulerTestCase extends TestCase {
        Stage[] result;
        Stage[] expect;
        String expectString;
        String requiredString;
        protected final String input;
        protected final String output;

        public SchedulerTestCase(String fname, Properties props) {
            super(fname, props);
            input = expectProperty("Input");
            output = expectProperty("Output");
            expectString = expectProperty("Result");
            requiredString = props.getProperty("Required");
        }

        public void run() {
            Registry registry = extractRegistry();
            Collection<String> req = null;
            if ( requiredString != null ) {
                req = (List<String>)StringUtil.toList(requiredString);
            }
            if ( expectString.length() > 0 ) {
                expect = Scheduler.getFixedPath(registry, expectString);
            }
            result = Scheduler.getAutoPath(registry, req, input, output);
        }

        public TestResult match(Throwable t) {
            if (t == null) {
                // if no exception
                if (result == null) {
                    // if no path found
                    if (expect == null) return new TestResult.TestSuccess();
                    return new TestResult.TestFailure("no path found, expected " + expectString);
                }
                // no exception, path was found
                if (expect == null) return new TestResult.TestFailure("no path expected");

                // path lengths do not match
                if (result.length != expect.length) {
                    return new TestResult.TestFailure("path lengths do not match");
                }

                // check each component of the paths
                for (int cntr = 0; cntr < result.length; cntr++) {
                    if (!result[cntr].equals(expect[cntr]))
                        return new TestResult.TestFailure("found wrong path");
                }
            }
            // default: return the result of super matching
            return super.match(t);
        }

        private Registry extractRegistry() {
            Registry reg = new Registry();

            for ( Map.Entry e : properties.entrySet() ) {
                String key = (String)e.getKey();
                String sig = (String)e.getValue();

                if ( key.startsWith("$") ) {
                    String name = key.substring(1, key.length());
                    reg.addStage(name, sig, new MockStage());
                }
            }
            return reg;
        }
    }
}
