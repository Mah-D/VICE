/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.core;

import cck.parser.AbstractToken;
import cck.text.Printer;
import vpc.core.decl.*;
import vpc.core.virgil.VirgilClass;
import vpc.core.virgil.VirgilComponent;

import java.io.PrintStream;

/**
 * The <code>ProgramPrinter</code> class implements a framework for
 * outputting the program in various forms.
 *
 * @author Ben L. Titzer
 */
public abstract class ProgramPrinter {
    public final Printer printer;
    private final String repName;

    protected ProgramPrinter(String r, Printer p) {
        printer = p;
        repName = r;
    }

    protected ProgramPrinter(String r, PrintStream o) {
        printer = new Printer(o);
        repName = r;
    }

    public void visit(VirgilClass pcd) {
        StringBuffer b = new StringBuffer("class ");
        b.append(pcd.getName());
        b.append(" ");
        AbstractToken pn = pcd.getParent();
        if (pn != null) b.append("extends ").append(pn.image);
        printer.startblock(b.toString());
        visitFields(pcd);
        printer.println("");
        visit(pcd.getConstructor());
        printer.println("");
        visitMethods(pcd);
        printer.endblock();
        printer.println("");
    }

    private void visitMethods(CompoundDecl pcd) {
        for (Method m : pcd.getMethods()) visit(m);
    }

    private void visitFields(CompoundDecl pcd) {
        for (Field f : pcd.getFields()) visit(f);
    }

    public void visit(VirgilComponent pcd) {
        String b = "component " + pcd.getName();
        printer.startblock(b);
        visitFields(pcd);
        printer.println("");
        visit(pcd.getConstructor());
        printer.println("");
        visitMethods(pcd);
        printer.endblock();
        printer.println("");
    }

    public void visit(Method m) {
        printer.print(m.toString());
        Method.MethodRep rep = m.getMethodRep(repName);
        if (rep != null) {
            printer.print(" ");
            printMethodRep(m, rep);
        } else {
            printer.println(";");
        }
    }

    public void visit(Constructor c) {
        if (c == null) return;
        printer.print(c.toString());
        Constructor.MethodRep rep = c.getMethodRep(repName);
        if (rep != null) {
            printer.print(" ");
            printConstructorRep(c, rep);
        } else {
            printer.println(";");
        }
    }

    public void visit(Field f) {
        printer.println(f.toString() + ";");
    }

    public void emitHeap(Heap h) {
        printer.startblock("heap");
        for (Heap.Record r : h.getRecords()) {
            printer.startblock("record #" + r.uid + ":" + r.getSize() + ":" + r.getType());
            for (int cell = 0; cell < r.getSize(); cell++) {
                printField(h.program, r, cell);
            }
            printer.endblock();
        }
        printer.endblock();
    }

    public void printField(Program program, Heap.Record r, int cell) {
        StringBuffer buf = new StringBuffer("field ");
        Heap.Layout layout = program.getLayout(r);
        buf.append(layout.getCellName(cell));
        buf.append(": ");
        buf.append(layout.getCellType(cell));
        buf.append(" = ");
        buf.append(r.values[cell]);
        buf.append(";");
        printer.println(buf.toString());
    }

    public abstract void printMethodRep(Method pm, Method.MethodRep r);

    public abstract void printConstructorRep(Constructor pc, Constructor.MethodRep r);

}
