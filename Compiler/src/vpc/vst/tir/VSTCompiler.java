/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 26, 2006
 */

package vpc.vst.tir;

import cck.parser.AbstractToken;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.Program;
import vpc.core.Value;
import vpc.core.base.*;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.core.types.TypeFormula;
import vpc.core.virgil.*;
import static vpc.core.virgil.V2TypeSystem.*;
import vpc.tir.*;
import static vpc.tir.TIRUtil.*;
import vpc.tir.expr.Operator;
import vpc.util.ArrayUtil;
import static vpc.vst.VSTUtil.typeOf;
import vpc.vst.tree.*;
import vpc.vst.visitor.VSTAccumulator;

import java.util.List;
import java.util.Stack;

/**
 * The <code>VSTCompiler</code> class implements a visitor that is able
 * to build blocks of IR code from blocks of abstract syntax trees.
 */
public class VSTCompiler implements VSTAccumulator<TIRExpr>, VSTBinding.Visitor<TIRExpr> {

    private BuilderEnv builderEnv;
    private Stack<LoopContext> loopContexts = new Stack<LoopContext>();

    public static class LoopContext {
        boolean forLoop;
        TIRBlock outerBlock;
        TIRBlock innerBlock;

        LoopContext(boolean f, TIRBlock b, TIRBlock c) {
            forLoop = f;
            outerBlock = b;
            innerBlock = c;
        }

        TIRExpr buildBreak(VSTBreakStmt s) {
            // in both for's and other loops, we simply break out of the block
            return new TIRBlock.Break(outerBlock);
        }

        TIRExpr buildContinue(VSTContinueStmt s) {
            if (forLoop) {
                return new TIRBlock.Break(innerBlock);
            }
            return new TIRBlock.Continue(outerBlock);
        }
    }

    public VSTCompiler(BuilderEnv builderEnv) {
        this.builderEnv = builderEnv;
    }

    public TIRExpr buildExpr(VSTExpr e) {
        TIRExpr tirExpr = setTypeAndSourcePoint(e, typeOf(e), e.accept(this));
        return promote(e, tirExpr);
    }

    public TIRExpr buildStmtBlock(List<VSTStmt> l) {
        if (l == null) return newNoOp(null);
        TIRBlock block = builderEnv.newBlock("L_");
        for (VSTStmt stmt : l) {
            TIRExpr nstmt = buildStmt(stmt);
            if (!isNoOp(nstmt)) block.addExpr(nstmt);
        }
        return block;
    }

    private TIRExpr promote(VSTExpr e, TIRExpr ne) {
        Type pt = e.getPromotionType();
        if ( pt != null ) {
            Type ot = typeOf(e);
            if ( ot == pt ) return ne;
            Operator op = null;
            if ( isInt(ot) ) {
                if ( isRaw(pt) ) op = new PrimConversion.Int32_Raw((PrimRaw.IType)pt);
            } else if ( isChar(ot) ) {
                if ( isInt(pt) ) op = new PrimConversion.Char_Int32();
                if ( isRaw(pt) ) op = new PrimConversion.Char_Raw((PrimRaw.IType)pt);
            } else if ( isRaw(ot) && isRaw(pt) ) {
                op = new PrimConversion.AdjustRaw((PrimRaw.IType)ot, (PrimRaw.IType)pt);
            }

            if ( op == null)
                throw fail("Invalid promotion from "+ot+" to "+pt, ne);

            return setTypeAndSourcePoint(e, pt, $OP(op, ne));
        }
        return ne;
    }

    private TIRExpr buildStmt(VSTStmt s) {
        if (s == null) {
            return newNoOp(null);
        } else {
            return setTypeAndSourcePoint(s, PrimVoid.TYPE, s.accept(this));
        }
    }

    private TIRExpr buildExprBlock(List<VSTExpr> l) {
        if (l == null) return newNoOp(null);
        TIRBlock block = builderEnv.newBlock("E_");
        for (VSTExpr expr : l) {
            TIRExpr nexpr = buildExpr(expr);
            if (!isNoOp(nexpr)) block.addExpr(nexpr);
        }
        return block;
    }

    private TIRExpr buildLoopBody(VSTStmt bod, LoopContext loopContext) {
        loopContexts.push(loopContext);
        TIRExpr body = buildStmt(bod);
        loopContexts.pop();
        return body;
    }

    private TIRExpr[] buildExprList(List<VSTExpr> l) {
        TIRExpr[] res = new TIRExpr[l.size()];
        int cntr = 0;
        for (VSTExpr e : l) {
            res[cntr++] = buildExpr(e);
        }
        return res;
    }

    private Method.Temporary getTemporary(VSTVarDecl var) {
        return builderEnv.varMap.get(var);
    }

    private TIRLocal.Get liftTemp(TIRExpr e, TIRBlock block) {
        return lift(builderEnv.method, null, e, block);
    }

    private TIRExpr liftExpr(TIRExpr e, TIRBlock block) {
        if (e instanceof TIRLocal.Get || e instanceof TIRConst ) return e;
        else return lift(builderEnv.method, null, e, block);
    }

    private TIRExpr liftLvalue(TIRExpr e, TIRBlock block) {
        if (e instanceof TIRLocal.Get ) return e;
        if (e instanceof TIROperator ) {
            TIROperator to = (TIROperator)e;
            TIRExpr[] nl = new TIRExpr[to.operands.length];
            if ( to.operator instanceof PrimRaw.GetBit ) {
                // in the case of a get bit, we need to recursively lift the lvalue
                nl[0] = liftLvalue(to.operands[0], block);
                nl[1] = liftExpr(to.operands[1], block);
            } else {
                // otherwise, we can just lift all expressions to temporaries
                for ( int cntr = 0; cntr < to.operands.length; cntr++ ) {
                    nl[cntr] = liftExpr(to.operands[cntr], block);
                }
            }
            TIROperator tirOp = $OP(to.operator, nl);
            tirOp.setSourcePoint(e.getSourcePoint());
            return tirOp;
        }
        throw TIRUtil.fail("cannot lift location", e);
    }

    public TIRExpr buildBinOp(VSTNode w, Operator oper, TIRExpr le, TIRExpr re) {
        AbstractToken tok = w.getToken();
        if (oper != null) {
            TIRExpr e;
            Class clz = oper.getClass();
            if (clz == PrimBool.OR.class) e = $OR(le, re);
            else if (clz == PrimBool.AND.class) e = $AND(le, re);
            else e = $OP(oper, le, re);

            return setSourcePoint(tok, e);
        }

        throw TIRBuilder.fail("unknown binary op " + StringUtil.quote(tok.image), w);
    }

    public TIRExpr visit(VSTReturnStmt r) {
        if (r.expr == null) {
            // translate a void return into returning a no-op
            return new TIRReturn($NOOP());
        } else {
            // translate a return with a value
            return new TIRReturn(buildExpr(r.expr));
        }
    }

    public TIRExpr visit(VSTEmptyStmt s) {
        return newNoOp(s.getToken());
    }

    public TIRExpr visit(VSTBlock b) {
        return buildStmtBlock(b.stmts); // flatten into this block
    }

    public TIRExpr visit(VSTSuperClause s) {
        TIRExpr[] args = buildExprList(s.args.exprs);
        TIRExpr tref = builderEnv.newThisRef();
        Operator op = new VirgilClass.GetMethod((VirgilClass.IType)tref.getType(), s.target.memberDecl, false, TypeFormula.NOTYPES);
        return new TIRCall(true, setSourcePoint(s, $OP(op, tref)), args);
    }

    public TIRExpr visit(VSTIfStmt s) {
        return $IF(buildExpr(s.cond), buildStmt(s.trueStmt), buildStmt(s.falseStmt));
    }

    public TIRExpr visit(VSTWhileStmt s) {
        TIRBlock loop = builderEnv.newBlock("while");
        TIRExpr body = buildLoopBody(s.body, new LoopContext(false, loop, loop));
        TIRExpr cond = buildExpr(s.cond);
        TIRBlock tb = builderEnv.newBlock("while_if", body, new TIRBlock.Continue(loop));
        loop.addExpr($IF(cond, tb, $NOOP()));
        return loop;
    }

    public TIRExpr visit(VSTDoWhileStmt s) {
        TIRBlock loop = builderEnv.newBlock("dowhile");
        TIRExpr body = buildLoopBody(s.body, new LoopContext(false, loop, loop));
        TIRExpr cond = buildExpr(s.cond);
        loop.addExpr(body);
        loop.addExpr($IF(cond, new TIRBlock.Continue(loop), $NOOP()));
        return loop;
    }

    public TIRExpr visit(VSTForStmt s) {
        TIRBlock outer = builderEnv.newBlock("for_out");
        TIRBlock inner = builderEnv.newBlock("for_in");
        TIRExpr init = buildExprBlock(s.init);
        TIRExpr cond = s.cond == null ? $VAL(true) : buildExpr(s.cond);
        TIRExpr body = buildLoopBody(s.body, new LoopContext(true, outer, inner));
        TIRExpr upd = buildExprBlock(s.update);
        TIRBlock loop = builderEnv.newBlock("for");
        loop.addExpr(init);
        inner.addExpr($IF(cond, body, new TIRBlock.Break(outer)));
        outer.addExpr(inner);
        outer.addExpr(upd);
        outer.addExpr(new TIRBlock.Continue(outer));
        loop.addExpr(outer);
        return loop;
    }

    public TIRExpr visit(VSTContinueStmt s) {
        return loopContexts.peek().buildContinue(s);
    }

    public TIRExpr visit(VSTBreakStmt s) {
        return loopContexts.peek().buildBreak(s);
    }

    public TIRExpr visit(VSTExprStmt s) {
        return buildExpr(s.expr);
    }

    public TIRExpr visit(VSTSwitchStmt s) {
        TIRExpr e = buildExpr(s.value);
        TIRSwitch.Case[] cs = new TIRSwitch.Case[s.cases.size()];
        int cntr = 0;
        for (VSTSwitchCase c : s.cases) {
            TIRConst.Value[] vals = null;
            if (c.list != null) vals = buildConsts(c.list);
            cs[cntr] = new TIRSwitch.Case(vals, buildStmt(c.stmt));
            cntr++;
        }
        TIRSwitch.Case dc = null;
        if (s.defcase != null) // build the default case
            dc = new TIRSwitch.Case(null, buildStmt(s.defcase.stmt));
        return new TIRSwitch(e, cs, dc);
    }

    private TIRConst.Value[] buildConsts(List<VSTExpr> l) {
        TIRConst.Value[] ret = new TIRConst.Value[l.size()];
        int cntr = 0;
        for (VSTExpr expr : l) {
            TIRExpr e = buildExpr(expr);
            if (e instanceof TIRConst.Value) ret[cntr] = (TIRConst.Value) e;
            else throw TIRUtil.fail("value required", e);
            cntr++;
        }
        return ret;
    }

    public TIRExpr visit(VSTLocalVarDecl d) {
        Method.Temporary t = builderEnv.newVariable(d);
        if (d.init == null) return newNoOp(d.getToken());
        else return new TIRLocal.Set(t, buildExpr(d.init));
    }

    public TIRExpr visit(VSTSwitchCase c) {
        throw TIRBuilder.fail("should not visit cases directly", c);
    }

    public TIRExpr visit(VSTArrayInitializer e) {
        // build an expression representing the element initializers
        TIRExpr[] elems = buildExprList(e.list);
        VirgilArray.IType at = (VirgilArray.IType) typeOf(e);
        VirgilArray.Alloc allocop = opArrayAlloc(at, 1);
        TIRConst.Value len = $VAL(elems.length);
        setSourcePoint(e, len);
        TIRExpr op = setSourcePoint(e, $OP(allocop, len));
        return $OP(new VirgilArray.Init(at, elems.length), ArrayUtil.prepend(op, elems));
    }

    public TIRExpr visit(VSTAssign e) {
        TIRExpr l = buildExpr(e.target);
        TIRExpr v = buildExpr(e.value);
        return convertAssign(e.getToken(), l, v);
    }

    public TIRExpr convertAssign(AbstractToken assign, TIRExpr l, TIRExpr v) {
        if ( l instanceof TIRLocal.Get ) {
            return setSourcePoint(assign, new TIRLocal.Set(((TIRLocal.Get) l).temp, v));
        } else if ( l instanceof TIROperator ) {
            return convertGet(assign, (TIROperator)l, v);
        }
        throw TIRUtil.fail("not an lvalue", l);
    }

    public TIRExpr visit(VSTBinOp e) {
        TIRExpr le = buildExpr(e.left);
        TIRExpr re = buildExpr(e.right);

        return buildBinOp(e, e.binop.binop, le, re);
    }

    public TIRExpr visit(VSTComparison c) {
        TIRExpr l = buildExpr(c.left);
        TIRExpr r = buildExpr(c.right);
        try {
            Program program = builderEnv.tirBuilder.program;
            Type t = program.typeSystem.unify(program.typeCache, l.getType(), r.getType());
            Operator op = c.isEqOp() ? new Value.Equal(t) : new Value.NotEqual(t);
            return new TIROperator(op, buildExpr(c.left), buildExpr(c.right));
        } catch (UnificationError ue) {
            throw Util.unexpected(ue);
        }
    }

    public TIRExpr visit(VSTIndexExpr e) {
        Type it = typeOf(e.array);
        TIRExpr ne = buildExpr(e.array);
        TIRExpr i = buildExpr(e.index);
        if ( isArray(it) ) {
            Operator op = new VirgilArray.GetElement((VirgilArray.IType)it);
            return $OP(op, nullCheck(ne), i);
        } else if ( isRaw(it) ) {
            Operator op = new PrimRaw.GetBit((PrimRaw.IType)it);
            return $OP(op, ne, i);
        }
        throw TIRUtil.fail("invalid index expression of type "+it, ne);
    }

    public TIRExpr visit(VSTTypeQueryExpr e) {
        TIRExpr expr = buildExpr(e.expr);
        VirgilClass.IType stype = (VirgilClass.IType)expr.getType();
        VirgilClass.IType target = (VirgilClass.IType)typeOf(e.type);
        return $OP(new VirgilClass.TypeQuery(stype, target), expr);
    }

    public TIRExpr visit(VSTTypeCastExpr e) {
        TIRExpr ne = buildExpr(e.expr);
        Type ot = ne.getType();
        Type ct = typeOf(e.type);

        if (ot == ct) return ne;
        
        Operator op = null;
        if ( isClass(ot) ) {
            op = new VirgilClass.TypeCast((VirgilClass.IType)ot, (VirgilClass.IType)ct);
        } else if ( isInt(ot) ) {
            if ( isRaw(ct) )  op = new PrimConversion.Int32_Raw((PrimRaw.IType)ct);
            if ( isChar(ct) )  op = new PrimConversion.Int32_Char();
        } else if ( isChar(ot) ) {
            if ( isInt(ct) ) op = new PrimConversion.Char_Int32();
            if ( isRaw(ct) ) op = new PrimConversion.Char_Raw((PrimRaw.IType)ct);
        } else if ( isRaw(ot) ) {
            if ( isRaw(ct) )
                op = new PrimConversion.AdjustRaw((PrimRaw.IType)ot, (PrimRaw.IType)ct);
            if ( isChar(ct) )
                op = new PrimConversion.Raw_Char((PrimRaw.IType)ot);
            if ( isInt(ct) )
                op = new PrimConversion.Raw_Int32((PrimRaw.IType)ot);
        }

        if ( op == null)
            throw TIRUtil.fail("Invalid promotion from "+ot+" to "+ct, ne);

        return setTypeAndSourcePoint(e, ct, $OP(op, ne));
    }

    public TIRExpr visit(VSTBoolLiteral e) {
        return $VAL(e.getValue());
    }

    public TIRExpr visit(VSTCharLiteral e) {
        return $VAL(e.getValue());
    }

    public TIRExpr visit(VSTIntLiteral e) {
        return $VAL(e.getValue());
    }

    public TIRExpr visit(VSTRawLiteral e) {
        return $VAL(e.getValue());
    }

    public TIRExpr visit(VSTStringLiteral e) {
        return new TIRConst.Symbol(e.original(), e.value());
    }

    public TIRExpr visit(VSTNullLiteral e) {
        return $NULL();
    }

    public TIRExpr visit(VSTThisLiteral e) {
        return builderEnv.newThisRef();
    }

    public TIRExpr visit(VSTAppExpr e) {
        TIRExpr ref = buildExpr(e.func);
        TIRExpr[] args = buildExprList(e.args.exprs);
        Type ftype = ref.getType();
        if (isArray(ftype)) {
            assert args.length == 1;
            VirgilArray.IType atype = (VirgilArray.IType)ftype;
            return $OP(new VirgilArray.GetElement(atype), nullCheck(ref), args[0]);
        } else {
            return new TIRCall(true, ref, args);
        }
    }

    public TIRExpr visit(VSTNewArrayExpr e) {
        TIRExpr[] dims = buildExprList(e.list);
        VirgilArray.IType at = (VirgilArray.IType)typeOf(e);
        return $OP(opArrayAlloc(at, dims.length), dims);
    }

    public TIRExpr visit(VSTNewObjectExpr e) {
        TIRExpr[] args = buildExprList(e.args.exprs);
        TIRBlock b = builderEnv.newBlock("NEW_");
        setSourcePoint(e.getToken(), b);
        Type type = typeOf(e.type);
        if (isClass(type)) {
            VirgilClass.IType ct = (VirgilClass.IType) type;
            TIRExpr ao = setSourcePoint(e.type, $OP(new VirgilClass.Alloc(ct)));
            TIRExpr obj = lift(builderEnv.method, null, ao, b);
            TIRExpr rc = setSourcePoint(e.type, $OP(new VirgilClass.GetMethod(ct, ct.getDecl().getConstructor(), false, TypeFormula.NOTYPES), obj));
            b.addExpr(setSourcePoint(e.type, new TIRCall(true, rc, args)));
            b.addExpr(obj);
            return b;
        } else if (isArray(type)) {
            VirgilArray.IType at = (VirgilArray.IType) type;
            return $OP(opArrayAlloc(at, 1), args);
        }
        throw Util.failure("unknown new "+e);
    }

    public TIRExpr visit(VSTCompoundAssign e) {
        return readModifyWrite(e.getToken(), false, buildExpr(e.target), e.binop.binop, buildExpr(e.value));
    }

    public TIRExpr visit(VSTTernaryExpr e) {
        return $IF(buildExpr(e.cond), buildExpr(e.trueExpr), buildExpr(e.falseExpr));
    }

    public TIRExpr visit(VSTTupleExpr e) {
        if (e.exprs.size() == 0) return $VAL(PrimVoid.VALUE);
        if (e.exprs.size() == 1) return buildExpr(e.exprs.get(0));
        return $OP(new Tuple.Create((Tuple.IType)e.getType()), buildExprList(e.exprs));
    }

    public TIRExpr visit(VSTUnaryOp e) {
        return $OP(e.unaryop.unop, buildExpr(e.expr));
    }

    public TIRExpr visit(VSTPreOp p) {
        TIRExpr val = buildConstFor(p, p.autoop.value);
        return readModifyWrite(p.getToken(), false, buildExpr(p.expr), p.autoop.binop, val);
    }

    public TIRExpr visit(VSTPostOp p) {
        TIRExpr val = buildConstFor(p, p.autoop.value);
        return readModifyWrite(p.getToken(), true, buildExpr(p.expr), p.autoop.binop, val);
    }

    public TIRExpr visit(VSTMemberExpr e) {
        if (e.binding == null) throw TIRBuilder.fail("unbound MemberExpr", e);
        return e.binding.accept(this);
    }

    public TIRExpr visit(VSTVarUse u) {
        if (u.binding == null) throw TIRBuilder.fail("unbound VarUse", u);
        return u.binding.accept(this);
    }

    private TIRExpr readModifyWrite(AbstractToken loc, boolean before, TIRExpr expr, Operator mod, TIRExpr val) {
        TIRBlock block = builderEnv.newBlock("RMW_");
        setSourcePoint(loc, block);
        expr = liftLvalue(expr, block);
        if ( before ) {
            TIRLocal.Get temp = liftTemp(expr, block);
            TIRExpr op = setSourcePoint(loc, $OP(mod, temp, val));
            block.addExpr(convertAssign(loc, expr, op));
            block.addExpr(temp);
        } else {
            TIRExpr op = setSourcePoint(loc, $OP(mod, expr, val));
            block.addExpr(convertAssign(loc, expr, op));
        }
        return block;
    }

    protected TIRExpr convertGet(AbstractToken assign, TIROperator read, TIRExpr val) {
        if ( read.operator.getClass() == PrimRaw.GetBit.class )
            return convertGetBit(assign, read, val);
        else if ( read.operator instanceof Operator.Location )
            return convertGetLocation(assign, read, val);
        throw Util.failure("unknown operator "+read.operator.getClass());
    }

    private TIRExpr convertGetLocation(AbstractToken assign, TIROperator read, TIRExpr val) {
        Operator setOp = ((Operator.Location)read.operator).setOperator();
        TIRExpr[] writeOps = ArrayUtil.append(read.operands, val);
        return setSourcePoint(assign, $OP(setOp, writeOps));
    }

    private TIRExpr convertGetBit(AbstractToken assign, TIROperator read, TIRExpr val) {
        // TODO: this read-modify-write operation is kind of ugly
        TIRExpr[] ops = read.operands;
        PrimRaw.GetBit f = (PrimRaw.GetBit)read.operator;
        PrimRaw.SetBit mod = new PrimRaw.SetBit((PrimRaw.IType)f.type1);
        TIRBlock block = builderEnv.newBlock("R");
        setSourcePoint(assign, block);
        TIRExpr expr = liftLvalue(ops[0], block);
        TIRExpr bit = liftExpr(ops[1], block);
        TIRExpr nval = liftExpr(val, block);
        TIROperator op = $OP(mod, expr, bit, nval);
        setSourcePoint(assign, op);
        block.addExpr(convertAssign(assign, expr, op));
        block.addExpr(nval);
        return block;
    }

    private TIRExpr setSourcePoint(VSTNode vn, TIRExpr expr) {
        expr.setSourcePoint(vn.getToken().getSourcePoint());
        return expr;
    }

    private TIRExpr setTypeAndSourcePoint(VSTNode vn, Type t, TIRExpr expr) {
        expr.setSourcePoint(vn.getToken().getSourcePoint());
        expr.setType(t);
        return expr;
    }

    private TIRExpr setSourcePoint(AbstractToken t, TIRExpr expr) {
        expr.setSourcePoint(t.getSourcePoint());
        return expr;
    }

    public TIRExpr visit(VSTBinding.Local e) {
        return new TIRLocal.Get(getTemporary(e.decl));
    }

    public TIRExpr visit(VSTBinding.ComponentMethod e) {
        return $OP(new VirgilComponent.GetMethod(e.ref, e.typeVars));
    }

    public TIRExpr visit(VSTBinding.ComponentField e) {
        return $OP(new VirgilComponent.GetField(e.ref.memberDecl));
    }

    public TIRExpr visit(VSTBinding.ClassMethod e) {
        TIRExpr te = nullCheckExpr(e.expr);
        TypeFormula[] tf = TypeFormula.newFormula(e.typeVars);
        boolean virtual = e.ref.memberDecl.family != null;
        return $OP(new VirgilClass.GetMethod(e.ref, virtual, tf), te);
    }

    public TIRExpr visit(VSTBinding.ClassField e) {
        TIRExpr te = nullCheckExpr(e.expr);
        return $OP(new VirgilClass.GetField(e.ref), te);
    }

    public TIRExpr visit(VSTBinding.DeviceRegister e) {
        return $OP(new vpc.hil.Device.GetRegister(e.register));
    }

    public TIRExpr visit(VSTBinding.DeviceInstruction e) {
        return $OP(new vpc.hil.Device.InstrUse(e.instruction, e.getType()));
    }

    public TIRExpr visit(VSTBinding.TupleElement e) {
        return $OP(new Tuple.GetElement(e.tupleType, e.position), buildExpr(e.tupleExpr));
    }

    public TIRExpr visit(VSTBinding.ArrayLength e) {
        TIRExpr ae = nullCheck(buildExpr(e.array));
        return $OP(new VirgilArray.GetLength((VirgilArray.IType)e.array.getType()), ae);
    }

    protected TIRExpr nullCheckExpr(VSTExpr e) {
        TIRExpr ne = buildExpr(e);
        return e instanceof VSTThisLiteral ? ne : nullCheck(ne);
    }

    private VirgilArray.Alloc opArrayAlloc(VirgilArray.IType at, int dims) {
        return new VirgilArray.Alloc(at, dims);
    }

    private TIRExpr newNoOp(AbstractToken t) {
        TIRExpr n = $NOOP();
        if ( t != null ) {
            setSourcePoint(t, n);
        }
        return n;
    }

    private static TIRExpr buildConstFor(VSTNode e, Value v) {
        TIRExpr value = $VAL(v);
        value.setSourcePoint(e.getToken().getSourcePoint());
        return value;
    }
}
