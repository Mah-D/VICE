/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Dec 26, 2006
 */
package vpc.tir.opt.rma;

import cck.util.Option;
import vpc.core.Program;
import vpc.sched.Stage;

import java.io.IOException;

/**
 * The <code>TIR_RMA</code> definition.
 *
 * @author Ben L. Titzer
 */
public class TIR_RMA extends Stage {

    protected final Option.Bool DEBUG = options.newOption("rma-debug", false,
            "This option enables debugging information for the RMA (reachable " +
            "members analysis) module. The output generated consists of units " +
            "of work performed by the analysis, as well as the reachable members " +
            "of each class and component in the program.");
    protected final Option.Bool CONSTR = options.newOption("set-constraints", false,
            "This option selects the analysis based on set-constraints, as opposed to " +
            "the traditional approach which is based on a work list.");

    public void visitProgram(Program p) throws IOException {
        // do nothing if we don't have the main entrypoints.
        if ( p.programDecl == null ) return;
        // create an analyzer and initialize the debug printer
        RMResults results;
        if (!CONSTR.get()) {
            RMAnalyzer rma = new RMAnalyzer();
            rma.analyzeProgram(p);
            results = rma;
        } else {
            RMSetAnalyzer rma = new RMSetAnalyzer();
            rma.analyzeProgram(p);
            results = rma;
        }

        // create a transformer and transform the program
        RMTransformer tf = new RMTransformer(results);
        tf.transform();
    }

}
