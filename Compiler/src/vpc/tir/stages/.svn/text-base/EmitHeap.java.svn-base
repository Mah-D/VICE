/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Feb 24, 2006
 */
package vpc.tir.stages;

import cck.text.Printer;
import cck.text.StringUtil;
import vpc.core.*;
import vpc.core.virgil.VirgilDelegate;
import vpc.sched.Stage;

import java.io.*;

/**
 * @author Ben L. Titzer
 */
public class EmitHeap extends Stage {

    public void visitProgram(Program program) throws IOException {
        FileOutputStream fos = new FileOutputStream(program.name + ".dot");
        Printer p = new Printer(new PrintStream(fos));
        p.startblock("digraph " + program.name);
        p.println("rankdir=LR;");
        emitRecordDecls(program, p);
        emitEdges(program, p);
        p.endblock();
        p.close();
    }

    private void emitRecordDecls(Program program, Printer p) {
        for (Heap.Record r : program.closure.getRecords()) {
            String rn = getRecordLabel(r);
            p.print(rn + "[shape=record,label=\"");
            Heap.Layout layout = program.getLayout(r);
            for (int cell = 0; cell < r.getSize(); cell++) {
                if (cell > 0) p.print(" | ");
                String fname = getFieldName(layout.getCellName(cell), cell);
                p.print("<" + fname + "> " + fname);
                Value fval = r.getValue(cell);
                if (fval instanceof Heap.Record || fval instanceof VirgilDelegate.Val) {
                    p.print(": " + layout.getCellType(cell));
                } else {
                    p.print(" = " + fval);
                }
            }
            p.println("\"];");
        }
    }

    private void emitEdges(Program program, Printer p) {
        for (Heap.Record r : program.closure.getRecords()) {
            String rn = getRecordLabel(r);
            Heap.Layout layout = program.getLayout(r);
            for (int cell = 0; cell < r.getSize(); cell++ ) {
                emitEdge(p, rn, r.getValue(cell), getFieldName(layout.getCellName(cell), cell));
            }
        }
    }

    private String getRecordLabel(Heap.Record r) {
        return StringUtil.quote(Heap.recordName(r));
    }

    private String getFieldName(String n, int cntr) {
        return "".equals(n) ? String.valueOf(cntr) : n;
    }

    private void emitEdge(Printer p, String rn, Value val, String name) {
        if (val instanceof Heap.Record) {
            p.println(rn + ":" + name + " -> " + StringUtil.quote(val) + "[label=" + name + "];");
        } else if (val instanceof VirgilDelegate.Val) {
            VirgilDelegate.Val d = VirgilDelegate.fromValue(val);
            if (d.record != null) {
                p.println(rn + ":" + name + " -> " + getRecordLabel(d.record) + "[label=" + name + "];");
            }
            if (d.method != null) {
                String mname = StringUtil.quote(d.method.getFullName());
                p.println(mname + "[shape=diamond];");
                p.println(rn + ":" + name + " -> " + mname + "[label=" + name + "];");
            }
        }
    }
}