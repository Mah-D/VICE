/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Apr 15, 2006
 */
package vpc.test;

import cck.test.TestEngine;
import cck.text.Terminal;
import cck.util.*;
import vpc.core.virgil.V2Language;
import vpc.core.virgil.V3Language;

import java.io.IOException;

/**
 * The <code>Main</code> class represents the main driver for the testing harness.
 * It parses the command line options and creates a <code>AutomatedTester</code>
 * instance that instantiates and runs each test case from the files specified
 * on the command line.
 *
 * @author Ben L. Titzer
 */
public class Main {
    static final Options options = new Options();

    static final Option.Bool COLORS = options.newOption("colors", true,
            "This option controls the printing of terminal colors.");
    static final Option.Bool DETAIL = options.newOption("detail", false,
            "When this option is specified, the automated testing framework will " +
            "report the details of any test case failures, including stacktraces " +
            "of unexpected exceptions, state mismatches, etc.");
    static final Option.Bool PROGRESS = options.newOption("progress", false,
            "When this option is specified, the automated testing framework will " +
            "report the result of each test as it is run.");
    static final Option.Bool STAT = options.newOption("statistics", false,
            "When this option is specified, the automated testing framework will " +
            "report statistics for each test case.");
    static final Option.Long THREADS = options.newOption("threads", 1,
            "This option specifies the number of worker threads to use in running " +
            "the test cases.");
    static final Option.Long TIMEOUT = options.newOption("timeout", 5,
            "This option specifies the number of seconds that the test engine will " +
            "wait before forcibly terminating a test case. This is used to prevent " +
            "test cases that enter an infinite loop from blocking the entire framework.");
    static final Option.Long VERBOSE = options.newOption("verbose", 1,
            "This option specifies the verbosity level of the testing engine. Level 1 " +
            "reports only the number of tests passed and each failing test. Level 2 reports " +
            "progress by printing a character to the console for each test. Level 3 reports " +
            "the progress of each individual test. Level 0 disables any output, and only " +
            "the return code of the process indicates success or failure.");
    public static final Option.Str TEMPDIR = options.newOption("tmp", "/tmp",
            "This option specifies the location of a temporary directory which can be used " +
            "by the testing harness to generate temporary files.");


    protected static final ClassMap harnesses = new ClassMap("Test Harness", TestEngine.Harness.class);

    static {
        V2Language virgil2 = new V2Language();
        harnesses.addInstance("v2-seman", new VSTHarness.SemanHarness("virgil-parse,virgil-gtb,virgil-tc", virgil2));
        V3Language virgil3 = new V3Language();
        harnesses.addInstance("v3-seman", new VSTHarness.SemanHarness("virgil-parse,virgil-gtb,virgil-tc", virgil3));
        harnesses.addInstance("v2-parse", new VSTHarness.SemanHarness("virgil-parse", virgil2));
        harnesses.addInstance("v3-parse", new VSTHarness.SemanHarness("virgil-parse", virgil3));

        harnesses.addInstance("v2-init", new TIRInitHarness("virgil,init", virgil2));
        harnesses.addInstance("v2-exec", new ExecutionHarness(virgil2));

        harnesses.addInstance("v3-init", new TIRInitHarness("virgil3,init", virgil3));
        harnesses.addInstance("v3-exec", new ExecutionHarness(virgil3));

        harnesses.addClass("scheduler", SchedulerHarness.class);
    }

    public static void main(String[] args) {

        boolean success = false;

        try {
            // parse the command line options
            args = parseOptions(args);

            // no files specified on command line
            if (args.length == 0) Util.userError("no input files");

            // if all of the files exist, run the tests
            if (Util.verifyFilesExist(args)) {
                success = runTests(args);
            }

        } catch (Util.Error e) {
            e.report();
        } catch (Throwable t) {
            Terminal.print(Terminal.ERROR_COLOR, "Unexpected exception");
            Terminal.println(": " + t.getClass());
            t.printStackTrace();
        }
        if ( success ) System.exit(0);
        else System.exit(1);
    }

    public static String[] parseOptions(String[] args) {
        options.parseCommandLine(args);
        Terminal.useColors = COLORS.get();
        TestEngine.LONG_REPORT = DETAIL.get();
        TestEngine.PROGRESS_REPORT = PROGRESS.get();
        TestEngine.STATISTICS = STAT.get();
        TestEngine.VERBOSE = (int)VERBOSE.get();
        TestEngine.THREADS = (int)THREADS.get();
        TestEngine.MAXIMUM_TEST_MS = 1000*(int)TIMEOUT.get();
        return options.getArguments();
    }

    private static boolean runTests(String[] files) throws IOException {
        return new TestEngine(harnesses).runTests(files);
    }
}
