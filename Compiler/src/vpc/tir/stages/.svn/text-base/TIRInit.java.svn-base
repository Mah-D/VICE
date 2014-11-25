/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 2, 2006
 */

package vpc.tir.stages;

import cck.util.Option;
import vpc.core.Program;
import vpc.core.virgil.VirgilComponent;
import vpc.sched.Stage;
import vpc.tir.TIRInterpreter;

import java.io.IOException;

/**
 * The <code>TIRInit</code> class implements the initialization stage in compiling
 * a Virgil program. Initialization is accomplished by interpreting the constructors
 * of each component in the program in the order that they were encountered in
 * compilation.
 *
 * @author Ben L. Titzer
 */
public class TIRInit extends Stage {

    public Option.Bool TRACE = options.newOption("trace", false,
            "This option enables tracing of the method calls invoked during the initialization " +
            "phase of the program.");

    public void visitProgram(Program p) throws IOException {
        TIRInterpreter interpreter = new TIRInterpreter(p);
        for (VirgilComponent d : p.closure.getComponents()){
            d.setRecord(interpreter.initComponent(d));
        }
    }
}
