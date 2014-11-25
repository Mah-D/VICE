/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Dec 8, 2006
 */

package vpc.sched;

import cck.util.Options;
import vpc.core.Program;

import java.io.IOException;

/**
 * The <code>Stage</code> class represents a compilation stage that processes a program.
 * 
 * @author Ben L. Titzer
 */
public abstract class Stage {

    public final Options options = new Options();

    public abstract void visitProgram(Program p) throws IOException;

    public void processOptions(Options o) {
        options.process(o);
    }
}
