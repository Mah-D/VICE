/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Apr 4, 2006
 */
package vpc.core.csr;

import cck.util.Util;
import vpc.core.Value;
import vpc.core.base.*;
import vpc.core.decl.Variable;
import vpc.core.types.Type;
import vpc.core.virgil.*;
import vpc.tir.TIRExpr;
import vpc.tir.TIROperator;
import vpc.tir.expr.Operator;
import vpc.tir.expr.Precedence;
import vpc.tir.tir2c.CIR;
import vpc.util.Ovid;

import java.util.*;

/**
 * The <code>CSRGen</code> class contains a collection of helper methods that make it
 * easier to generate CIR code.
 *
 * @author Ben L. Titzer
 */
public class CSRGen {

    public static final int VALUE_EQU             =  0;
    public static final int VALUE_NEQU            =  1;

    public static final int INT32_ADD             =  2;
    public static final int INT32_SUB             =  3;
    public static final int INT32_MUL             =  4;
    public static final int INT32_DIV             =  5;
    public static final int INT32_MOD             =  6;
    public static final int INT32_LT              =  7;
    public static final int INT32_GR              =  8;
    public static final int INT32_LTEQ            =  9;
    public static final int INT32_GREQ            = 10;
    public static final int INT32_NEG             = 11;

    public static final int CHAR_LT               = 12;
    public static final int CHAR_GR               = 13;
    public static final int CHAR_LTEQ             = 14;
    public static final int CHAR_GREQ             = 15;

    public static final int BOOL_AND              = 16;
    public static final int BOOL_OR               = 17;
    public static final int BOOL_NOT              = 18;

    public static final int RAW_AND               = 19;
    public static final int RAW_OR                = 20;
    public static final int RAW_XOR               = 21;
    public static final int RAW_SHL               = 22;
    public static final int RAW_SHR               = 23;
    public static final int RAW_COMP              = 24;

    public static final int CAST_INT_RAW          = 25;
    public static final int CAST_INT_CHAR         = 26;
    public static final int CAST_CHAR_RAW         = 27;
    public static final int CAST_CHAR_INT         = 28;
    public static final int CAST_RAW_INT          = 29;
    public static final int CAST_RAW_CHAR         = 30;
    public static final int CAST_RAW_RAW          = 31;

    public static final int CSRSTRUCT_GETREFFIELD = 32;
    public static final int CSRSTRUCT_SETREFFIELD = 33;
    public static final int CSRSTRUCT_GETVALFIELD = 34;
    public static final int CSRSTRUCT_SETVALFIELD = 35;
    public static final int CSRSTRUCT_NEWVAL      = 36;

    public static final int CSRARRAY_GETELEM      = 37;
    public static final int CSRARRAY_SETELEM      = 38;

    public static final int CSRPTR_GET            = 39;
    public static final int CSRPTR_SET            = 40;

    public static final int CSR_EXTERN            = 41;
    public static final int CSR_COERCE            = 42;

    public static final int CSRGLOBAL_GET         = 43;
    public static final int CSRGLOBAL_SET         = 44;

    public static HashMap<Class<? extends Operator>, Integer> opMap;

    static {
        opMap = Ovid.newHashMap();

        // register comparison operators
        opMap.put(Value.Equal.class, VALUE_EQU);
        opMap.put(Value.NotEqual.class, VALUE_NEQU);

        // register the integer operators
        opMap.put(PrimInt32.ADD.class, INT32_ADD);
        opMap.put(PrimInt32.SUB.class, INT32_SUB);
        opMap.put(PrimInt32.MUL.class, INT32_MUL);
        opMap.put(PrimInt32.DIV.class, INT32_DIV);
        opMap.put(PrimInt32.MOD.class, INT32_MOD);
        opMap.put(PrimInt32.LT.class, INT32_LT);
        opMap.put(PrimInt32.GR.class, INT32_GR);
        opMap.put(PrimInt32.LTEQ.class, INT32_LTEQ);
        opMap.put(PrimInt32.GREQ.class, INT32_GREQ);
        opMap.put(PrimInt32.NEG.class, INT32_NEG);

        // register the raw operators
        opMap.put(PrimRaw.OR.class, RAW_OR);
        opMap.put(PrimRaw.AND.class, RAW_AND);
        opMap.put(PrimRaw.XOR.class, RAW_XOR);
        opMap.put(PrimRaw.SHL.class, RAW_SHL);
        opMap.put(PrimRaw.SHR.class, RAW_SHR);
        opMap.put(PrimRaw.Complement.class, RAW_COMP);

        // register the known operators for characters
        opMap.put(PrimChar.LT.class, CHAR_LT);
        opMap.put(PrimChar.GR.class, CHAR_GR);
        opMap.put(PrimChar.LTEQ.class, CHAR_LTEQ);
        opMap.put(PrimChar.GREQ.class, CHAR_GREQ);

        // register operators for booleans
        opMap.put(PrimBool.AND.class, BOOL_AND);
        opMap.put(PrimBool.OR.class, BOOL_OR);
        opMap.put(PrimBool.NOT.class, BOOL_NOT);

        opMap.put(PrimConversion.Int32_Raw.class, CAST_INT_RAW);
        opMap.put(PrimConversion.Int32_Char.class, CAST_INT_CHAR);
        opMap.put(PrimConversion.Char_Raw.class, CAST_CHAR_RAW);
        opMap.put(PrimConversion.Char_Int32.class, CAST_CHAR_INT);
        opMap.put(PrimConversion.Raw_Char.class, CAST_RAW_INT);
        opMap.put(PrimConversion.Raw_Int32.class, CAST_RAW_CHAR);
        opMap.put(PrimConversion.AdjustRaw.class, CAST_RAW_RAW);

        opMap.put(CSRStruct.GetRefField.class, CSRSTRUCT_GETREFFIELD);
        opMap.put(CSRStruct.SetRefField.class, CSRSTRUCT_SETREFFIELD);
        opMap.put(CSRStruct.GetValueField.class, CSRSTRUCT_GETVALFIELD);
        opMap.put(CSRStruct.SetValueField.class, CSRSTRUCT_SETVALFIELD);
        opMap.put(CSRStruct.NewValue.class, CSRSTRUCT_NEWVAL);

        opMap.put(CSRArray.GetElement.class, CSRARRAY_GETELEM);
        opMap.put(CSRArray.SetElement.class, CSRARRAY_SETELEM);

        opMap.put(CSRPointer.GetPtr.class, CSRPTR_GET);
        opMap.put(CSRPointer.SetPtr.class, CSRPTR_SET);

        // register raw bit operators
        opMap.put(PrimRaw.Concat.class, VirgilOp.RAW_CONCAT);
        opMap.put(PrimRaw.GetBit.class, VirgilOp.RAW_GETBIT);
        opMap.put(PrimRaw.SetBit.class, VirgilOp.RAW_SETBIT);

        // register null check
        opMap.put(Reference.NullCheck.class, VirgilOp.NULL_CHECK);

        // register operators for classes
        opMap.put(VirgilClass.GetField.class, VirgilOp.CLASS_GETFIELD);
        opMap.put(VirgilClass.SetField.class, VirgilOp.CLASS_SETFIELD);
        opMap.put(VirgilClass.GetMethod.class, VirgilOp.CLASS_GETMETHOD);
        opMap.put(VirgilClass.TypeQuery.class, VirgilOp.TYPE_QUERY);
        opMap.put(VirgilClass.TypeCast.class, VirgilOp.TYPE_CAST);

        // register operators for components
        opMap.put(VirgilComponent.GetField.class, VirgilOp.COMPONENT_GETFIELD);
        opMap.put(VirgilComponent.SetField.class, VirgilOp.COMPONENT_SETFIELD);
        opMap.put(VirgilComponent.GetMethod.class, VirgilOp.COMPONENT_GETMETHOD);

        // register operators for arrays
        opMap.put(VirgilArray.GetElement.class, VirgilOp.ARRAY_GETELEMENT);
        opMap.put(VirgilArray.SetElement.class, VirgilOp.ARRAY_SETELEMENT);
        opMap.put(VirgilArray.GetLength.class, VirgilOp.ARRAY_GETLENGTH);
        opMap.put(VirgilArray.Init.class, VirgilOp.ARRAY_INIT);

        opMap.put(Tuple.GetElement.class, VirgilOp.TUPLE_GETELEMENT);

        opMap.put(vpc.hil.Device.GetRegister.class, VirgilOp.DEVICE_GETREGISTER);
        opMap.put(vpc.hil.Device.SetRegister.class, VirgilOp.DEVICE_SETREGISTER);
        opMap.put(vpc.hil.Device.InstrUse.class, VirgilOp.DEVICE_INSTRUSE);
        opMap.put(vpc.hil.Device.InstrInvoke.class, VirgilOp.DEVICE_INSTRINVOKE);

        opMap.put(VirgilClass.Alloc.class, VirgilOp.NEW_OBJECT);
        opMap.put(VirgilArray.Alloc.class, VirgilOp.NEW_ARRAY);

        opMap.put(CSRFunction.Extern.class, CSR_EXTERN);
        opMap.put(CSRType.Coerce.class, CSR_COERCE);

        opMap.put(CSRData.GetGlobal.class, CSRGLOBAL_GET);
        opMap.put(CSRData.SetGlobal.class, CSRGLOBAL_SET);
    }

    public static CIR.CStmt DECLARE(CSRFunction cf, String var, CSRType ctype, CIR.CExpr e) {
        String v = cf.newVariable(var, ctype).getName();
        return new CIR.CSingle(v + " = " + e.nest(0));
    }

    public static CIR.CStmt ASSIGN(String dest, String expr) {
        return new CIR.CSingle(dest + " = " + expr);
    }

    public static CIR.CBlock FOR(CIR.CExpr i, CIR.CExpr c, CIR.CExpr u, CIR.CStmt st) {
        return new CIR.CBlock("for ( " + empty(i) + "; " + c.nest(0) + "; " + empty(u) + " )", st, "");
    }

    public static CIR.CIfStmt IF(CIR.CExpr c, CIR.CStmt t, CIR.CStmt f) {
        return new CIR.CIfStmt("if ( " + c.nest(0) + " ) ", t, f);
    }

    private static String empty(CIR.CExpr i) {
        return i == null ? "" : i.nest(0);
    }

    public static CIR.CStmt RETURN(CIR.CExpr c) {
        return new CIR.CSingle("return " + c.nest(0));
    }

    public static CIR.CExpr CALL(String n, CIR.CExpr... e) {
        StringBuffer buf = new StringBuffer();
        buf.append(n);
        buf.append('(');
        for (int cntr = 0; cntr < e.length; cntr++) {
            if (cntr > 0) buf.append(", ");
            buf.append(e[cntr].nest(0));
        }
        buf.append(")");
        return new CIR.CExpr(Precedence.PREC_APPLY, buf.toString());
    }

    public static CIR.CNested NEST(CIR.CStmt... st) {
        return new CIR.CNested(toList(st));
    }

    public static CIR.CExpr EXPR(String s) {
        return new CIR.CExpr(0, s);
    }

    public static CIR.CStmt STMT(String s) {
        return new CIR.CSingle(s);
    }

    protected static <T> List<T> toList(T[] a) {
        ArrayList<T> list = new ArrayList<T>(a.length);
        for (T i : a) list.add(i);
        return list;
    }

    protected static Variable VAR(String name, Type atype) {
        return new Var(name, atype);
    }

    protected static Variable INTVAR(String name) {
        return new Var(name, CSRProgram.INT32);
    }

    public static int getOpcode(TIROperator to) {
        Integer val = opMap.get(to.operator.getClass());
        if ( val == null ) unknownOperator(to.operator, to);
        return val;
    }

    public static int getOpcode(TIRExpr to) {
        if ( to instanceof TIROperator ) return getOpcode((TIROperator)to);
        return -1;
    }

    public static void unknownOperator(Operator op, TIROperator to) {
        throw Util.failure("unknown operator (op:" + op.getClass() + ") @ " + to.getSourcePoint());
    }

    public static class Var implements Variable {
        public final String name;
        public final Type type;
        public Var(String n, Type t) {
            name = n;
            type = t;
        }
        public Type getType() {
            return type;
        }
        public String getName() {
            return name;
        }
    }
}
