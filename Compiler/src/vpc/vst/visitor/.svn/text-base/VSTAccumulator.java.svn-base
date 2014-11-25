/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.visitor;

import vpc.vst.tree.*;

/**
 * The <code>VSTAccumulator</code> interface represents a visitor for expressions
 * in the abstract syntax tree that computes a value for each of the visited expressions.
 * This is useful for transforming the syntax tree into another form or reducing it.
 */
public interface VSTAccumulator<V> {
    public V visit(VSTAppExpr a);

    public V visit(VSTArrayInitializer e);

    public V visit(VSTAssign e);

    public V visit(VSTBinOp e);

    public V visit(VSTIndexExpr e);

    public V visit(VSTTypeQueryExpr e);

    public V visit(VSTTypeCastExpr e);

    public V visit(VSTBoolLiteral e);

    public V visit(VSTCharLiteral e);

    public V visit(VSTIntLiteral e);

    public V visit(VSTRawLiteral e);

    public V visit(VSTStringLiteral e);

    public V visit(VSTNullLiteral e);

    public V visit(VSTThisLiteral e);

    public V visit(VSTNewArrayExpr e);

    public V visit(VSTNewObjectExpr e);

    public V visit(VSTCompoundAssign e);

    public V visit(VSTTernaryExpr e);

    public V visit(VSTUnaryOp e);

    public V visit(VSTPreOp p);

    public V visit(VSTPostOp p);

    public V visit(VSTMemberExpr e);

    public V visit(VSTVarUse e);

    public V visit(VSTComparison c);

    public V visit(VSTBlock b);

    public V visit(VSTBreakStmt b);

    public V visit(VSTContinueStmt c);

    public V visit(VSTDoWhileStmt s);

    public V visit(VSTEmptyStmt e);

    public V visit(VSTExprStmt s);

    public V visit(VSTForStmt s);

    public V visit(VSTIfStmt s);

    public V visit(VSTTupleExpr s);

    public V visit(VSTReturnStmt r);

    public V visit(VSTSwitchCase s);

    public V visit(VSTSwitchStmt s);

    public V visit(VSTLocalVarDecl d);

    public V visit(VSTWhileStmt s);

    public V visit(VSTSuperClause s);
}
