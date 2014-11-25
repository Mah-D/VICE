/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;

import cck.text.Printer;

public class TIRPrinter extends TIRExprVisitor<Object, Object> {

    private final Printer p;

    public TIRPrinter(Printer p) {
        this.p = p;
    }

    public Object visit(TIRExpr e, Object env) {
        p.print(e.toString());
        return null;
    }

    public Object visit(TIRIfExpr s, Object env) {
        p.print("if ( " + s.condition.toString() + " ) ");
        print(s.true_target);
        if (s.false_target != null) {
            p.print("else ");
            print(s.false_target);
        }
        return null;
    }

    public Object visit(TIRSwitch s, Object env) {
        p.startblock("switch (" + s.expr.toString() + ")");
        for (TIRSwitch.Case c : s.cases) {
            p.print("case (");
            p.print(TIRUtil.exprsToString(c.value));
            p.print(") ");
            print(c.body);
        }

        if (s.defcase != null) {
            p.print("default ");
            print(s.defcase.body);
        }
        return null;
    }

    public Object visit(TIRBlock b, Object env) {
        p.startblock("seq");
        for (TIRExpr e : b.list) {
            print(e);
            p.println(";");
        }
        p.endblock();
        return null;
    }

    private void print(TIRExpr obj) {
        obj.accept(this, null);
    }
}
