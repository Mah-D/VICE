/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst;

import cck.parser.ErrorReporter;
import cck.parser.SourcePoint;
import vpc.core.decl.Decl;
import vpc.core.types.*;
import vpc.core.virgil.VirgilClass;
import vpc.core.virgil.VirgilError;
import vpc.tir.expr.Operator;
import vpc.vst.tree.*;

/**
 * The <code>VSTErrorReporter</code> class implements many utility functions
 * that are used by the verifier to generate errors. These utility
 * functions also allow classification of the errors into groups so
 * that they can be checked automatically AutomatedTester, which has
 * programs that are supposed to generate particular errors.
 *
 * @author Ben L. Titzer
 */
public class VSTErrorReporter extends ErrorReporter {

    private String at(VSTNode node) {
        return node.getToken().getSourcePoint().toString();
    }

    private String nm(Type t1) {
        return t1.toString();
    }

    private String nm(VSTTypeRef t1) {
        return t1.toString();
    }

    public void NotAnLvalue(VSTExpr e) {
        throw VirgilError.NotAnLvalue.gen(e);
    }

    public void VariableNotInitialized(VSTVarUse u) {
        throw VirgilError.VariableNotInitialized.gen(u, u.getName());
    }

    public void ThisMustBeInClass(VSTThisLiteral l) {
        throw VirgilError.ThisMustBeInClass.gen(l);
    }

    public void InvalidLiteral(VSTLiteral l) {
        throw VirgilError.InvalidLiteral.gen(l);
    }

    public void InvalidConstructorParam(VSTParamDecl p, String msg) {
        throw VirgilError.InvalidConstructorParam.gen(p, msg);
    }

    public void ParameterRedefined(VSTParamDecl d) {
        throw VirgilError.ParameterRedefined.gen(d, d.getName());
    }

    public void VariableRedefined(VSTLocalVarDecl d) {
        throw VirgilError.VariableRedefined.gen(d, d.getName());
    }

    public void BuiltinRedefined(VSTTypeDecl d) {
        throw VirgilError.BuiltinRedefined.gen(d, d.getName());
    }

    public void TypeRedefined(VSTTypeDecl d, VSTTypeDecl prev) {
        throw VirgilError.TypeRedefined.gen(d, d.getName(), at(prev));
    }

    public void TypeParamRedefined(TypeParam d) {
        throw VirgilError.TypeParameterRedefined.gen(d.getSourcePoint(), d.getName());
    }

    public void MemberRedefined(VSTMemberDecl d, VSTMemberDecl prev) {
        throw VirgilError.MemberRedefined.gen(d, d.getName(), at(prev));
    }

    public void ConstructorRedefined(VSTConstructorDecl d, VSTConstructorDecl prev) {
        throw VirgilError.ConstructorRedefined.gen(d, at(prev));
    }

    public void MemberDefinedInSuper(VSTMemberDecl d) {
        throw VirgilError.MemberDefinedInSuper.gen(d, d.getName());
    }

    public void LocalMustHaveTypeOrInit(VSTLocalVarDecl d) {
        throw VirgilError.LocalMustHaveTypeOrInit.gen(d, d.getName());
    }

    public void RedundantModifier(VSTModifier m) {
        throw VirgilError.RedundantModifier.gen(m, m.toString());
    }

    public void StatementMustBeInLoop(VSTNode n) {
        throw VirgilError.StatementMustBeInLoop.gen(n);
    }

    public void UnreachableCode(VSTNode n) {
        throw VirgilError.UnreachableCode.gen(n);
    }

    public void MissingReturn(VSTNode n) {
        throw VirgilError.MissingReturn.gen(n);
    }

    public void NonVoidReturn(VSTNode n) {
        throw VirgilError.NonVoidReturn.gen(n);
    }

    public void VoidReturn(VSTNode n) {
        throw VirgilError.VoidReturn.gen(n);
    }

    public void NotAStatement(VSTNode n) {
        throw VirgilError.NotAStatement.gen(n);
    }

    public void CannotOverrideReturnType(VSTMethodDecl m) {
        throw VirgilError.CannotOverrideReturnType.gen(m, m.getName());
    }

    public void CannotOverrideTypeParams(VSTMethodDecl m) {
        throw VirgilError.CannotOverrideTypeParams.gen(m, m.getName());
    }

    public void CannotOverrideArity(VSTMethodDecl m) {
        throw VirgilError.CannotOverrideArity.gen(m, m.getName());
    }

    public void CannotOverrideParamType(VSTMethodDecl m, VSTParamDecl p) {
        throw VirgilError.CannotOverrideParamType.gen(p, m.getName());
    }

    public void CannotAssignToMember(VSTExpr a) {
        throw VirgilError.CannotAssignToMember.gen(a);
    }

    public void NoSuperClass(VirgilClass pcd, VSTSuperClause sc) {
        throw VirgilError.NoSuperClass.gen(sc, pcd.getName());
    }

    public void NoDefaultConstructor(VSTNode cd) {
        throw VirgilError.NoDefaultConstructor.gen(cd);
    }

    public void SuperClauseMustBeInClass(VSTSuperClause sc) {
        throw VirgilError.SuperClauseMustBeInClass.gen(sc);
    }

    public void UnresolvedType(VSTTypeRef tref) {
        throw VirgilError.UnresolvedType.gen(tref, nm(tref));
    }

    public void UnresolvedType(String name, SourcePoint pt) {
        throw VirgilError.UnresolvedType.gen(pt, name);
    }

    public void TypeParamArityMismatch(String name, SourcePoint pt, int expected, int found) {
        throw VirgilError.TypeParamArityMismatch.gen(pt, name, String.valueOf(expected), String.valueOf(found));
    }

    public void UnresolvedMember(VSTMemberExpr e, Decl d) {
        throw VirgilError.UnresolvedMember.gen(e, e.getMemberName(), d.getName());
    }

    public void UnresolvedMember(VSTMemberExpr e, Type t) {
        throw VirgilError.UnresolvedMember.gen(e, e.getMemberName(), nm(t));
    }

    public void UnresolvedBinOp(VSTBinOp b) {
        throw VirgilError.UnresolvedBinOp.gen(b, b.token.toString(), nm(b.left.getType()), nm(b.right.getType()));
    }

    public void UnresolvedOperator(VSTExpr e, String op) {
        throw VirgilError.UnresolvedOperator.gen(e, op);
    }

    public void UnresolvedAssignOp(VSTCompoundAssign b, Type t) {
        throw VirgilError.UnresolvedAssignOp.gen(b, b.token.toString(), nm(t));
    }

    public void UnresolvedUnaryOp(VSTUnaryOp u, Type t) {
        throw VirgilError.UnresolvedUnaryOp.gen(u, u.token.toString(), nm(t));
    }

    public void UnresolvedIdentifier(VSTVarUse u) {
        throw VirgilError.UnresolvedIdentifier.gen(u, u.getName());
    }

    public void CyclicInheritance(VSTTypeRef tref) {
        throw VirgilError.CyclicInheritance.gen(tref);
    }

    public void TypeMismatch(VSTNode n, String op, Type expect, Type got) {
        throw VirgilError.TypeMismatch.gen(n, op, nm(expect), nm(got));
    }

    public void ExprCannotBeVoid(VSTNode n) {
        throw VirgilError.ExprCannotBeVoid.gen(n);
    }

    public void CannotInferEmptyArrayType(VSTArrayInitializer i) {
        throw VirgilError.CannotInferEmptyArrayType.gen(i);
    }

    public void CannotInferTypeParam(TypeVar tv) {
        throw VirgilError.CannotInferTypeParam.gen(tv.usePoint, tv.paramDecl.getName());
    }

    public void CannotUnifyElemTypes(VSTExpr e, Type t1, Type t2) {
        throw VirgilError.CannotUnifyElemTypes.gen(e, nm(t1), nm(t2));
    }

    public void CannotUnifyBranchTypes(VSTExpr e, Type t1, Type t2) {
        throw VirgilError.CannotUnifyBranchTypes.gen(e, nm(t1), nm(t2));
    }

    public void InvalidTypeQuery(VSTTypeQueryExpr e, String reason) {
        throw VirgilError.InvalidTypeQuery.gen(e, reason);
    }

    public void InvalidTypeCast(VSTTypeCastExpr e, String reason) {
        throw VirgilError.InvalidTypeCast.gen(e, reason);
    }

    public void ExpectedObjectType(VSTTypeRef tref) {
        throw VirgilError.ExpectedObjectType.gen(tref, nm(tref));
    }

    public void ExpectedReturnType(VSTMethodDecl d, VSTTypeRef tref) {
        throw VirgilError.ExpectedReturnType.gen(d, nm(tref));
    }

    public void ExpectedVarType(VSTTypeRef tref) {
        throw VirgilError.ExpectedVarType.gen(tref, nm(tref));
    }

    public void ExpectedIndexable(VSTExpr e, Type t) {
        throw VirgilError.ExpectedIndexable.gen(e, nm(t));
    }

    public void ExpectedExprType(VSTVarUse u) {
        throw VirgilError.ExpectedExprType.gen(u, u.getName());
    }

    public void ExpectedFunction(VSTExpr e, Type t) {
        throw VirgilError.ExpectedFunction.gen(e, nm(t));
    }

    public void ArgumentCountMismatch(VSTNode a, int expect, int got) {
        throw VirgilError.ArgumentCountMismatch.gen(a, String.valueOf(expect), String.valueOf(got));
    }

    public void ArgumentCountMismatchInNew(VSTNewObjectExpr a, int expect, int got) {
        throw VirgilError.ArgumentCountMismatchInNew.gen(a, String.valueOf(expect), String.valueOf(got));
    }

    public void NotAnEqualityType(VSTExpr e, Type t) {
        throw VirgilError.NotAnEqualityType.gen(e, nm(t));
    }

    public void CannotCompareValues(VSTExpr e, Type t1, Type t2) {
        throw VirgilError.CannotCompareValues.gen(e, nm(t1), nm(t2));
    }

    public void NonSwitchableType(VSTExpr e, Type t) {
        throw VirgilError.NonSwitchableType.gen(e, nm(t));
    }

    public void DuplicateCase(VSTExpr c1, VSTExpr prev) {
        throw VirgilError.DuplicateCase.gen(c1, at(prev));
    }

    public void DefaultCaseRedefined(VSTSwitchCase dc, VSTSwitchCase prev) {
        throw VirgilError.DefaultCaseRedefined.gen(dc, at(prev));
    }

    public void NotComputable(VSTExpr e) {
        throw VirgilError.NotComputable.gen(e);
    }

    public void UnexpectedException(VSTExpr e, Operator.Exception ex) {
        throw VirgilError.UnexpectedException.gen(e, ex.type);
    }

    public void InvalidModifier(VSTModifier m) {
        throw VirgilError.InvalidModifier.gen(m, m.toString());
    }
}
