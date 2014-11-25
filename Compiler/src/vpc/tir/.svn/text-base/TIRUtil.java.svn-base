/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Jan 29, 2006
 */
package vpc.tir;

import cck.parser.SourcePoint;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.Heap;
import vpc.core.Value;
import vpc.core.base.*;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.tir.expr.Operator;

import java.util.List;

/**
 * The <code>TIRUtil</code> class contains a collection of utility methods for
 * working with code in the TIR format.
 *
 * @author Ben L. Titzer
 */
public class TIRUtil {

    public static final String THIS_PARAM = "__this";

    /**
     * The <code>getRep()</code> method gets the TIR method representation for a given
     * method declaration by looking it up in the representation map.
     *
     * @param method the method for which to retrieve the TIR code
     * @return a reference to the <code>TIRRep</code> instances that represents
     *         the code for the specified method
     */
    public static TIRRep getRep(Method method) {
        return (TIRRep) method.getMethodRep(TIRRep.REP_NAME);
    }

    public static String exprsToString(TIRExpr[] args) {
        StringBuffer b = new StringBuffer("[");
        for (int cntr = 0; cntr < args.length; cntr++) {
            if (cntr != 0) b.append(", ");
            b.append(args[cntr].toString());
        }
        b.append("]");
        return b.toString();
    }

    public static TIRLocal.Get lift(TIRRep method, Method.Temporary temp, TIRExpr e, TIRBlock block) {
        if ( e instanceof TIRLocal.Set ) {
            // this statement is already an assignment; lift and return the temp
            block.addExpr(e);
            TIRLocal.Get rd = new TIRLocal.Get(((TIRLocal.Set)e).temp);
            copy(e, rd);
            return rd;
        } else {
            // this expression requires a new temporary (if one is not supplied)
            if ( temp == null )
                temp = method.newTemporary(e.getType());
            TIRLocal.Set wr = new TIRLocal.Set(temp, e);
            copy(e, wr);
            block.addExpr(wr);
            TIRLocal.Get rd = new TIRLocal.Get(temp);
            copy(e, rd);
            return rd;
        }
    }

    public static TIROperator nullCheck(TIRExpr e) {
        Reference.NullCheck check = new Reference.NullCheck(e.getType());
        TIROperator op = $OP(check, e);
        op.setType(e.getType());
        op.setSourcePoint(e.getSourcePoint());
        return op;
    }

    public static Util.Error fail(String msg, TIRExpr e) {
        String nstr = StringUtil.quote(e.getClass());
        SourcePoint sourcePoint = e.getSourcePoint();
        return Util.failure("TIRBuilder failure: " + msg + " on node " + nstr + " @ " + sourcePoint);
    }

    public static <R extends TIRExpr> R copy(TIRExpr oe, R ne) {
        ne.setType(oe.getType());
        ne.setSourcePoint(oe.getSourcePoint());
        return ne;
    }

    public static <R extends TIRExpr> R label(TIRExpr oe, R ne) {
        ne.setSourcePoint(oe.getSourcePoint());
        return ne;
    }

    public static TIRLocal.Set dup(TIRLocal.Set e, TIRExpr v) {
        return copy(e, new TIRLocal.Set(e.temp, v));
    }

    public static TIRLocal.Get dup(TIRLocal.Get e) {
        return copy(e, new TIRLocal.Get(e.temp));
    }

    public static TIROperator dup(TIROperator e, TIRExpr... o) {
        return copy(e, new TIROperator(e.operator, o));
    }

    public static TIRConst.Value dup(TIRConst.Value e) {
        return copy(e, new TIRConst.Value(e.value));
    }

    public static TIRConst.Symbol dup(TIRConst.Symbol e) {
        return copy(e, new TIRConst.Symbol(e.orig, e.value));
    }

    public static TIRCall dup(TIRCall e, TIRExpr f, TIRExpr... a) {
        return copy(e, new TIRCall(e.delegate, f, a));
    }

    public static TIRReturn dup(TIRReturn e, TIRExpr v) {
        return copy(e, new TIRReturn(v));
    }

    public static TIRThrow dup(TIRThrow e) {
        return copy(e, new TIRThrow(e.exception, e.point));
    }

    public static TIRIfExpr dup(TIRIfExpr e, TIRExpr c, TIRExpr t, TIRExpr f) {
        return copy(e, new TIRIfExpr(c, t, f));
    }

    public static TIRSwitch dup(TIRSwitch e, TIRExpr v, TIRSwitch.Case[] c, TIRSwitch.Case d) {
        return copy(e, new TIRSwitch(v, c, d));
    }

    public static TIRBlock dup(TIRBlock e, List<TIRExpr> i) {
        TIRBlock b = copy(e, new TIRBlock(e.label));
        for ( TIRExpr ie : i ) b.addExpr(ie);
        return b;
    }

    public static boolean isNoOp(TIRExpr nstmt) {
        return nstmt.getType() == PrimVoid.TYPE && nstmt instanceof TIRConst.Value;
    }

    public static boolean isGet(TIRExpr nstmt) {
        return nstmt instanceof TIRLocal.Get;
    }

    public static boolean isValue(TIRExpr e) {
        return e instanceof TIRConst.Value;
    }

    public static TIROperator $OP(Operator op, TIRExpr... list) {
        return new TIROperator(op, list);
    }

    public static TIRExpr $ISNOTNULL(TIRExpr e) {
        return $NEQ(e.getType(), e, $NULL());
    }

    public static TIRExpr $ISNULL(TIRExpr e) {
        return $EQ(e.getType(), e, $NULL());
    }

    public static TIRExpr $NEQ(Type t, TIRExpr a, TIRExpr b) {
        return $OP(new Value.NotEqual(t), a, b);
    }

    public static TIRExpr $EQ(Type t, TIRExpr a, TIRExpr b) {
        return $OP(new Value.Equal(t), a, b);
    }

    public static TIRExpr $AND(TIRExpr a, TIRExpr b) {
        TIRIfExpr e = new TIRIfExpr(a, b, $VAL(PrimBool.FALSE));
        e.setType(PrimBool.TYPE);
        return e;
    }

    public static TIRExpr $OR(TIRExpr a, TIRExpr b) {
        TIRIfExpr e = new TIRIfExpr(a, $VAL(PrimBool.TRUE), b);
        e.setType(PrimBool.TYPE);
        return e;
    }

    public static TIRExpr $IF(TIRExpr c, TIRExpr a, TIRExpr b) {
        TIRIfExpr e = new TIRIfExpr(c, a, b);
        e.setType(a.getType());
        return e;
    }

    public static TIRLocal.Get $GET(Method.Temporary t) {
        return new TIRLocal.Get(t);
    }

    public static TIRCall $CALL(boolean bound, TIRExpr e, TIRExpr... args) {
        return new TIRCall(bound, e, args);
    }

    public static TIRLocal.Set $SET(Method.Temporary t, TIRExpr e) {
        return new TIRLocal.Set(t, e);
    }

    public static TIRConst.Value $REF(Type t, Heap.Record r) {
        return new TIRConst.Value(t, r);
    }

    public static TIRConst.Value $REF(Heap.Record r) {
        return new TIRConst.Value(r);
    }

    public static TIRConst.Value $NULL() {
        return new TIRConst.Value(Value.BOTTOM);
    }

    public static TIRConst.Value $VAL(Value v) {
        return new TIRConst.Value(v);
    }

    public static TIRExpr $NOOP() {
        TIRConst.Value nop = new TIRConst.Value(PrimVoid.VALUE);
        nop.setType(PrimVoid.TYPE);
        return nop;
    }

    public static TIRExpr $NOOP(SourcePoint pt) {
        TIRExpr nop = new TIRConst.Value(PrimVoid.VALUE);
        nop.setSourcePoint(pt);
        nop.setType(PrimVoid.TYPE);
        return nop;
    }

    public static TIRConst.Value $VAL(int v) {
        return new TIRConst.Value(PrimInt32.toValue(v));
    }

    public static TIRConst.Value $VAL(boolean b) {
        return new TIRConst.Value(PrimBool.toValue(b));
    }

    public static TIROperator $GR(TIRExpr a, TIRExpr b) {
        return $OP(new PrimInt32.GR(), a, b);
    }

    public static TIROperator $GREQ(TIRExpr a, TIRExpr b) {
        return $OP(new PrimInt32.GREQ(), a, b);
    }

    public static TIROperator $LTEQ(TIRExpr a, TIRExpr b) {
        return $OP(new PrimInt32.LTEQ(), a, b);
    }

    public static TIROperator $LT(TIRExpr a, TIRExpr b) {
        return $OP(new PrimInt32.LT(), a, b);
    }

    public static TIRBlock $BLOCK(TIRExpr... l) {
        // TODO: uniquify the label
        TIRBlock b = new TIRBlock("B");
        for ( TIRExpr e : l ) b.addExpr(e);
        return b;
    }

    public static Value[] reduce(TIRExpr... list) {
        if ( list.length == 0 ) return TIRInterpreter.NONE;
        if ( !isValue(list[0]) ) return null;
        Value[] vals = new Value[list.length];
        for ( int cntr = 0; cntr < list.length; cntr++ ) {
            TIRExpr e = list[cntr];
            if ( !isValue(e) ) return null;
            vals[cntr] = ((TIRConst.Value)e).getValue();
        }
        return vals;
    }

    public static Value valOf(TIRExpr e) {
        if ( !isValue(e) ) return null;
        return ((TIRConst.Value)e).getValue();
    }

}
