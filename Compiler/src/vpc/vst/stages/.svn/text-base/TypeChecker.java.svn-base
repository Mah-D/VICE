/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.stages;

import cck.parser.AbstractToken;
import cck.text.StringUtil;
import static cck.text.StringUtil.quote;
import cck.util.Util;
import vpc.core.Program;
import vpc.core.base.*;
import vpc.core.decl.*;
import vpc.core.types.*;
import vpc.core.virgil.*;
import static vpc.core.virgil.V2TypeSystem.*;
import vpc.hil.Device;
import vpc.tir.expr.Operator;
import vpc.util.Ovid;
import vpc.vst.VSTErrorReporter;
import vpc.vst.VSTUtil;
import vpc.vst.tree.*;
import vpc.vst.visitor.VSTDepthFirstVisitor;

import java.util.Map;
import java.util.List;

/**
 * The <code>TypeChecker</code> class implements a typechecker that visits Virgil
 * syntax tree nodes, infers types, and checks that the types of all expressions
 * are correct according to the type rules of the Virgil language.
 *
 * @author Ben L. Titzer
 */
public class TypeChecker extends VSTDepthFirstVisitor<Type> {

    public static final TypeVar[] NOTYPEVARS = new TypeVar[0];
    public static final boolean OPEN = false;
    public static final boolean CLOSED = true;

    private TypeSystem typeSystem;
    private CompoundDecl container;
    private Program program;
    private VSTErrorReporter ERROR;
    private TypeEnv typeEnv;
    private VirgilClass.IType thisClass;
    private Type thisReturnType;

    public TypeChecker(Program p, VSTErrorReporter error) {
        typeSystem = p.typeSystem;
        program = p;
        ERROR = error;
        typeEnv = p.typeEnv;
    }

    public void typeCheckClass(VirgilClass pcd) {
        VSTClassDecl d = VSTUtil.vstRepOf(pcd);
        container = pcd;
        TypeEnv parent = typeEnv;
        typeEnv = d.typeEnv;
        visit(d, null);
        typeEnv = parent;
    }

    public void typeCheckComponent(VirgilComponent pcd) {
        VSTComponentDecl d = VSTUtil.vstRepOf(pcd);
        if (d != null) {
            container = pcd;
            visit(d, null);
        }
    }

    public void visit(VSTClassDecl d, Type t) {
        if ( d.parent != null ) resolveClassType(d.parent);
        VirgilClass vc = program.virgil.getClassDecl(d.token.image);
        thisClass = vc.getParameterizedType(program.typeCache);
        super.visit(d, null);
        thisClass = null;
    }

    public void visit(VSTFieldDecl d, Type t) {
        checkInitializer(d.getName(), d.memberType, d.init);
    }

    public void visit(VSTMethodDecl d, Type t) {
        TypeEnv parent = typeEnv;
        typeEnv = d.typeEnv;
        visitParamDecls(d.params, null);
        thisReturnType = d.returnType.getType();
        if ( isComponent(thisReturnType) )
            ERROR.ExpectedReturnType(d, d.returnType);
        super.visit(d, null);
        typeEnv = parent;
    }

    public void visit(VSTConstructorDecl d, Type t) {
        visitParamDecls(d.params, null);
        super.visit(d, null);
    }

    public void visit(VSTParamDecl d, Type t) {
        resolveVarType(d.type);
        if (d.field != null) {
            Type from = d.getType();
            Type dst = d.field.getType();
            if (!typeSystem.isAssignable(from, dst)) {
                ERROR.TypeMismatch(d, "implicit field initialization", dst, from);
            }
        }
    }

    public void visit(VSTLocalVarDecl d, Type t) {
        d.typeRef = checkInitializer(d.getName(), d.typeRef, d.init);
    }

    private VSTTypeRef checkInitializer(String name, VSTTypeRef decl, VSTExpr init) {
        if (decl == null) {
            // if the type is not declared, infer and check
            Type type = inferClosedType(init);
            VSTTypeRef tref = new VSTTypeRef(init.getToken(), type);
            resolveVarType(tref);
            return tref;
        } else {
            Type dst = resolveVarType(decl);
            if (init != null) {
                if (isArray(dst) && init instanceof VSTArrayInitializer)
                    typeCheckArrayInitializer(name, (VirgilArray.IType) dst, (VSTArrayInitializer) init);
                else {
                    unifyAndTypeCheck(msg("initialization of", name), init, dst);
                }
            }
            return decl;
        }
    }

    private void unifyAndTypeCheck(String msg, VSTExpr e, Type dst) {
        typeCheckExpr(msg, e, inferType(e, dst, CLOSED), dst);
    }

    public void visit(VSTArrayInitializer a, Type outer) {
        // this method is only called in very specific circumstances:
        // when the type of an array initializer must be inferred
        // in order to determine the type of a local variable that
        // has been declared without a type signature.

        if (a.list.size() < 1) ERROR.CannotInferEmptyArrayType(a);

        Type comp = null;
        for (VSTExpr e : a.list) {
            Type t = inferClosedType(e);
            if (comp == null) comp = t;
            else {
                try {
                    comp = typeSystem.unify(program.typeCache, comp, t);
                } catch (UnificationError unificationError) {
                    ERROR.CannotUnifyElemTypes(e, comp, t);
                }
            }
        }
        a.setType(getArrayType(comp));
    }

    private void typeCheckArrayInitializer(String name, VirgilArray.IType outertype, VSTArrayInitializer outerexpr) {
        Type innertype = outertype.getElemType();
        for (VSTExpr innerexpr : outerexpr.list) {
            if (isArray(innertype) && innerexpr instanceof VSTArrayInitializer)
                typeCheckArrayInitializer(name, (VirgilArray.IType) innertype, (VSTArrayInitializer) innerexpr);
            else inferAndTypeCheck(msg("initialization of array", name), innerexpr, innertype);
        }
        outerexpr.setType(outertype);
    }

    private void inferAndTypeCheck(String msg, VSTExpr e, Type dst) {
        // visit the node to compute its type
        e.accept(this, dst);
        // type check the expression now
        typeCheckExpr(msg, e, e.getType(), dst);
    }

    private void typeCheckExpr(String msg, VSTExpr e, Type from, Type dst) {
        if (!(program.language instanceof V3Language) && isVoid(from)) {
            // no expression is allowed to be of type void
            ERROR.ExprCannotBeVoid(e);
        } else if ( typeSystem.isAssignable(from, dst) ) {
            // if assignable, no promotion is necessary
            return;
        } else if ( typeSystem.isPromotable(from, dst) ) {
            // if promotable, record the need for promotion code
            if ( from != dst ) e.setPromotionType(dst);
            return;
        }
        ERROR.TypeMismatch(e, msg, dst, from);
    }

    public void visit(VSTAssign a, Type t) {
        Type dst = inferClosedType(a.target);
        VSTExpr target = checkLvalue(a.target);
        if (target instanceof VSTMemberExpr) {
            checkMemberAssignment(a, ((VSTMemberExpr) target).binding);
        } else if (target instanceof VSTVarUse) {
            checkMemberAssignment(a, ((VSTVarUse) target).binding);
        }
        unifyAndTypeCheck(msg("assignment", null), a.value, dst);
        a.setType(dst);
    }

    protected VSTExpr checkLvalue(VSTExpr e) {
        if (e instanceof VSTIndexExpr ) {
            VSTIndexExpr vi = (VSTIndexExpr)e;
            Type atype = vi.array.getType();
            if ( V2TypeSystem.isRaw(atype) ) {
                checkLvalue(vi.array);
                return e;
            }
            return e;
        } else if (e instanceof VSTAppExpr) {
            VSTAppExpr ae = (VSTAppExpr)e;
            Type atype = ae.func.getType();
            if (isArray(atype)) return e;
            if (isAssociation(atype)) return e;
        } else if (e instanceof VSTMemberExpr) {
            return e;
        } else if (e instanceof VSTVarUse) {
            return e;
        }
        ERROR.NotAnLvalue(e);
        return e;
    }

    public void visit(VSTCompoundAssign a, Type t) {
        Type dtype = inferClosedType(a.target);
        VSTExpr target = checkLvalue(a.target);
        String op = binopFromCompoundAssign(a.getToken().image);

        Capability.BinOp binop = typeSystem.resolveBinOp(op, dtype, inferClosedType(a.value));

        if (binop == null) ERROR.UnresolvedAssignOp(a, dtype);

        checkOperand(target, binop, binop.binop.type1);
        checkOperand(a.value, binop, binop.binop.type2);

        a.setBinOp(binop);
        a.setType(dtype);

        if ( !typeSystem.isAssignable(binop.binop.getResultType(), dtype) ) {
            ERROR.TypeMismatch(a, "compound assignment", dtype, binop.binop.getResultType());
        }
    }

    private String binopFromCompoundAssign(String image) {
        return image.substring(0, image.length() - 1);
    }

    private void checkMemberAssignment(VSTExpr a, VSTBinding binding) {
        if (binding instanceof VSTBinding.ClassField) {
            VSTBinding.ClassField cf = (VSTBinding.ClassField)binding;
            VSTFieldDecl fdecl = VSTUtil.vstRepOf(cf.ref.memberDecl);
            if (fdecl.isValue) ERROR.CannotAssignToMember(a);
        }
        if (binding instanceof VSTBinding.ComponentMethod) ERROR.CannotAssignToMember(a);
        if (binding instanceof VSTBinding.ClassMethod) ERROR.CannotAssignToMember(a);
        if (binding instanceof VSTBinding.ArrayLength) ERROR.CannotAssignToMember(a);
    }

    public void visit(VSTVarUse u, Type outer) {
        resolveVar(u, outer);
        if ( u.binding == null ) {
            // is it a totally unknown thing, or a non-expression thing?
            String name = u.getName();
            if ( resolveGlobalDecl(name) == null )  ERROR.UnresolvedIdentifier(u);
            else ERROR.ExpectedExprType(u);
        }
        u.setType(u.binding.getType());
    }

    private Decl resolveGlobalDecl(String name) {
        return program.namespace.getDecl(name);
    }

    public void visit(VSTTypeQueryExpr e, Type outer) {
        Type etype = inferClosedType(e.expr);

        if (isClass(etype) || isNull(etype)) {
            // if the expression type is an object (or null), this an "instanceof" test
            VirgilClass.IType qtc = resolveClassType(e.type);
            if ( !couldBeSubtypes(etype, qtc) )
                ERROR.InvalidTypeQuery(e, quote(qtc)+" is not a subclass of "+quote(etype));
        } else {
            // Virgil does not support type queries on non-object types
            ERROR.InvalidTypeQuery(e, "object expression expected");
        }
    }

    private boolean couldBeSubtypes(Type et, VirgilClass.IType ttype) {
        if ( isNull(et) ) return true;
        if ( !isClass(et) ) return false;
        LeastUpperBound lub = leastUpperBound((VirgilClass.IType)et, ttype);
        return lub != null && couldBeEqual(lub.left, lub.right);
    }

    private boolean couldBeEqual(Type a, Type b) {
        if ( a == b ) return true;
        if ( isTypeParam(a) || isTypeParam(b) ) return true;

        Type[] na = a.elements();
        Type[] nb = b.elements();
        if (na.length == 0 || na.length != nb.length) return false;

        if ( isFunction(a) != isFunction(b) ) return false;
        if ( isArray(a) != isArray(b) ) return false;
        if ( isClass(a) ) {
            if (!isClass(b) ) return false;
            if (VirgilClass.declOf(a) != VirgilClass.declOf(b) ) return false;
        }
        for ( int cntr = 0; cntr < na.length; cntr++ )
            if ( !couldBeEqual(na[cntr], nb[cntr]) ) return false;
        return true;
    }

    public void visit(VSTTypeCastExpr e, Type outer) {
        Type etype = inferClosedType(e.expr);
        Type rtype;

        if (isClass(etype) || isNull(etype)) {
            // if the expression type is an object, this a dynamic object cast
            VirgilClass.IType qtc = resolveClassType(e.type);
            if ( !couldBeSubtypes(etype, qtc) )
                ERROR.InvalidTypeCast(e, quote(qtc)+" is not a subclass of "+quote(etype));
            rtype = qtc;
        } else {
            // this is a type conversion, not a type cast
            Type dtype = resolveType(e.type);
            if ( !typeSystem.isPromotable(etype, dtype) && !typeSystem.isConvertible(etype, dtype) )
                ERROR.InvalidTypeCast(e, "cannot convert "+quote(etype)+" to "+quote(dtype));
            rtype = dtype;
        }
        // the result type is the type specified in the cast
        e.setType(rtype);
    }

    public void visit(VSTTupleExpr t, Type outer) {
        List<VSTExpr> te = t.exprs;
        if (te.size() == 0) {
            // an empty () tuple expression
            t.setType(PrimVoid.TYPE);
        } else if (te.size() == 1) {
            // a simple (expr) expression
            te.get(0).accept(this, outer);
            t.setType(te.get(0).getType());
        } else {
            // a compound tuple expression (e1, e2...)
            Type[] et = new Type[te.size()];
            if (outer instanceof Tuple.IType) {
                // if the outer is a tuple, use its type information for inference
                Tuple.IType ttype = (Tuple.IType)outer;
                Type[] at = ttype.getElementTypes();
                for (int i = 0; i < te.size(); i++) {
                    et[i] = inferType(te.get(i), at[i], CLOSED);
                }
            } else {
                // either there is no outer type, or it is not a tuple (type mismatch)
                for (int i = 0; i < te.size(); i++) {
                    et[i] = inferType(te.get(i), null, CLOSED);
                }
            }
            t.setType(Tuple.TYPECON.newType(program.typeCache, et));
        }
    }

    public void visit(VSTIntLiteral i, Type outer) {
        if (!i.value.exists()) ERROR.InvalidLiteral(i);
    }

    public void visit(VSTRawLiteral i, Type outer) {
        if (!i.value.exists()) ERROR.InvalidLiteral(i);
    }

    public void visit(VSTIndexExpr e, Type outer) {
        Type type = inferClosedType(e.array);
        if ( type instanceof VirgilArray.IType ) {
            // array type: element access
            unifyAndTypeCheck(msg("index expression", null), e.index, PrimInt32.TYPE);
            VirgilArray.IType arraytype = (VirgilArray.IType) type;
            e.setType(arraytype.getElemType());
        } else if ( type instanceof PrimRaw.IType) {
            // raw type: bit access
            unifyAndTypeCheck(msg("index expression", null), e.index, PrimInt32.TYPE);
            e.setType(PrimRaw.getType(1));
        } else {
            // invalid type for indexing operation.
            ERROR.ExpectedIndexable(e.array, type);
        }
    }

    public void visit(VSTAppExpr a, Type outer) {
        // infer the open type of the function
        Type t = inferType(a.func, null, OPEN);
        if (!(t instanceof Callable)) ERROR.ExpectedFunction(a.func, t);
        Callable callableType = (Callable) t;

        // check the argument count to the function call first
        checkArity(callableType, a);

        // infer argument types and solve the constraints for the type variables
        Callable cfunc = solveConstraints(a, callableType, inferArgTypes(a), outer);

        // type check the arguments
        typeCheckArguments(a, cfunc);

        // set the return type to the new (solved) return type
        a.setType(cfunc.getResultType());
    }

    private void typeCheckArguments(VSTAppExpr a, Callable cfunc) {
        Type[] argTypes = Tuple.getParameterTypes(cfunc);

        int cntr = 0;
        for ( VSTExpr arg : a.args.exprs ) {
            typeCheckExpr("invocation", arg, arg.getType(), argTypes[cntr++]);
        }
    }

    private Callable solveConstraints(VSTAppExpr a, Callable polyFunc, Type[] argTypes, Type outer) {
        Type[] polyTypes = Tuple.getParameterTypes(polyFunc);
        for ( int cntr = 0; cntr < polyTypes.length; cntr++ ) {
            // collect the constraints for each parameter
            tryUnify(polyTypes[cntr], argTypes[cntr]);
        }
        Type returnType = tryUnify(polyFunc.getResultType(), outer);
        Type rt = polyFunc.rebuild(new Type[] { Tuple.toTuple(program.typeCache, polyTypes), returnType });
        return (Callable)close(rt);
    }

    private Type[] inferArgTypes(VSTAppExpr a) {
        Type[] monoTypes = new Type[a.args.exprs.size()];
        int cntr = 0;
        for ( VSTExpr arg : a.args.exprs) {
            monoTypes[cntr++] = inferClosedType(arg);
        }
        return monoTypes;
    }

    public void visit(VSTSuperClause sc, Type outer) {
        if (sc.target == null) throw Util.failure("unbound super call");

        Type[] atypes = Tuple.getParameterTypes((Function.IType)sc.target.memberType);

        if (sc.args.exprs.size() != atypes.length)
            ERROR.ArgumentCountMismatch(sc, atypes.length, sc.args.exprs.size());

        int cntr = 0;
        for (VSTExpr arg : sc.args.exprs) {
            inferAndTypeCheck(msg("super constructor invocation", null), arg, atypes[cntr]);
            cntr++;
        }

    }

    public void visit(VSTNewArrayExpr nae, Type outer) {
        Type type = resolveType(nae.type);
        for (VSTExpr e : nae.list) {
            unifyAndTypeCheck(msg("array dimension", null), e, PrimInt32.TYPE);
            type = getArrayType(type);
        }
        nae.setType(type);
    }

    public void visit(VSTNewObjectExpr ne, Type outer) {
        Type type = resolveType(ne.type);
        if (isClass(type)) {
            VirgilClass.IType tc = (VirgilClass.IType) type;
            Member.Ref mr = tc.getConstructor(program.typeCache);
            Type[] atypes = Type.NOTYPES;
            if (mr != null) atypes = Tuple.getParameterTypes((Function.IType)mr.memberType);

            if (ne.args.exprs.size() != atypes.length)
                ERROR.ArgumentCountMismatchInNew(ne, atypes.length, ne.args.exprs.size());

            int cntr = 0;
            for (VSTExpr arg : ne.args.exprs) {
                inferAndTypeCheck(msg("constructor invocation", null), arg, atypes[cntr]);
                cntr++;
            }

            ne.setType(tc);
        } else if (isArray(type)) {
            VirgilArray.IType at = (VirgilArray.IType) type;
            if (ne.args.exprs.size() != 1)
                ERROR.ArgumentCountMismatchInNew(ne, 1, ne.args.exprs.size());
            inferAndTypeCheck(msg("array allocation", null), ne.args.exprs.get(0), PrimInt32.TYPE);
            ne.setType(at);
        } else {
            ERROR.ExpectedObjectType(ne.type);
        }
    }

    public void visit(VSTMemberExpr fe, Type outer) {
        if (fe.expr instanceof VSTVarUse) {
            // the expression matches var.member => could mean component.member
            resolveVarMember((VSTVarUse)fe.expr, fe, outer);
        } else {
            // the expression is (e).member => infer type and check
            Type type = inferClosedType(fe.expr);
            resolveExprMember(fe.expr, type, fe, outer);
        }
    }


    public void visit(VSTReturnStmt rs, Type outer) {
        if (rs.expr != null) {
            Type r = inferType(rs.expr, thisReturnType, CLOSED);
            typeCheckExpr(msg("return statement", null), rs.expr, r, thisReturnType);
        }
    }

    public void visit(VSTStringLiteral l, Type outer) {
        l.setType(getArrayType(PrimChar.TYPE));
    }

    private VirgilArray.IType getArrayType(Type elemType) {
        return VirgilArray.getArrayType(program.typeCache, elemType);
    }

    public void visit(VSTIfStmt s, Type outer) {
        unifyAndTypeCheck(msg("if statement", null), s.cond, PrimBool.TYPE);
        visitIfNonNull(s.trueStmt, null);
        visitIfNonNull(s.falseStmt, null);
    }

    public void visit(VSTSwitchStmt s, Type outer) {
        Type valuetype = inferClosedType(s.value);
        if (!isSwitchable(valuetype)) ERROR.NonSwitchableType(s.value, valuetype);

        Map<String, VSTExpr> cases = Ovid.newMap();

        for (VSTSwitchCase c : s.cases) {
            for (VSTExpr val : c.list) {
                String cval = evaluateCaseValue(val, valuetype);
                VSTExpr prev = cases.get(cval);
                if (prev != null) {
                    ERROR.DuplicateCase(val, prev);
                }
                cases.put(cval, val);
            }

            visitIfNonNull(c.stmt, null);
        }

        // visit the default case (if there is one)
        if (s.defcase != null) visitIfNonNull(s.defcase.stmt, null);
    }

    private String evaluateCaseValue(VSTExpr e, Type valuetype) {
        inferAndTypeCheck(msg("switch case", null), e, valuetype);
        if (!e.isComputable()) ERROR.NotComputable(e);
        try {
            return e.computeConstValue().toString();
        } catch (Operator.Exception ex) {
            ERROR.UnexpectedException(e, ex);
            return "";
        }
    }

    public void visit(VSTWhileStmt s, Type outer) {
        unifyAndTypeCheck(msg("while statement", null), s.cond, PrimBool.TYPE);
        visitIfNonNull(s.body, null);
    }

    public void visit(VSTForStmt s, Type outer) {
        if (s.init != null) visitExprs(s.init, null);
        if (s.cond != null) unifyAndTypeCheck(msg("for statement", null), s.cond, PrimBool.TYPE);
        if (s.update != null) visitExprs(s.update, null);
        s.body.accept(this, null);
    }

    public void visit(VSTTernaryExpr e, Type outer) {
        unifyAndTypeCheck(msg("ternary expression", null), e.cond, PrimBool.TYPE);
        Type tt = inferClosedType(e.trueExpr);
        Type ft = inferClosedType(e.falseExpr);
        try {
            e.setType(typeSystem.unify(program.typeCache, tt, ft));
        } catch (UnificationError unificationError) {
            ERROR.CannotUnifyBranchTypes(e, tt, ft);
        }
    }

    public void visit(VSTBinOp b, Type outer) {

        Capability.BinOp binop = typeSystem.resolveBinOp(b.getOp(), inferClosedType(b.left), inferClosedType(b.right));

        if (binop == null) ERROR.UnresolvedBinOp(b);

        checkOperand(b.left, binop, binop.binop.type1);
        checkOperand(b.right, binop, binop.binop.type2);

        b.setBinOp(binop);
        b.setType(binop.binop.getResultType());
    }

    private void checkOperand(VSTExpr e, Capability.BinOp op, Type xlt) {
        typeCheckExpr(msg(op), e, e.getType(), xlt);
    }

    public void visit(VSTPreOp p, Type outer) {
        inferClosedType(p.expr);
        VSTExpr expr = checkLvalue(p.expr);
        Capability.AutoOp at = resolveAutoOp(p, p.getToken().toString(), expr);
        p.setType(at.binop.getResultType());
        p.setAutoOp(at);
    }

    public void visit(VSTPostOp p, Type outer) {
        inferClosedType(p.expr);
        VSTExpr expr = checkLvalue(p.expr);
        Capability.AutoOp at = resolveAutoOp(p, p.getToken().toString(), expr);
        p.setType(at.binop.getResultType());
        p.setAutoOp(at);
    }

    private Capability.AutoOp resolveAutoOp(VSTExpr p, String op, VSTExpr expr) {
        Capability.AutoOp at = typeSystem.resolveAutOp(op, inferClosedType(expr));
        if ( at == null ) ERROR.UnresolvedOperator(p, op+" on type "+ StringUtil.quote(expr.getType()));
        return at;
    }

    public void visit(VSTComparison c, Type outer) {
        Type lt = inferClosedType(c.left);
        Type rt = inferClosedType(c.right);
        Type ct = typeSystem.unifyCompare(lt, rt);
        if ( ct == null ) ERROR.CannotCompareValues(c, lt, rt);
        typeCheckExpr("comparison", c.left, lt, ct);  // should never fail
        typeCheckExpr("comparison", c.right, rt, ct); // should never fail
    }

    public void visit(VSTUnaryOp u, Type outer) {
        Type t = inferClosedType(u.expr);
        Capability.UnaryOp uo = typeSystem.resolveUnOp(u.getToken().toString(), t);

        if (uo == null) ERROR.UnresolvedUnaryOp(u, t);

        typeCheckExpr(msg(uo), u.expr, u.expr.getType(), uo.unop.type1);

        u.setUnaryOp(uo);
        u.setType(uo.unop.getResultType());
    }

    public void visit(VSTThisLiteral t, Type outer) {
        if (thisClass == null) ERROR.ThisMustBeInClass(t);
        t.setType(thisClass);
    }

    protected void resolveExprMember(VSTExpr expr, Type type, VSTMemberExpr fe, Type outer) {
        VSTBinding binding = null;
        String name = fe.getMemberName();
        if (isClass(type)) {
            binding = resolveObjectMember((VirgilClass.IType)type, name, expr);
        } else if (isArray(type)) {
            binding = resolveArrayMember(name, expr);
        } else if (isTuple(type)) {
            binding = resolveTupleElement(expr, type, name);
        }
        if ( binding == null )
            ERROR.UnresolvedMember(fe, type);
        fe.binding = binding;
        fe.setType(binding.getType());
    }

    private VSTBinding resolveTupleElement(VSTExpr expr, Type type, String name) {
        VSTBinding binding = null;
        Tuple.IType ttype = (Tuple.IType)type;
        try {
            int pos = Integer.parseInt(name);
            if (pos < ttype.elements().length) {
                binding = new VSTBinding.TupleElement(expr, ttype, pos);
            }
        } catch (NumberFormatException e) {
            // do nothing.
        }
        return binding;
    }

    private VSTBinding resolveArrayMember(String name, VSTExpr expr) {
        if ("length".equals(name)) {
            return new VSTBinding.ArrayLength(expr);
        }
        return null;
    }

    private VSTBinding resolveObjectMember(VirgilClass.IType ct, String name, VSTExpr expr) {
        Member.Ref mr = VSTUtil.getVisibleMember(program.typeCache, ct, name, container);
        if (mr != null) {
            if ( mr.memberDecl instanceof Method ) {
                return addTypeVars(expr.getToken(), new VSTBinding.ClassMethod(expr, mr));
            }
            else return new VSTBinding.ClassField(expr, mr);
        }
        return null;
    }

    private VSTBinding resolveCompMember(AbstractToken tok, VirgilComponent comp, String name, Type outer) {
        Member.Ref mr = null;
        Member pm = comp.resolveMember(name, program);
        if (pm != null) {
            CompoundDecl cd = pm.getCompoundDecl();
            if (pm.isPrivate() && cd != container) return null;
            mr = new Member.Ref<Member>(comp.getCanonicalType(), pm, pm.getType());
        }

        if ( mr != null ) {
            if ( mr.memberDecl instanceof Method ) {
                return addTypeVars(tok, new VSTBinding.ComponentMethod(comp, mr));
            }
            else return new VSTBinding.ComponentField(comp, mr);
        }
        return null;
    }

    private VSTBinding resolveImplicitMember(AbstractToken tok, String name, Type outer) {
        if ( container instanceof VirgilClass ) {
            // try to resolve a field use with implicit "this"
            VSTThisLiteral expr = new VSTThisLiteral(tok);
            expr.setType(thisClass);
            return resolveObjectMember(thisClass, name, expr);
        } else {
            // try to resolve an implicit component field use
            return resolveCompMember(tok, (VirgilComponent) container, name, outer);
        }
    }

    private VSTBinding addTypeVars(AbstractToken tok, VSTBinding.MethodUse binding) {
        TypeParam[] tpl = binding.ref.memberDecl.getTypeParams();
        if (tpl.length > 0) {
            // type parameters => create list of type variables
            TypeVar[] result = new TypeVar[tpl.length];
            for ( int cntr = 0; cntr < tpl.length; cntr++ ) {
                result[cntr] = new TypeVar(tok.getSourcePoint(), tpl[cntr]);
            }
            binding.typeVars = result;
            binding.setVarType(TypeParam.substitute(program.typeCache, tpl, result, binding.ref.memberType));
        } else {
            // no type parameters to the method => no type variables
            binding.typeVars = NOTYPEVARS;
        }
        return binding;
    }

    private VSTBinding resolveDeviceMember(vpc.hil.Device dev, String name) {
        Device.Register r = dev.registers.get(name);
        if (r != null) {
            return new VSTBinding.DeviceRegister(dev, r);
        }
        Device.Instruction i = dev.instructions.get(name);
        if (i != null) {
            Function.IType ft = Function.TYPECON.newType(program.typeCache, PrimVoid.TYPE, PrimVoid.TYPE);
            return new VSTBinding.DeviceInstruction(dev, ft, i);
        }
        return null;
    }

    protected void resolveVarMember(VSTVarUse u, VSTMemberExpr fe, Type outer) {
        resolveVar(u, outer);
        if ( u.binding != null ) {
            resolveExprMember(fe.expr, u.binding.getType(), fe, outer);
        } else {
            Decl d = resolveGlobalDecl(u.getName());
            VSTBinding binding = null;
            if (d instanceof VirgilComponent) {
                // if the resolved declaration is a component, lookup the member
                VirgilComponent cd = (VirgilComponent) d;
                binding = resolveCompMember(fe.getToken(), cd, fe.getMemberName(), outer);
            } else if (d instanceof vpc.hil.Device) {
                // if the resolved declaration is a device, this is a register access
                vpc.hil.Device dev = (vpc.hil.Device) d;
                binding = resolveDeviceMember(dev, fe.getMemberName());
            } else {
                // don't know what the declaration is.
                ERROR.UnresolvedIdentifier(u);
            }
            if ( binding == null ) ERROR.UnresolvedMember(fe, d);
            fe.binding = binding;
            fe.setType(binding.getType());
        }
    }

    private void resolveVar(VSTVarUse u, Type outer) {
        if (u.binding == null) {
            u.binding = resolveImplicitMember(u.getToken(), u.getName(), outer);
        }
        if (u.binding != null) {
            u.setType(u.binding.getType());
        }
    }

    protected Type resolveType(VSTTypeRef tref) {
        return VSTTypeBuilder.resolveType(tref, program.typeCache, typeEnv, ERROR);
    }

    protected VirgilClass.IType resolveClassType(VSTTypeRef tref) {
        Type type = resolveType(tref);
        if (!isClass(type)) ERROR.ExpectedObjectType(tref);
        return (VirgilClass.IType)type;
    }

    protected Type resolveVarType(VSTTypeRef tref) {
        Type t = resolveType(tref);
        if (isVoid(t) || isComponent(t) || isNull(t))
            ERROR.ExpectedVarType(tref);
        return t;
    }

    protected VirgilClass prRepOf(VirgilClass.IType c) {
        return c.getTypeCon().getDecl();
    }

    private void checkArity(Callable f, VSTAppExpr a) {
        Type[] argTypes = Tuple.getParameterTypes(f);
        if (a.args.exprs.size() != argTypes.length) {
            // check the type param arity
            ERROR.ArgumentCountMismatch(a, argTypes.length, a.args.exprs.size());
        }
    }

    protected Type inferClosedType(VSTExpr e) {
        return inferType(e, null, CLOSED);
    }

    private Type inferType(VSTExpr e, Type outer, boolean closed) {
        // infer the type
        e.accept(this, outer);

        // get the result type
        Type type = e.getType();

        // try to unify with the outer type (don't report errors)
        if ( outer != null ) tryUnify(type, outer);

        // attempt to close the type if necessary
        if ( closed ) type = close(type);

        // return the type
        e.setType(type);
        return type;
    }

    private Type tryUnify(Type t1, Type t2) {
        if ( t2 == null ) return t1;
        try {
            return typeSystem.unify(program.typeCache, t1, t2);
        } catch (UnificationError unificationError) {
            return t1;
        }
    }

    private Type close(Type t) {
        if ( isTypeVar(t) ) {
            TypeVar tv = (TypeVar)t;
            if ( tv.binding == null ) ERROR.CannotInferTypeParam(tv);
            return tv.binding;
        }
        Type[] e = t.elements();
        if (e.length > 0) {
            // close the nested types
            Type[] n = new Type[e.length];
            for ( int cntr = 0; cntr < e.length; cntr++ ) {
                n[cntr] = close(e[cntr]);
            }
            return program.typeCache.getCached(t.rebuild(n));
        }
        return t;
    }

    private String msg(String op, Object o) {
        return o == null ? op : op + " " + quote(o);
    }

    private String msg(Capability.BinOp op) {
        return "operator " + op.opname +"("+op.binop.type1+", "+op.binop.type2+")";
    }

    private String msg(Capability.UnaryOp uo) {
        return "operator " + uo.opname +"("+uo.unop.type1+")";
    }

    private boolean isSwitchable(Type valuetype) {
        return valuetype == PrimInt32.TYPE || valuetype == PrimChar.TYPE || valuetype instanceof PrimRaw.IType;
    }
}
