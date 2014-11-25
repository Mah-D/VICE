/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Nov 16, 2006
 */

package vpc.tir.opt;

import cck.parser.SourcePoint;
import cck.text.StringUtil;
import cck.text.Terminal;
import cck.util.Util;
import vpc.core.Program;
import vpc.core.base.PrimVoid;
import vpc.core.decl.Method;
import vpc.sched.Stage;
import vpc.tir.*;

import java.io.IOException;
import java.util.Iterator;

/**
 * The <code>TIRChecker</code> class performs a number of consistency checks on
 * TIR code that helps to maintain invariants across compilation passes.
 *
 * @author Ben L. Titzer
 */
public class TIRChecker extends Stage {

    private Method currentMethod;

    public void visitProgram(Program p) throws IOException {
        for ( Method m : p.closure.methods ) {
            currentMethod = m;
            TIRRep r = TIRUtil.getRep(m);
            Visitor visitor = new Visitor();
            visitor.visitBody(r.getBody());
        }
    }

    protected class Visitor extends TIRExprVisitor<TIRExpr, Visitor.Context> {

        public class Context {
            final Context parent;
            final boolean expression;
            final TIRBlock block;
            public Context(Context p, boolean b, TIRBlock bl) {
                parent = p;
                expression = b;
                block = bl;
            }
        }

        Visitor() {
        }

        public TIRExpr visit(TIRExpr e, Context c) {
            throw TIRUtil.fail("unknown TIR expression kind in checker", e);
        }

        public TIRExpr visit(TIROperator e, Context c) {
            basicChecks(e, c);
            int arity = e.operands.length;
            if ( arity != e.operator.getOperandTypes().length ) {
                // check the number of operands match the arity of the operator.
                fail("arity mismatch", e);
            }
            visitExprs(e.operands, c);
            return e;
        }

        public TIRExpr visit(TIRConst e, Context c) {
            basicChecks(e, c);
            return e;
        }

        public TIRExpr visit(TIRLocal.Get e, Context c) {
            basicChecks(e, c);
            return nonVoid(e, c);
        }

        public TIRExpr visit(TIRCall e, Context c) {
            basicChecks(e, c);
            visitExpr(e.func, c);
            visitExprs(e.arguments, c);
            return e;
        }

        public TIRExpr visit(TIRLocal.Set e, Context c) {
            basicChecks(e, c);
            visitExpr(e.value, c);
            return e;
        }

        public TIRExpr visit(TIRThrow e, Context c) {
            basicChecks(e, c);
            return e;
        }

        public TIRExpr visit(TIRReturn e, Context c) {
            basicChecks(e, c);
            assertStmt(e, c);
            visitExpr(e.value, c);
            return e;
        }

        public TIRExpr visit(TIRIfExpr e, Context c) {
            basicChecks(e, c);
            visitExpr(e.condition, c);
            if ( c.expression ) {
                visitExpr(e.true_target, c);
                visitExpr(e.false_target, c);
            } else {
                visitStmt(e.true_target, c);
                visitStmt(e.false_target, c);
            }
            return e;
        }

        public TIRExpr visit(TIRSwitch e, Context c) {
            basicChecks(e, c);
            visitExpr(e.expr, c);
            for (TIRSwitch.Case oc : e.cases) {
                visitStmt(oc.body, c);
            }
            visitStmt(e.defcase.body, c);
            return e;
        }

        public TIRExpr visit(TIRBlock e, Context c) {
            basicChecks(e, c);
            Context nc = new Context(c, c.expression, e);
            Iterator<TIRExpr> i = e.list.iterator();
            int cntr = 0, last = e.list.size() - 1;
            while ( i.hasNext() ) {
                TIRExpr se = i.next();
                if ( c.expression && cntr == last ) visitExpr(se, nc);
                else visitStmt(se, nc);
                cntr++;
            }
            return e;
        }

        public TIRExpr visit(TIRBlock.Break e, Context c) {
            searchForBlock(e, e.block, c);
            return assertStmt(e, c);
        }

        public TIRExpr visit(TIRBlock.Continue e, Context c) {
            searchForBlock(e, e.block, c);
            return assertStmt(e, c);
        }

        void searchForBlock(TIRExpr e, TIRBlock b, Context c) {
            for ( Context p = c; p != null; p = p.parent ) {
                if ( p.block == b ) return;
            }
            fail("Control expression not nested in block", e);
        }

        protected TIRExpr[] visitExprs(TIRExpr[] e, Context c) {
            for ( TIRExpr ie : e ) visitExpr(ie, c);
            return e;
        }

        protected TIRExpr visitExpr(TIRExpr e, Context c) {
            return e.accept(this, new Context(c, true, c.block));
        }

        protected TIRExpr visitStmt(TIRExpr e, Context c) {
            return e.accept(this, new Context(c, false, c.block));
        }

        protected TIRExpr visitBody(TIRExpr e) {
            Context nc = new Context(null, false, null);
            return e.accept(this, nc);
        }
    }

    private TIRExpr nonVoid(TIRExpr e, Visitor.Context c) {
        if ( e.getType() == PrimVoid.TYPE ) fail("should have non-void type", e);
        return e;
    }

    private TIRExpr assertStmt(TIRExpr e, Visitor.Context c) {
        basicChecks(e, c);
        if ( c.expression ) fail("should be a statement", e);
        return e;
    }

    private void basicChecks(TIRExpr e, TIRChecker.Visitor.Context c) {
        if ( e.getType() == null ) fail("no type", e);
        if ( e.getSourcePoint() == null ) warn("no source information", e);
    }

    private void fail(String s, TIRExpr e) {
        String nstr = StringUtil.quote(e.getClass());
        SourcePoint sp = e.getSourcePoint();
        String mn = currentMethod.getFullName();
        throw Util.failure("TIRChecker failure", s + " on node " + nstr + " @ " + sp +" in "+mn);
    }

    private void warn(String s, TIRExpr e) {
        String nstr = StringUtil.quote(e.getClass());
        String mn = currentMethod.getFullName();
        Terminal.print(Terminal.ERROR_COLOR, "TIRChecker warning");
        Terminal.println(": " + s + " on node " + nstr + " in "+mn);
    }
}
