/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 30, 2006
 */

package vpc.test;

import cck.parser.SourceException;
import cck.test.*;
import static cck.text.StringUtil.*;
import cck.text.Terminal;
import cck.util.*;
import vpc.core.*;
import vpc.core.base.*;
import vpc.core.csr.CSRProgram;
import vpc.core.decl.Method;
import vpc.core.virgil.VirgilComponent;
import vpc.sched.*;
import vpc.tir.TIRInterpreter;
import vpc.tir.tir2c.CLinkage;
import vpc.util.Ovid;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.Properties;

/**
 * The <code>TIRtoCHarness</code> class implements a test harness for the TIR to C translation.
 * The test harness checks that the generated C code compiles correctly (no errors) and
 * that when linked with a small main stub function and executed on the workstation, the
 * program actually computes the expected value.
 *
 * @author Ben L. Titzer
 */
public class ExecutionHarness implements TestEngine.Harness {

    protected abstract class Executor {

        protected final Options options = new Options();
        protected Stage[] STAGES;

        public abstract void compile(ExecuteTest test) throws Exception;
        public abstract void run(ExecuteTest test, Run r) throws Exception;

        public void cleanup(ExecuteTest test) {
            test.slot1 = null; // unlink any temporaries to prevent memory leaks
            test.slot2 = null;
        }

        protected void addMainEntryPoint(Program program, String cname, String mname) {
            program.programDecl = new ProgramDecl(program.getDefaultToken(program.name));
            ProgramDecl.EntryPoint n = new ProgramDecl.EntryPoint(
                    program.getDefaultToken("main"),
                    program.getDefaultToken(cname),
                    program.getDefaultToken(mname));
            program.programDecl.addEntryPoint(n);
        }

        protected Program compileTest(ExecuteTest test) throws IOException {
            Program program = new Program(test.progpath, language);
            program.addFile(new File(test.getFileName()));
            addMainEntryPoint(program, test.progname, "main");
            // compile the test
            Compilation compilation = new Compilation(program, STAGES, options);
            compilation.run();
            test.compiled = true;
            return program;
        }
    }

    /**
     * The <code>TIR2C_Executor</code> class implements a test case executor that uses
     * the TIR->C translation and a native compiler to generate a binary file for the
     * host platform. Testcases are executed by running the binary on the host computer.
     */
    public class TIR2C_Executor extends Executor {

        protected Option.Str LINK = options.newOption("linkage", "c:test", "");

        public TIR2C_Executor() {
            STAGES = Scheduler.getFixedPath("virgil,init,opt0,emit-c,gcc");
        }

        public void compile(ExecuteTest test) throws Exception {
            // compile the test to a binary
            compileTest(test);
        }

        public void run(ExecuteTest test, Run r) throws Exception {
            // run the binary file
            String[] command = { test.binaryFile, Integer.toString(r.input) };
            Process testproc = Runtime.getRuntime().exec(command);
            r.resultCode = testproc.waitFor();
        }
    }

    /**
     * The <code>Avrora_Executor</code> implements a test case executor that uses the
     * TIR->C translator and an AVR cross compiler (avr-gcc) to generate an AVR binary.
     * The AVR binary is then loaded into the Avrora simulator and the machine code
     * is emulated.
     */
    public class Avrora_Executor extends Executor {

        protected Option.Str LINK = options.newOption("linkage", "c:avrora", "");
        protected Option.Str GCCNAME = options.newOption("gcc-name", "avr-gcc", "");
        protected Option.Str GCCOPTS = options.newOption("gcc-options", "-mmcu=atmega128", "");

        public Avrora_Executor() {
            STAGES = Scheduler.getFixedPath("virgil,init,opt0,emit-c,gcc");
        }

        public void compile(ExecuteTest test) throws Exception {
            // compile the test to a binary
            compileTest(test);
            // make an Avrora VirgilRunner object
            // we use reflection here to remove the need for static dependencies on avrora
            Class rclz = Class.forName("avrora.test.VirgilRunner");
            Constructor rcons = rclz.getConstructor(String.class);
            test.slot1 = rcons.newInstance(test.binaryFile);
            // resolve the run method
            test.slot2 = rclz.getMethod("run", int.class, int.class, int.class);
        }

        public void run(ExecuteTest test, Run r) throws Exception {
            // invoke the run method on the object
            java.lang.reflect.Method rmeth = (java.lang.reflect.Method)test.slot2;
            Object result = rmeth.invoke(test.slot1, CLinkage.CAVRLinkage.STATUS_ADDR, CLinkage.CAVRLinkage.IO_ADDR, r.input);
            r.resultCode = (Integer) result;
        }
    }

    /**
     * The <code>Interpreter_Executor</code> class implements a test case executor that uses
     * the built-in Virgil interpreter. Testcases are executed by interpreting the program.
     * Note that this requires taking a snapshot of the program's heap after initialization,
     * but before executing the program. The snapshot is used to restore the program heap
     * to the initialization state for each run of the program.
     */
    public class Interpreter_Executor extends Executor {

        public Interpreter_Executor() {
            STAGES = Scheduler.getFixedPath("virgil,init");
        }

        public void compile(ExecuteTest test) throws Exception {
            // compile the test to get a program back
            Program program = compileTest(test);
            // remember the program and take a snapshot of the heap
            test.slot1 = program.heap.takeSnapshot(program.closure.getRecords());
            // the compilation succeeded
        }

        public void run(ExecuteTest test, Run run) throws Exception {
            Heap.Snapshot snapshot = (Heap.Snapshot)test.slot1;
            Program program = snapshot.heap.program;
            // resolve the main component and method
            VirgilComponent cd = program.virgil.getComponentDecl(test.progname);
            Method meth = cd.getLocalMethod("main");
            // try to execute the main method with the specified input value.
            try {
                snapshot.heap.closed = true;
                TIRInterpreter interp = new TIRInterpreter(program);
                Value[] v1 = { PrimInt32.toValue(run.input) };
                matchValue(run, interp.invokeComponentMethod(meth, v1));
            } catch (SourceException e) {
                Integer integer = CSRProgram.getExceptionCode(e.getErrorType());
                if ( integer == null ) throw Util.failure("unknown source exception: "+e.getErrorType());
                run.resultCode = integer;
            } finally {
                // restore the program state for the next run
                snapshot.heap.closed = false;
                snapshot.restore();
            }
        }

        private void matchValue(Run run, Value result) {
            if ( result == Value.BOTTOM ) run.resultCode = 0;
            else if ( result instanceof PrimBool.Val ) run.resultCode = PrimBool.fromValue(result) ? 1 : 0;
            else if ( result instanceof PrimRaw.Val ) run.resultCode = (int)(PrimRaw.fromValue(result) & 0xff);
            else if ( result instanceof PrimInt32.Val ) run.resultCode = PrimInt32.fromValue(result) & 0xff;
            else if ( result instanceof PrimChar.Val ) run.resultCode = PrimChar.fromValue(result) & 0xff;
            else throw Util.failure("unknown value: " + result);
        }
    }

    protected static class Run {
        protected int input;
        protected int expected;
        protected String exceptionName;
        protected int resultCode;

        protected void parse(CharacterIterator i) {
           skipWhiteSpace(i);
           int val = readDecimalValue(i, 12);
           skipWhiteSpace(i);
           if (peekAndEat(i, '=')) {
               input = val;
               skipWhiteSpace(i);
               if (Character.isDigit(i.current())) {
                   expected = readDecimalValue(i, 12);
                   exceptionName = String.valueOf(expected);
               } else {
                   String except = readIdentifier(i);
                   Integer integer = CSRProgram.getExceptionCode(except);
                   if (integer == null) throw Util.failure("unknown exception type: " + except);
                   expected = integer;
                   exceptionName = except;
               }
               skipWhiteSpace(i);
           } else {
               expected = val;
           }
        }
    }

    /**
     * The <code>ExecuteTest</code> class implements a test case that compiles a program
     * to a runnable representation and then executes the program with various inputs.
     * The output values (or exceptions or failures) are compared against the expected
     * result which is specified in the test case itself. This harness is designed to
     * work with multiple different ways of executing the program.
     * The runnable representation might be an IR suitable for an interpreter or C code
     * to be compiled for the native platform. The program may be run in the interpreter,
     * on the host platform, in another Java virtual machine, or in a hardware simulator
     * to produce the output results.
     */
    class ExecuteTest extends TestCase {

        protected String progpath;
        protected String progname;
        protected String outputFile;
        protected String binaryFile;

        protected boolean compiled;

        protected List<Run> runs;

        protected long vpcTime;
        protected long gccTime;
        protected long runTime;

        protected Object slot1;
        protected Object slot2;

        ExecuteTest(String fname, Properties props) {
            super(fname, props);
            runs = parseRuns(expectProperty("Result"));

            String tmpdir = Main.TEMPDIR.get();
            if ( !tmpdir.endsWith("/") ) tmpdir = tmpdir+"/";

            progname = baseFileName(fname);
            progpath = tmpdir + progname;
            outputFile = progpath + ".c";
            binaryFile = progpath + ".elf";
        }

        private List<Run> parseRuns(String result) {
            List<Run> list = Ovid.newLinkedList();
            CharacterIterator i = new StringCharacterIterator(result);
            while (true) {
                Run run = new Run();
                run.parse(i);
                list.add(run);
                if (!peekAndEat(i, ',')) break;
            }
            return list;
        }

        public void run() throws Exception {
            String ename = Main.options.getOptionValue("executor", "tir2c");
            Executor e = (Executor)executorMap.getObjectOfClass(ename);
            e.options.process(Main.options);
            e.compile(this);
            if ( compiled ) {
                // if the compilation succeeded, execute the runs
                for (Run r : runs) e.run(this, r);
            }
            e.cleanup(this);
        }

        /**
         * The <code>match()</code> method of a test case is called after the test case completes. If
         * the <code>run()</code> method throws an exception (Throwable), this method will be passed
         * the exception that was generated. If no exception was generated, then the exception passed
         * will be null. This method should return a new instance of the <code>TestResult</code> class
         * that represents the results of the test case.
         *
         * @param t the exception (if any) that was thrown while running the test case
         * @return a new <code>TestResult</code> indicating success or error
         */
        public TestResult match(Throwable t) {
            // default behavior: no exception = pass
            if (t == null) {
                if (!compiled) return new TestResult.TestFailure("program did not compile correctly");
                for (Run r : runs) {
                    if (r.resultCode != r.expected)
                        return new TestResult.TestFailure("program(" + r.input + ") = " + r.resultCode + ", expected " + r.exceptionName);
                }
                return new TestResult.TestSuccess();
            }
            return super.match(t);
        }

        public void reportStatistics() {
            Terminal.print(result.getColor(), getFileName());
            Terminal.print(": ");
            Terminal.print(rightJustify(TimeUtil.milliToSecs(vpcTime), 6)+" ");
            Terminal.print(rightJustify(TimeUtil.milliToSecs(gccTime), 6)+" ");
            Terminal.print(rightJustify(TimeUtil.milliToSecs(runTime), 6)+"  (vpc, gcc, run)");
            Terminal.nextln();
        }

    }

    protected final Language language;
    protected final ClassMap executorMap = new ClassMap("Executor", Executor.class);

    public ExecutionHarness(Language l) {
        this.language = l;
        executorMap.addInstance("tir2c", new TIR2C_Executor());
        executorMap.addInstance("interpreter", new Interpreter_Executor());
        executorMap.addInstance("avrora", new Avrora_Executor());
    }

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
        return new ExecuteTest(fname, props);
    }

}
