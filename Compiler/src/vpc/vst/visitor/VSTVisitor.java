/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.visitor;

import vpc.vst.tree.*;

/**
 * The <code>VSTVisitor</code> interface represents the visitor pattern and is
 * implemented by classes wishing to traverse a syntax tree.
 *
 * @author Ben L. Titzer
 */
public interface VSTVisitor<E> {

    public void visit(VSTAppExpr a, E env);

    public void visit(VSTArrayInitializer a, E env);

    public void visit(VSTAssign a, E env);

    public void visit(VSTBinOp b, E env);

    public void visit(VSTBlock b, E env);

    public void visit(VSTBoolLiteral b, E env);

    public void visit(VSTBreakStmt b, E env);

    public void visit(VSTCharLiteral c, E env);

    public void visit(VSTClassDecl d, E env);

    public void visit(VSTTypeAliasDecl d, E env);

    public void visit(VSTComparison c, E env);

    public void visit(VSTComponentDecl d, E env);

    public void visit(VSTCompoundAssign c, E env);

    public void visit(VSTConstructorDecl c, E env);

    public void visit(VSTContinueStmt c, E env);

    public void visit(VSTDoWhileStmt s, E env);

    public void visit(VSTIndexExpr a, E env);

    public void visit(VSTEmptyStmt e, E env);

    public void visit(VSTExprStmt s, E env);

    public void visit(VSTFieldDecl d, E env);

    public void visit(VSTMemberExpr a, E env);

    public void visit(VSTForStmt s, E env);

    public void visit(VSTIfStmt s, E env);

    public void visit(VSTTupleExpr s, E env);

    public void visit(VSTTypeQueryExpr e, E env);

    public void visit(VSTTypeCastExpr e, E env);

    public void visit(VSTIntLiteral i, E env);

    public void visit(VSTRawLiteral i, E env);

    public void visit(VSTMethodDecl d, E env);

    public void visit(VSTParamDecl p, E env);

    public void visit(VSTPreOp p, E env);

    public void visit(VSTPostOp p, E env);

    public void visit(VSTModule m, E env);

    public void visit(VSTNewArrayExpr e, E env);

    public void visit(VSTNewObjectExpr e, E env);

    public void visit(VSTNullLiteral l, E env);

    public void visit(VSTReturnStmt r, E env);

    public void visit(VSTStringLiteral l, E env);

    public void visit(VSTSuperClause c, E env);

    public void visit(VSTSwitchCase c, E env);

    public void visit(VSTSwitchStmt s, E env);

    public void visit(VSTTernaryExpr e, E env);

    public void visit(VSTThisLiteral l, E env);

    public void visit(VSTTypeRef r, E env);

    public void visit(VSTUnaryOp u, E env);

    public void visit(VSTLocalVarDecl d, E env);

    public void visit(VSTVarUse u, E env);

    public void visit(VSTWhileStmt s, E env);

}
