/*
 * Copyright (c) 2008, Ben L. Titzer
 * See license.txt for details.
 *
 * Created: Jul 3, 2008
 */
package aeneas.jvm;

import java.io.PrintStream;
import java.util.List;
import java.util.LinkedList;

/**
 * The <code>ProgressPrinter</code> class definition.
 *
 * @author Ben L. Titzer
 */
public class ProgressPrinter {

    private static final String CTRL_RED = "\u001b[0;31m";
    private static final String CTRL_GREEN = "\u001b[0;32m";
    private static final String CTRL_NORM = "\u001b[0;00m";

    public final int total;
    public final int verbose;
    private String current;
    private int passed;
    private int finished;

    private final PrintStream output = System.out;
    private final List<String> failures = new LinkedList<String>();

    public ProgressPrinter(int total, int verbose) {
        this.total = total;
        this.verbose = verbose;
    }

    public void begin(String item) {
        current = item;
        if (verbose == 2) output.print("Running " + item + "...");
    }

    public void pass() {
        passed++;
        if (verbose > 0) output(CTRL_GREEN, 'o', "ok");
    }

    public void fail(String msg) {
        if (verbose > 0) output(CTRL_RED, 'X', "failed");
        if (verbose == 1) failures.add(CTRL_RED + current + CTRL_NORM + ": " + msg);
        if (verbose == 2) this.output.println(" -> " + msg);
    }

    private void output(String ctrl, char ch, String str) {
        finished++;
        if (verbose == 1) {
            output.print(ctrl);
            output.print(ch);
            output.print(CTRL_NORM);
            if (finished % 50 == 0 || finished == total) this.output.print(" " + finished + " of " + total + "\n");
            else if (finished % 10 == 0) this.output.print(' ');
        } else if (verbose == 2) {
            output.print(ctrl);
            output.print(str);
            output.print(CTRL_NORM);
            output.println("");
        }
    }

    public void report() {
        output.println(passed + " of " + total +" passed");
        if (verbose == 1) {
            for (String s : failures) {
                output.println(s);
            }
        }
    }
}
