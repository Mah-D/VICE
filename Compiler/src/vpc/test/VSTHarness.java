/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Sep 11, 2005
 */
package vpc.test;

import cck.test.TestCase;
import cck.test.TestEngine;
import vpc.core.Language;
import vpc.core.Program;
import vpc.core.types.TypeEnv;
import vpc.core.virgil.V2Language;
import vpc.core.virgil.V3Language;
import vpc.sched.*;
import vpc.vst.parser.v2.VirgilParser;
import vpc.vst.parser.v3.Virgil3Parser;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * The <code>VSTHarness</code> class implements a test harness
 * for virgil semantic tests. This class implements a harness that loads,
 * parses, and semantically checks each test case as a separate program.
 * Each program contains a description of what error(s) it expects the
 * semantic analyzer to generate, and this test harness compares the
 * expected results against the actual results to determine whether the
 * test case passed.
 *
 * @author Ben L. Titzer
 */
public class VSTHarness {

    /**
     * The <code>SemanTest</code> class implements a test case that checks the
     * semantic checker for the Virgil programming language. Each test case contains
     * one program and an expected that result that is either "PASS" or the name and
     * location of the semantic error that the virgil semantic checker should detect.
     */
    static class SemanTest extends TestCase.ExpectSourceError {
        private final String stages;
        private final Language language;

        SemanTest(String fname, Properties props, String st, Language lang) {
            super(fname, props);
            stages = st;
            language = lang;
        }

        public void run() throws Exception {
            Program program = new Program("test", language);
            Stage[] path = Scheduler.getFixedPath(stages);
            Compilation c = new Compilation(program, path, null);
            c.addFile(filename);
            c.run();
        }
    }

    /**
     * The <code>SemanHarness</code> class implements a test harness that creates test
     * cases for the compiler that check the semantic errors that the compiler should
     * generate.
     */
    public static class SemanHarness implements TestEngine.Harness {
        private final String stages;
        private final Language language;

        public SemanHarness(String stages, Language language) {
            this.stages = stages;
            this.language = language;
        }

        public TestCase newTestCase(String fname, Properties props) {
            return new SemanTest(fname, props, stages, language);
        }
    }
}
