/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.text.Printer;
import vpc.core.base.Function;
import vpc.vst.visitor.VSTDepthFirstVisitor;

import java.util.List;

/**
 * The <class>VSTPrinter</code> is a utility that allows an abstract syntax tree
 * to be printed out. It also serves as a demonstration of how to implement a
 * visitor for the VST of a Virgil program.
 *
 * @author Ben L. Titzer
 */
public class VSTPrinter extends VSTDepthFirstVisitor<Printer> {

    public void visit(VSTClassDecl d, Printer p) {
        p.print("class " + d.token + " ");
        if (d.parent != null) {
            p.print("extends ");
            visit(d.parent, p);
            p.print(" ");
        }
        p.startblock();
        super.visit(d, p);
        p.endblock();
    }

    public void visit(VSTArrayInitializer a, Printer p) {
        p.print("{ ");
        visitExprs(a.list, p);
        p.print(" }");
    }

    public void visit(VSTComponentDecl d, Printer p) {
        p.println("component " + d.token + " ");
        p.startblock();
        super.visit(d, p);
        p.endblock();
    }

    public void visit(VSTBlock b, Printer p) {
        p.println("{");
        p.indent();
        visitStmts(b.stmts, p);
        p.unindent();
        p.nextln();
        p.println("}");
    }

    public void visit(VSTWhileStmt s, Printer p) {
        p.print("while ( ");
        s.cond.accept(this, p);
        p.print(" ) ");
        s.body.accept(this, p);
        p.nextln();
    }

    public void visit(VSTSwitchStmt s, Printer p) {
        p.print("switch ( ");
        s.value.accept(this, p);
        p.print(" ) ");
        p.startblock();
        visitSwitchCases(s.cases, p);
        visitIfNonNull(s.defcase, p);
        p.endblock();
    }

    public void visit(VSTSwitchCase c, Printer p) {
        if (c.list != null) {
            p.print("case ( ");
            visitExprs(c.list, p);
            p.print(" ) ");
        } else {
            p.print("default ");
        }
        c.stmt.accept(this, p);
        p.nextln();
    }

    public void visit(VSTDoWhileStmt s, Printer p) {
        p.print("do ");
        s.body.accept(this, p);
        p.print("while ( ");
        s.cond.accept(this, p);
        p.println(" );");
    }

    public void visitExprs(List<VSTExpr> l, Printer p) {
        boolean prev = false;
        for (VSTExpr e : l) {
            if (prev) p.print(", ");
            e.accept(this, p);
            prev = true;
        }
    }

    public void visitParamDecl(List<VSTParamDecl> d, Printer p) {
        boolean prev = false;
        for (VSTParamDecl pd : d) {
            if (prev) p.print(", ");
            pd.accept(this, p);
            prev = true;
        }
    }


    public void visit(VSTForStmt s, Printer p) {
        p.print("for ( ");
        visitExprs(s.init, p);
        p.print("; ");
        if (s.cond != null) s.cond.accept(this, p);
        p.print("; ");
        visitExprs(s.update, p);
        if (!s.update.isEmpty()) p.print(" "); // slight space adjustment
        p.print(") ");
        s.body.accept(this, p);
        p.nextln();
    }

    public void visit(VSTIfStmt s, Printer p) {
        p.print("if ( ");
        s.cond.accept(this, p);
        p.print(" ) ");
        s.trueStmt.accept(this, p);
        if (s.falseStmt != null && !(s.falseStmt instanceof VSTEmptyStmt)) {
            p.print("else ");
            s.falseStmt.accept(this, p);
        }
        p.nextln();
    }

    public void visit(VSTBreakStmt s, Printer p) {
        p.println("break;");
    }

    public void visit(VSTContinueStmt s, Printer p) {
        p.println("continue;");
    }

    public void visit(VSTReturnStmt s, Printer p) {
        p.print("return");
        if (s.expr != null) {
            p.print(" ");
            s.expr.accept(this, p);
        }
        p.println(";");
    }

    public void visit(VSTEmptyStmt s, Printer p) {
        p.println(";");
    }

    public void visit(VSTExprStmt s, Printer p) {
        s.expr.accept(this, p);
        p.println(";");
    }

    public void visit(VSTAssign a, Printer p) {
        embed(a, a.target, p);
        p.print(" = ");
        embed(a, a.value, p);
    }

    public void visit(VSTCompoundAssign a, Printer p) {
        embed(a, a.target, p);
        p.print(" " + a.token + " ");
        embed(a, a.value, p);
    }

    // helper for parentheses
    protected void embed(VSTExpr out, VSTExpr in, Printer p) {
        if (in.getPrecedence() < out.getPrecedence()) p.print("(");
        in.accept(this, p);
        if (in.getPrecedence() < out.getPrecedence()) p.print(")");
    }

    public void visit(VSTTernaryExpr e, Printer p) {
        embed(e, e.cond, p);
        p.print(" ? ");
        embed(e, e.trueExpr, p);
        p.print(" : ");
        embed(e, e.falseExpr, p);
    }

    public void visit(VSTBinOp b, Printer p) {
        embed(b, b.left, p);
        p.print(" " + b.token + " ");
        embed(b, b.right, p);
    }

    public void visit(VSTComparison c, Printer p) {
        embed(c, c.left, p);
        p.print(" " + c.token + " ");
        embed(c, c.right, p);
    }

    public void visit(VSTUnaryOp u, Printer p) {
        p.print(u.token.toString());
        embed(u, u.expr, p);
    }

    public void visit(VSTMemberExpr e, Printer p) {
        embed(e, e.expr, p);
        p.print("." + e.token);
    }

    public void visit(VSTIndexExpr e, Printer p) {
        embed(e, e.array, p);
        p.print("[");
        e.index.accept(this, p);
        p.print("]");
    }

    public void visit(VSTAppExpr c, Printer p) {
        embed(c, c.func, p);
        p.print("(");
        c.args.accept(this, p);
        p.print(")");
    }

    public void visit(VSTNewObjectExpr n, Printer p) {
        p.print("new ");
        visit(n.type, p);
        p.print("(");
        n.args.accept(this, p);
        p.print(")");
    }

    public void visit(VSTNewArrayExpr n, Printer p) {
        p.print("new ");
        visit(n.type, p);
        for (VSTExpr e : n.list) {
            p.print("[");
            e.accept(this, p);
            p.print("]");
        }
    }

    public void visit(VSTVarUse u, Printer p) {
        p.print(u.token.toString());
    }

    public void visit(VSTIntLiteral l, Printer p) {
        p.print(l.token.toString());
    }

    public void visit(VSTBoolLiteral l, Printer p) {
        p.print(l.token.toString());
    }

    public void visit(VSTThisLiteral l, Printer p) {
        p.print(l.token.toString());
    }

    public void visit(VSTNullLiteral l, Printer p) {
        p.print(l.token.toString());
    }

    public void visit(VSTCharLiteral l, Printer p) {
        p.print(l.token.toString());
    }

    public void visit(VSTStringLiteral l, Printer p) {
        p.print(l.token.toString());
    }

    public void visit(VSTTypeRef r, Printer p) {
        p.print(r.toString());
    }

    public void visit(VSTMethodDecl m, Printer p) {
        p.println("");
        visitModifiers(m.modifiers, p);
        p.print("method " + m.getToken() + "(");
        visitParamDecls(m.params, p);
        p.print(")" + Function.asReturnType(m.returnType));
        if (m.block != null) {
            p.print(" ");
            m.block.accept(this, p);
        } else p.println(";");

    }

    public void visit(VSTConstructorDecl c, Printer p) {
        p.print("constructor(");
        visitParamDecls(c.params, p);
        p.print(")");
        if (c.supclause != null) {
            p.print(": super(");
            c.supclause.args.accept(this, p);
            p.print(")");
        }
        if (c.block != null) {
            p.print(" ");
            c.block.accept(this, p);
        } else p.println(";");
    }

    public void visit(VSTFieldDecl f, Printer p) {
        visitModifiers(f.modifiers, p);
        p.print("field " + f.getToken() + ": ");
        visit(f.memberType, p);
        if (f.init != null) {
            p.print(" = ");
            f.init.accept(this, p);
        }
        p.println(";");
    }

    public void visit(VSTLocalVarDecl d, Printer p) {
        p.print("local " + d.token);

        if (d.typeRef != null) {
            // locals with explicit initialization have their types inferred.
            p.print(": ");
            visit(d.typeRef, p);
        }

        if (d.init != null) {
            p.print(" = ");
            d.init.accept(this, p);
        }
        p.println(";");
    }

    public void visit(VSTParamDecl d, Printer p) {
        p.print(d.getName() + ": ");
        visit(d.getTypeRef(), p);
    }

    private void visitModifiers(List<VSTModifier> l, Printer p) {
        for (VSTModifier m : l) {
            p.print(m.token + " ");
        }
    }


}
