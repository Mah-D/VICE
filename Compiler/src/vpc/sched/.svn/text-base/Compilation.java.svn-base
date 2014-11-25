/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.sched;

import static cck.text.StringUtil.*;
import cck.text.TermUtil;
import cck.text.Terminal;
import cck.util.Options;
import cck.util.TimeUtil;
import vpc.Compiler;
import vpc.core.Program;
import vpc.core.virgil.V2Language;

import java.io.File;
import java.io.IOException;
import static java.lang.System.currentTimeMillis;

/**
 * The <code>Compilation</code> class encapsulates the functionality of
 * performing the various stages of the compilation process. A compilation
 * is built of <code>Compilation.Stage</code> instances that can be either
 * per module, per type, per class, per method, etc. A chain of these
 * stages is built and the Compilation class will schedule the
 * compilation appropriately (eventually in multiple threads).
 *
 * @author Ben L. Titzer
 */
public class Compilation {

    protected final Program program;
    protected final Options options = new Options();

    protected Stage[] stages;
    protected long[] time;
    protected boolean[] success;
    protected long begin_millis;
    protected long total_millis;

    public Compilation(Program p, Stage[] s, Options opts) {
        program = p;
        stages = s;
        time = new long[stages.length];
        success = new boolean[stages.length];
        for (Stage stage : stages) {
            if (opts != null) stage.processOptions(opts);
        }
    }

    public void addFile(String f) {
        program.addFile(new File(f));
    }

    public void setOption(String name, String val) {
        options.setOption(name, val);
    }

    public void setOptions(Options o) {
        options.process(o);
    }

    public Program getProgram() {
        return program;
    }

    public void run() throws IOException {

        long now = currentTimeMillis();
        begin_millis = now;
        for (int i = 0; i < stages.length; i++) {
            long start = now;          
            try {
                stages[i].visitProgram(program);
                success[i] = true;
            } finally {
                now = currentTimeMillis();
                total_millis = now - Compiler.startMillis;
                time[i] = now - start;
            }
        }
    }

    public void printReport() {
        TermUtil.printSeparator(50, "Compilation statistics");
        reportTime(Terminal.COLOR_DEFAULT, "startup", begin_millis - Compiler.startMillis);
        for ( int i = 0; i < stages.length; i++ ) {
            int color = success[i] ? Terminal.COLOR_GREEN : Terminal.COLOR_RED;
            reportTime(color, Registry.getStageName(stages[i]), time[i]);
            if (!success[i]) break;
        }
        
        TermUtil.printThinSeparator(50);
        reportTime(Terminal.COLOR_DEFAULT, "total", total_millis);
    }

    private void reportTime(int color, String name, long ms) {
        float pct = (100.0f * ms) / total_millis;
        // \color:$1{\19{$2}}: \-7{$3} \-7{$4} %
        Terminal.print(" ");
        Terminal.print(color, leftJustify(name, 19));
        Terminal.print(": ");
        Terminal.print(rightJustify(TimeUtil.milliToSecs(ms), 7));
        Terminal.print(" ");
        Terminal.print(rightJustify(toFixedFloat(pct, 2), 7));
        Terminal.print(" %");
        Terminal.nextln();
    }

    protected abstract static class Target {

        protected abstract void setOptions(Scheduler sched, Options mainOptions);

        protected Stage[] getPath(Scheduler sched, Options mainOptions) {
            setOptions(sched, mainOptions);
            int lvl = (int)sched.OPTLEVEL.get();
            if ( lvl < 0 ) lvl = 0;
            if ( lvl >= Scheduler.optlevels.length ) lvl = Scheduler.optlevels.length - 1;
            return Scheduler.getFixedPath(Scheduler.optlevels[lvl]);
        }
    }
}
