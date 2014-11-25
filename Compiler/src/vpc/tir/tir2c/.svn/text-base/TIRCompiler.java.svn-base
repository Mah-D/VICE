/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Jan 27, 2006
 */
package vpc.tir.tir2c;

import cck.parser.SourcePoint;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.Value;
import vpc.core.base.*;
import vpc.core.csr.*;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.core.virgil.VirgilDelegate;
import vpc.core.virgil.VirgilOp;
import vpc.tir.*;
import vpc.tir.expr.Operator;
import static vpc.tir.expr.Precedence.*;
import vpc.util.Ovid;
import vpc.hil.Device;

import java.util.List;
import java.util.Properties;

/**
 * The <code>TIRCompiler</code> class implements a translation between TIR code
 * and C code. It can compile a TIR representation of a method into a
 * <code>CSRFunction</code>.
 *
 * @author Ben L. Titzer
 */
public class TIRCompiler {

    protected final ExprCompiler EXP;
    protected final StmtCompiler STMT;
    protected final CSRProgram csr;

    protected CSRFunction func;

    public TIRCompiler(CSRProgram p) {
        csr = p;
        EXP = new ExprCompiler();
        STMT = new StmtCompiler();
    }

    protected static String $eval(String pat, Object... objs) {
        return StringUtil.stringReplace(pat, new Properties(), objs);
    }

    protected static CIR.CExpr $expr(int prec, String pat, Object... objs) {
        return new CIR.CExpr(prec, StringUtil.stringReplace(pat, new Properties(), objs));
    }

    protected static CIR.CStmt $stmt(String pat, Object... objs) {
        return new CIR.CSingle(StringUtil.stringReplace(pat, new Properties(), objs));
    }

    /**
     * The <code>compile()</code> method compiles a given C function. It translates
     * the TIR representation of the method to a <code>CIR</code> implementation
     * that can be used to output C code.
     *
     * @param cf the C function to compile.
     */
    public void compile(CSRFunction cf) {
        func = cf;
        TIRRep tr = TIRUtil.getRep(cf.method);
        for (Method.Temporary t : tr.getTemps()) {
            cf.newVariable(t.getName(), csr.encodeType(t.getType()));
        }
        cf.setBody(tr.getBody().accept(STMT, CSRProgram.VOID));
    }

    protected class ExprCompiler extends TIRExprVisitor<CIR.CExpr, CSRType> {

        public CIR.CExpr visit(TIRExpr e, CSRType ct) {
            throw Util.failure("compile(" + e.getClass() + ") unimplemented in TIR->C @ " + e.getSourcePoint());
        }

        public CIR.CExpr visit(TIRBlock s, CSRType ctype) {
            assert s.isStraight();
            if (s.isEmpty()) {
                throw TIRUtil.fail("cannot convert empty block to expression", s);
            } else if (s.isSingleton()) {
                return s.getFirst().accept(EXP, ctype);
            }
            CIR.CExpr ce = new CIR.CExpr(PREC_TERM);
            int cntr = 1, sz = s.list.size();
            for (TIRExpr e : s.list) {
                if (cntr++ == sz) ce.exprs.add(CEX(e, ctype));
                else ce.exprs.add(e.accept(STMT, csr.encodeType(e.getType())).asString());
            }
            return ce;
        }

        public CIR.CExpr visit(TIRIfExpr i, CSRType ctype) {
            return $expr(PREC_IF_EXPR, "($1) ? ($2) : ($3)",
                    CEX(i.condition, CSRProgram.BOOL),
                    CEX(i.true_target, ctype),
                    CEX(i.false_target, ctype));
        }

        public CIR.CExpr visit(TIROperator to, CSRType ctype) {
            Operator op = to.operator;

            switch ( CSRGen.getOpcode(to) ) {
                // check against value comparison operators
                case CSRGen.VALUE_EQU: return compare(true, to.operands[0], to.operands[1]);
                case CSRGen.VALUE_NEQU: return compare(false, to.operands[0], to.operands[1]);
                // check against the known operators for integers
                case CSRGen.INT32_ADD: return binop(to, "+", PREC_ADD);
                case CSRGen.INT32_SUB: return binop(to, "-", PREC_SUBTRACT);
                case CSRGen.INT32_MUL: return binop(to, "*", PREC_MULTIPLY);
                case CSRGen.INT32_DIV: return divide(to, "/", PREC_DIVIDE);
                case CSRGen.INT32_MOD: return divide(to, "%", PREC_MODULUS);
                case CSRGen.INT32_LT: return binop(to, "<", PREC_LESS_THAN);
                case CSRGen.INT32_GR: return binop(to, ">", PREC_GREATER);
                case CSRGen.INT32_LTEQ: return binop(to, "<=", PREC_LESS_EQUAL);
                case CSRGen.INT32_GREQ: return binop(to, ">=", PREC_GREATER_EQUAL);
                case CSRGen.INT32_NEG: return unop(to, "-", PREC_MINUS);

                // check against the raw operators
                case CSRGen.RAW_OR: return binop(to, "|", PREC_BITWISE_OR);
                case CSRGen.RAW_AND: return binop(to, "&", PREC_BITWISE_AND);
                case CSRGen.RAW_XOR: return binop(to, "^", PREC_BITWISE_XOR);
                case CSRGen.RAW_SHR: return binop(to, ">>", PREC_SHIFT_RIGHT);
                case CSRGen.RAW_COMP: return unop(to, "~", PREC_COMP);
                case CSRGen.RAW_SHL: return binop(to, "<<", PREC_SHIFT_LEFT);

                // check against the known operators for characters
                case CSRGen.CHAR_LT: return binop(to, "<", PREC_LESS_THAN);
                case CSRGen.CHAR_GR: return binop(to, ">", PREC_GREATER);
                case CSRGen.CHAR_LTEQ: return binop(to, "<=", PREC_LESS_EQUAL);
                case CSRGen.CHAR_GREQ: return binop(to, ">=", PREC_GREATER_EQUAL);

                // check against operators for booleans
                case CSRGen.BOOL_AND: return binop(to, "&&", PREC_LOGICAL_AND);
                case CSRGen.BOOL_OR: return binop(to, "||", PREC_LOGICAL_OR);
                case CSRGen.BOOL_NOT: return unop(to, "!", PREC_NOT);

                // check against struct operators
                case CSRGen.CSRSTRUCT_GETREFFIELD:
                    return getMember(to, ((CSRStruct.GetRefField)op).field, "->");
                case CSRGen.CSRSTRUCT_GETVALFIELD:
                    return getMember(to, ((CSRStruct.GetValueField)op).field, ".");
                case CSRGen.CSRSTRUCT_SETREFFIELD:
                    return setMember(to, ((CSRStruct.SetRefField)op).field, "->");
                case CSRGen.CSRSTRUCT_SETVALFIELD:
                    return setMember(to, ((CSRStruct.SetValueField)op).field, ".");
                case CSRGen.CSRSTRUCT_NEWVAL: {
                    CSRStruct.NewValue nv = (CSRStruct.NewValue)op;
                    StringBuffer buf = new StringBuffer("((");
                    buf.append(nv.structType);
                    buf.append("){");
                    int cntr = 0;
                    for (TIRExpr e : to.operands) {
                        if (cntr > 0) buf.append(", ");
                        buf.append(CEX(e));
                        cntr++;
                    }
                    buf.append("})");
                    return new CIR.CExpr(0, buf.toString());
                }
                    // check against C array operators
                case CSRGen.CSRARRAY_GETELEM:
                    return $expr(PREC_INDEX, "$1[$2]",
                            CEX(PREC_INDEX, to.operands[0]),
                            CEX(to.operands[1]));
                case CSRGen.CSRARRAY_SETELEM:
                    return $expr(PREC_ASSIGN, "$1[$2] = $3",
                            CEX(PREC_INDEX, to.operands[0]),
                            CEX(to.operands[1]),
                            CEX(PREC_ASSIGN, to.operands[2]));

                    // check against C pointer operators
                case CSRGen.CSRPTR_GET:
                    return $expr(PREC_DEREF, "*$1",
                            CEX(PREC_DEREF, to.operands[0]));
                case CSRGen.CSRPTR_SET: {
                    CSRPointer.SetPtr set = (CSRPointer.SetPtr)op;
                    return $expr(PREC_ASSIGN, "*$1 = $2",
                            CEX(PREC_UNKNOWN, to.operands[0]),
                            CEX(PREC_ASSIGN, to.operands[1], set.ptrType.ptype));
                }

                    // check against operators for devices
                case VirgilOp.DEVICE_GETREGISTER:
                    return new CIR.CExpr(PREC_TERM, REG(((vpc.hil.Device.GetRegister)op).register));
                case VirgilOp.DEVICE_SETREGISTER: {
                    Device.Register reg = ((vpc.hil.Device.SetRegister) op).register;
                    return $expr(PREC_ASSIGN, "$1 = $2",
                            REG(reg),
                            CEX(PREC_ASSIGN, to.operands[0], csr.encodeType(reg.getType())));
                }
                case VirgilOp.DEVICE_INSTRINVOKE: {
                    Device.InstrInvoke ii = (Device.InstrInvoke)op;
                    return new CIR.CExpr(PREC_TERM, "asm(\""+ii.instr.getName()+"\")");
                }
                    // check against operators for casts
                case CSRGen.CAST_INT_RAW:
                    return cast("unsigned int", to.operands[0]);
                case CSRGen.CAST_INT_CHAR:
                    return cast("char", to.operands[0]);
                case CSRGen.CAST_CHAR_RAW:
                    return cast("unsigned char", to.operands[0]);
                case CSRGen.CAST_CHAR_INT:
                    return cast("int", to.operands[0]);
                case CSRGen.CAST_RAW_INT:
                    return cast("int", to.operands[0]);
                case CSRGen.CAST_RAW_CHAR:
                    return cast("char", to.operands[0]);
                case CSRGen.CSR_COERCE: {
                    CSRType.Coerce c = (CSRType.Coerce)op;
                    return cast(c.getResultType().toString(), to.operands[0]);
                }
                case CSRGen.CSR_EXTERN: {
                    CSRFunction.Extern ef = (CSRFunction.Extern)op;
                    StringBuffer buf = new StringBuffer();
                    for (int cntr = 0; cntr < to.operands.length; cntr++) {
                        if (cntr > 0) buf.append(", ");
                        buf.append(CEX(to.operands[cntr]));
                    }
                    return $expr(PREC_APPLY, "$1($2)", ef.name, buf.toString());
                }

                case CSRGen.CSRGLOBAL_GET:
                    return new CIR.CExpr(PREC_TERM, ((CSRData.GetGlobal)op).global.name);
                case CSRGen.CSRGLOBAL_SET: {
                    CSRData.SetGlobal set = (CSRData.SetGlobal) op;
                    return $expr(PREC_ASSIGN, "$1 = $2",
                            set.global.name,
                            CEX(PREC_ASSIGN, to.operands[0], set.global.type));
                }
            }

            throw Util.failure("compile(op:" + op.getClass() + ") unsupported @ " + to.getSourcePoint());
        }

        private CIR.CExpr getMember(TIROperator to, CSRStruct.IType.Field field, String op) {
            return $expr(PREC_ARROW, "$1"+ op +"$2",
                    CEX(PREC_ARROW, to.operands[0]),
                    field.name);
        }

        private CIR.CExpr setMember(TIROperator to, CSRStruct.IType.Field field, String op) {
            return $expr(PREC_ASSIGN, "$1"+op +"$2 = $3",
                    CEX(PREC_ARROW, to.operands[0]),
                    field.name,
                    CEX(PREC_ASSIGN, to.operands[1], field.type));
        }

        private CIR.CExpr cast(String t, TIRExpr e) {
            return $expr(PREC_CAST, "($1)$2", t, CEX(PREC_CAST, e));
        }

        private CIR.CExpr binop(TIROperator b, String op, int prec) {
            return binop(b.operands[0], op, prec, b.operands[1]);
        }

        private CIR.CExpr unop(TIROperator b, String op, int prec) {
            return new CIR.CExpr(prec, op + CEX(prec, b.operands[0]));
        }

        private CIR.CExpr divide(TIROperator b, String op, int prec) {
            return $expr(prec, "$1 $2 zero_check($3, $4)",
                    CEX(prec, b.operands[0]),
                    op,
                    CEX(prec, b.operands[1]),
                    getFLID(b));
        }

        protected CIR.CExpr compare(boolean eq, TIRExpr left, TIRExpr right) {
            String comp = eq ? "==" : "!=";
            String join = eq ? "&&" : "||";
            if ( left.getType() instanceof Function.IType || right.getType() instanceof Function.IType ) {
                return compareDelegates(left, right, comp, join);
            } else {
                return binop(left, comp, PREC_EQUAL, right);
            }
        }

        public CIR.CExpr visit(TIRConst.Value v, CSRType ctype) {
            return new CIR.CExpr(PREC_TERM, ctype.renderValue(false, v.getValue()));
        }

        public CIR.CExpr visit(TIRThrow e, CSRType ctype) {
            Integer code = CSRProgram.getExceptionCode(e.exception);
            return $expr(PREC_TERM, "($1)throw($2, $3)", ctype, code, getFLID(e.point));
        }

        public CIR.CExpr visit(TIRConst.Symbol c, CSRType ctype) {
            // TODO: implement string constants
            return new CIR.CExpr(PREC_CAST, "(" + ctype + ")0");
        }

        public CIR.CExpr visit(TIRLocal.Get r, CSRType ctype) {
            return new CIR.CExpr(PREC_TERM, r.temp.getName());
        }

        public CIR.CExpr visit(TIRLocal.Set l, CSRType ctype) {
            return new CIR.CExpr(PREC_ASSIGN, compile(l));
        }

        public CIR.CExpr visit(TIRCall c, CSRType ctype) {
            assert !c.delegate;
            StringBuffer buf = new StringBuffer();
            Type[] tn = Tuple.getParameterTypes((Callable)c.func.getType());
            for (int cntr = 0; cntr < tn.length; cntr++) {
                if (cntr > 0) buf.append(", ");
                buf.append(CEX(c.arguments[cntr], csr.encodeType(tn[cntr])));
            }
            return $expr(PREC_APPLY, "$1($2)", CEX(c.func), buf.toString());
        }

        private CIR.CExpr binop(TIRExpr left, String op, int prec, TIRExpr right) {
            return $expr(prec, "$1 $2 $3", CEX(prec, left), op, CEX(prec, right));
        }
    }

    protected class StmtCompiler extends TIRExprVisitor<CIR.CStmt, CSRType> {

        public CIR.CStmt visit(TIRExpr e, CSRType ct) {
            if (TIRUtil.isNoOp(e)) {
                // compile to a single semicolon
                return new CIR.CSingle("");
            } else {
                // default behavior is to assign to a new temporary
                CSRType ctype = csr.encodeType(e.getType());
                return $stmt("$1 = $2", func.newTemporary(ctype), CEX(e));
            }
        }

        public CIR.CStmt visit(TIROperator to, CSRType ctype) {
            switch ( CSRGen.getOpcode(to) ) {
                case CSRGen.CSRSTRUCT_SETREFFIELD: // fall through
                case CSRGen.CSRSTRUCT_SETVALFIELD: // fall through
                case CSRGen.CSRARRAY_SETELEM: // fall through
                case VirgilOp.DEVICE_SETREGISTER: // fall through
                case VirgilOp.DEVICE_INSTRINVOKE: // fall through
                case CSRGen.CSRPTR_SET: // these expressions are fine as statements
                    return new CIR.CSingle(CEX(to));
            }

            // default: bail to the expression compiler and lift value to temp
            return visit((TIRExpr)to, ctype);
        }

        public CIR.CStmt visit(TIRThrow e, CSRType ctype) {
            Integer code = CSRProgram.getExceptionCode(e.exception);
            return $stmt("throw($1, $2)", code, getFLID(e.point));
        }

        public CIR.CStmt visit(TIRBlock s, CSRType ctype) {
            String bl = null;
            String el = null;
            if ( !s.isStraight() ) {
                bl = startLabelOf(s);
                el = endLabelOf(s);
            }
            List<CIR.CStmt> l = Ovid.newLinkedList();
            for (TIRExpr e : s.list) {
                CIR.CStmt st = e.accept(STMT, CSRProgram.VOID);
                if (st == null) continue;
                List<CIR.CStmt> il = st.unNest();
                if (il != null) l.addAll(il);
                else l.add(st);
            }
            return new CIR.CNested(bl, l, el);
        }

        private String endLabelOf(TIRBlock s) {
            return s.label+"_end";
        }

        private String startLabelOf(TIRBlock s) {
            return s.label+"_start";
        }

        public CIR.CStmt visit(TIRLocal.Set l, CSRType ctype) {
            return new CIR.CSingle(compile(l));
        }

        public CIR.CStmt visit(TIRBlock.Break e, CSRType ctype) {
            return new CIR.CSingle("goto " + endLabelOf(e.block));
        }

        public CIR.CStmt visit(TIRIfExpr i, CSRType ctype) {
            CIR.CStmt tt = i.true_target.accept(STMT, CSRProgram.VOID);
            // elide the else clause if it is a noop
            CIR.CStmt ft;
            if (TIRUtil.isNoOp(i.false_target)) ft = null;
            else ft = i.false_target.accept(STMT, CSRProgram.VOID);
            return new CIR.CIfStmt("if ( " + CEX(i.condition) + " ) ", tt, ft);
        }

        public CIR.CStmt visit(TIRSwitch s, CSRType ctype) {
            String before = "switch ( " + CEX(s.expr) + " )";
            List<CIR.CStmt> stmts = Ovid.newLinkedList();
            CSRType et = csr.encodeType(s.expr.getType());
            for (TIRSwitch.Case c : s.cases) {
                for (TIRConst.Value cv : c.value) {
                    Value value = cv.getValue();
                    stmts.add(new CIR.CSingle("case " + et.renderValue(false, value) + ":"));
                }
                stmts.add(c.body.accept(STMT, CSRProgram.VOID));
                stmts.add(new CIR.CSingle("break"));
            }
            if (s.defcase != null) {
                stmts.add(new CIR.CSingle("default:"));
                stmts.add(s.defcase.body.accept(STMT, CSRProgram.VOID));
            }
            CIR.CStmt block = new CIR.CNested(stmts);
            return new CIR.CBlock(before, block, "");
        }


        public CIR.CStmt visit(TIRBlock.Continue e, CSRType ctype) {
            return new CIR.CSingle("goto " + startLabelOf(e.block));
        }

        public CIR.CStmt visit(TIRReturn e, CSRType ctype) {
            if (func.retType == CSRProgram.VOID ) return new CIR.CSingle("return");
            else return $stmt("return $1", CEX(e.value, func.retType));
        }

        public CIR.CStmt visit(TIRCall c, CSRType ctype) {
            return new CIR.CSingle(c.accept(EXP, ctype).nest(0));
        }

    }

    protected String compile(TIRLocal.Set l) {
        return $eval("$1 = $2", l.temp.getName(), CEX(PREC_ASSIGN, l.value, csr.encodeType(l.temp.getType())));
    }

    private String CEX(TIRExpr e) {
        return nest(e.accept(EXP, csr.encodeType(e.getType())));
    }

    private String CEX(TIRExpr e, CSRType ct) {
        return nest(e.accept(EXP, ct));
    }

    private String CEX(int prec, TIRExpr e) {
        return e.accept(EXP, csr.encodeType(e.getType())).nest(prec);
    }

    private String CEX(int prec, TIRExpr e, CSRType ct) {
        return e.accept(EXP, ct).nest(prec);
    }

    private String REG(Device.Register r) {
        String addr = StringUtil.addrToString(r.getAbsoluteAddress());
        return "*((volatile "+ csr.encodeType(r.getType()) +" *)" + addr + ")";
    }

    private static String nest(CIR.CExpr e) {
        return e.nest(0);
    }

    private CIR.CExpr compareDelegates(TIRExpr left, TIRExpr right, String comp, String join) {
        if (isNull(left)) {
            // special case of comparing a delegate against the null constant
            return $expr(PREC_EQUAL, "$1 $2 $3.method", CSRProgram.NULL, comp, CEX(PREC_EQUAL, right));
        }
        if (isNull(right)) {
            // special case of comparing a delegate against the null constant
            return $expr(PREC_EQUAL, "$1 $2 $3.method", CSRProgram.NULL, comp, CEX(PREC_EQUAL, left));
        }

        Type funcType = left.getType();
        String t1 = newTemp(funcType);
        String t2 = newTemp(funcType);
        return $expr(PREC_LOGICAL_OR, "($1 = $2).ref $3 ($4 = $5).ref $6 $1.method $3 $4.method",
                t1,                           // 1 left tmp
                CEX(PREC_LOGICAL_OR, left), // 2 left
                comp,                         // 3 comparison operator
                t2,                           // 4 right tmp
                CEX(PREC_LOGICAL_OR, right), // 5 right
                join);                        // 6 join operator
    }

    private String newTemp(Type type) {
        return func.newTemporary(csr.encodeType(type)).getName();
    }

    private boolean isNull(TIRExpr e) {
        if (e instanceof TIRConst.Value) {
            Value v = ((TIRConst.Value)e).getValue();
            return Reference.isNull(v) || VirgilDelegate.isNull(v);
        }
        return false;
    }

    private String getFLID(TIRExpr e) {
        return "0";
    }

    private String getFLID(SourcePoint pt) {
        return "0";
    }

}
