/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 2, 2006
 */

package vpc.tir.stages;

import vpc.core.Program;
import vpc.core.ProgramPrinter;
import vpc.core.decl.*;
import vpc.core.virgil.VirgilClass;
import vpc.core.virgil.VirgilComponent;
import vpc.sched.Stage;
import vpc.tir.*;

import java.io.*;
import java.util.Iterator;

/**
 * The <code>TIREmit</code> class implements a compilation phase that emits the
 * TIR representation of the program as text.
 *
 * @author Ben L. Titzer
 */
public class TIREmit extends Stage {

    public void visitProgram(Program p) throws IOException {
        FileOutputStream fos = new FileOutputStream(p.name + ".tir");
        IREmitVisitor visitor = new IREmitVisitor(new PrintStream(fos));
        for (VirgilClass c : p.closure.getClasses()) visitor.visit(c);
        for (VirgilComponent c : p.closure.getComponents()) visitor.visit(c);
        visitor.emitHeap(p.heap);
    }

    public static class IREmitVisitor extends ProgramPrinter {
        private TIRPrinter irp;

        public IREmitVisitor(PrintStream o) {
            super(TIRRep.REP_NAME, o);
            irp = new TIRPrinter(printer);
        }

        public void printMethodRep(Method m, Method.MethodRep rep) {
            printRep((TIRRep) rep);
        }

        private void printRep(TIRRep tcr) {
            printer.startblock();

            printTemps("params", tcr.getParams());

            TIRExpr b = tcr.getBody();
            if (b != null) {
                printTemps("temps", tcr.getTemps());
                printer.print("body ");
                tcr.getBody().accept(irp, null);
            }
            printer.endblock();
        }

        public void printConstructorRep(Constructor c, Constructor.MethodRep rep) {
            printRep((TIRRep) rep);
        }

        private void printTemps(String name, Iterable<? extends Variable> it) {
            Iterator<? extends Variable> i = it.iterator();
            if (!i.hasNext()) return;
            printer.startblock(name);
            while (i.hasNext()) {
                Variable t = i.next();
                printer.println(t.getName() + ": " + t.getType() + ";");
            }
            printer.endblock();
        }
    }
}
