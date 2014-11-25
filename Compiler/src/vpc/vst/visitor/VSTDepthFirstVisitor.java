/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.visitor;

import vpc.vst.tree.*;

import java.util.List;


/**
 * The <code>VSTDepthFirstVisitor</code> class is a basic implementation of the
 * <code>VSTVisitor</code> interface that provides enough base functionality
 * to be useful. Visitors that are only interested in VSTNodes of a particular
 * type can then subclass this class and override the methods corresponding
 * to the types they are interested in.
 *
 * @author Ben L. Titzer
 */
public class VSTDepthFirstVisitor<E> implements VSTVisitor<E> {

    public void visit(VSTArrayInitializer a, E env) {
        visitExprs(a.list, env);
    }

    public void visit(VSTAssign a, E env) {
        a.value.accept(this, env);
        a.target.accept(this, env);
    }

    public void visit(VSTBinOp b, E env) {
        b.left.accept(this, env);
        b.right.accept(this, env);
    }

    public void visit(VSTComparison c, E env) {
        c.left.accept(this, env);
        c.right.accept(this, env);
    }

    public void visit(VSTBlock b, E env) {
        visitStmts(b.stmts, env);
    }

    public void visit(VSTBoolLiteral b, E env) {
    }

    public void visit(VSTBreakStmt b, E env) {
    }

    public void visit(VSTCharLiteral c, E env) {
    }

    public void visit(VSTClassDecl d, E env) {
        visitFieldDecls(d.fields, env);
        visitMethodDecls(d.methods, env);
        visitIfNonNull(d.declaredConstr, env);
    }

    public void visit(VSTComponentDecl d, E env) {
        visitFieldDecls(d.fields, env);
        visitMethodDecls(d.methods, env);
        visitIfNonNull(d.declaredConstr, env);
    }

    public void visit(VSTTypeAliasDecl d, E env) {
        // do nothing.
    }

    public void visit(VSTCompoundAssign c, E env) {
        c.value.accept(this, env);
        c.target.accept(this, env);
    }

    public void visit(VSTContinueStmt c, E env) {
    }

    public void visit(VSTDoWhileStmt s, E env) {
        s.body.accept(this, env);
        s.cond.accept(this, env);
    }

    public void visit(VSTIndexExpr a, E env) {
        a.array.accept(this, env);
        a.index.accept(this, env);
    }

    public void visit(VSTEmptyStmt e, E env) {
    }

    public void visit(VSTExprStmt s, E env) {
        s.expr.accept(this, env);
    }

    public void visit(VSTFieldDecl d, E env) {
        visitModifiers(d.modifiers, env);
        visitIfNonNull(d.init, env);
    }

    public void visit(VSTMemberExpr a, E env) {
        a.expr.accept(this, env);
    }

    public void visit(VSTForStmt s, E env) {
        if (s.init != null) visitExprs(s.init, env);
        visitIfNonNull(s.cond, env);
        if (s.update != null) visitExprs(s.update, env);
        s.body.accept(this, env);
    }

    public void visit(VSTIfStmt s, E env) {
        s.cond.accept(this, env);
        visitIfNonNull(s.trueStmt, env);
        visitIfNonNull(s.falseStmt, env);
    }

    public void visit(VSTTypeQueryExpr e, E env) {
        e.expr.accept(this, env);
    }

    public void visit(VSTTypeCastExpr e, E env) {
        e.expr.accept(this, env);
    }

    public void visit(VSTIntLiteral i, E env) {
    }

    public void visit(VSTRawLiteral i, E env) {
    }

    public void visitFieldDecls(List<VSTFieldDecl> l, E env) {
        for (VSTFieldDecl e : l) e.accept(this, env);
    }

    public void visitMethodDecls(List<VSTMethodDecl> l, E env) {
        for (VSTMethodDecl e : l) e.accept(this, env);
    }

    public void visitExprs(List<VSTExpr> l, E env) {
        for (VSTExpr e : l) e.accept(this, env);
    }

    public void visitParamDecls(List<VSTParamDecl> l, E env) {
        for (VSTParamDecl m : l) m.accept(this, env);
    }

    private void visitModifiers(List<VSTModifier> l, E env) {
        for (VSTModifier m : l) m.accept(this, env);
    }

    public void visitStmts(List<VSTStmt> l, E env) {
        for (VSTStmt m : l) m.accept(this, env);
    }

    private void visitTypeDecls(List<VSTTypeDecl> l, E env) {
        for (VSTTypeDecl m : l) m.accept(this, env);
    }

    public void visit(VSTMethodDecl d, E env) {
        visitIfNonNull(d.block, env);
    }

    public void visit(VSTConstructorDecl d, E env) {
        visitIfNonNull(d.supclause, env);
        visitIfNonNull(d.block, env);
    }

    public void visit(VSTParamDecl p, E env) {
    }

    public void visit(VSTModule m, E env) {
        visitTypeDecls(m.decls, env);
    }

    public void visit(VSTNewArrayExpr e, E env) {
        visitExprs(e.list, env);
    }

    public void visit(VSTNewObjectExpr e, E env) {
        e.args.accept(this, env);
    }

    public void visit(VSTNullLiteral l, E env) {
    }

    public void visit(VSTReturnStmt r, E env) {
        visitIfNonNull(r.expr, env);
    }

    public void visit(VSTAppExpr a, E env) {
        a.func.accept(this, env);
        a.args.accept(this, env);
    }

    public void visit(VSTStringLiteral l, E env) {
    }

    public void visit(VSTSwitchCase c, E env) {
        if (c.list != null) visitExprs(c.list, env);
        c.stmt.accept(this, env);
    }

    public void visit(VSTSwitchStmt s, E env) {
        s.value.accept(this, env);
        visitSwitchCases(s.cases, env);
    }

    public void visitSwitchCases(List<VSTSwitchCase> l, E env) {
        for (VSTSwitchCase c : l) c.accept(this, env);
    }

    public void visit(VSTTernaryExpr e, E env) {
        e.cond.accept(this, env);
        e.trueExpr.accept(this, env);
        e.falseExpr.accept(this, env);
    }

    public void visit(VSTThisLiteral l, E env) {
    }

    public void visit(VSTSuperClause c, E env) {
        c.args.accept(this, env);
    }

    public void visit(VSTTypeRef r, E env) {
    }

    public void visit(VSTUnaryOp u, E env) {
        u.expr.accept(this, env);
    }

    public void visit(VSTPreOp p, E env) {
        p.expr.accept(this, env);
    }

    public void visit(VSTPostOp p, E env) {
        p.expr.accept(this, env);
    }

    public void visit(VSTLocalVarDecl d, E env) {
        visitIfNonNull(d.init, env);
    }

    public void visit(VSTVarUse u, E env) {
    }

    public void visit(VSTTupleExpr t, E env) {
        for (VSTExpr e : t.exprs) {
            e.accept(this, env);
        }
    }

    public void visit(VSTWhileStmt s, E env) {
        s.cond.accept(this, env);
        s.body.accept(this, env);
    }

    protected void visitIfNonNull(VSTNode n, E env) {
        if (n != null) n.accept(this, env);
    }
}
