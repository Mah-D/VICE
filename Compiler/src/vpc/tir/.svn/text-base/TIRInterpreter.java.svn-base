/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 20, 2006
 */

package vpc.tir;

import java.util.concurrent.TimeoutException;

import cck.parser.*;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.*;
import vpc.core.base.*;
import vpc.core.decl.Constructor;
import vpc.core.decl.Method;
import vpc.core.types.*;
import vpc.core.virgil.*;
import vpc.hil.Device;
import vpc.simu.IntptrScheduler;
import vpc.tir.expr.Operator;
import vpc.util.Cache;

/**
 * The <code>TIRInterpreter</code> class implements an interpreter for evaluating
 * expressions in the TIR representation (i.e. <code>TIRExpr</code> instances). This
 * interpreter implements the semantics of all the basic TIR constructs and relies
 * on the behavior of <code>Operator.apply()</code> for the functionality of
 * unknown operators.
 *
 * @author Ben L. Titzer
 * @see Operator
 */
public class TIRInterpreter extends TIRExprVisitor<Value, TIRInterpreter.Frame> {

    public static final TIRExpr[] NOARGS = new TIRExpr[0];
    public static final Value[] NONE = new Value[0];

    private int overflowRethrows = 10;

    public static class Frame extends StackTrace {
        protected final Value[] locals;
        protected Type[] typeEnv;

        public Frame(Frame p, String name, int size) {
            super(p, name);
            locals = new Value[size];
            typeEnv = Type.NOTYPES;
        }
    }

    public interface Monitor {
        public void fireBeforeCall(SourcePoint callpt, Method method);
        public void fireAfterReturn(SourcePoint callpt);
        public void fireBeforeApply(SourcePoint srcpt, Operator operator);
    }

    public class Environment implements Program.DynamicEnvironment {
        public Cache<Type> getTypeCache() {
            return program.typeCache;
        }
        public Heap getHeap() {
            return program.heap;
        }
        public Program getProgram() {
            return program;
        }
        public Type getMethodTypeParam(TypeParam.IType tp) {
            return frame.typeEnv[tp.index];
        }
        public Type getClassTypeParam(TypeParam.IType tp) {
            Value this_ = frame.locals[0];
            Heap.Record r = (Heap.Record) this_;
            VirgilClass.IType ctype = (VirgilClass.IType)r.getType();
            return ctype.getTypeEnv(program.typeCache)[tp.index];
        }
    }

    private static class BlockExit extends RuntimeException {
        final TIRBlock block;
        BlockExit(TIRBlock b) {
            block = b;
        }
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    private static class BlockReentry extends RuntimeException {
        final TIRBlock block;
        BlockReentry(TIRBlock b) {
            block = b;
        }
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    private static class MethodReturn extends RuntimeException {
        Value value;
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    protected final MethodReturn RETURN = new MethodReturn();

    protected final Environment env;
    protected final Program program;

    protected Frame frame;
    protected int indent;

    protected Monitor monitor;

    protected Value[][] cache = new Value[16][];

    public TIRInterpreter(Program p) {
        program = p;
        env = new Environment();
    }

    public void setMonitor(Monitor mon) {
        monitor = mon;
    }

    public Value eval(TIRExpr e) {
    	//IntptrScheduler.Instance.tryInterrupt();
    	if(IntptrScheduler.Instance.isTimeout()){
    		throw new IntptrScheduler.TimeupEvent();
    	}
    	IntptrScheduler.Instance.tryInterrupt();
        return e.accept(this, frame);
    }

    public Value[] eval(TIRExpr[] e) {
        if (e.length == 0) return NONE;
        Value[] vs = allocValues(e.length);
        for (int cntr = 0; cntr < e.length; cntr++)
            vs[cntr] = eval(e[cntr]);
        return vs;
    }

    private Value[] allocValues(int length) {
        return new Value[length];
    }

    public Value invokeComponentMethod(Method m, Value[] args) {
        SourcePoint pt = program.getDefaultSourcePoint();
        VirgilComponent c = (VirgilComponent)m.getCompoundDecl();
        Heap.Record rec = c.getRecord();
        return invoke(pt, m, rec, args, Type.NOTYPES);
    }

    public Value visit(TIRBlock st, Frame frame) {
    	
        Value last = PrimVoid.VALUE;
        int max = st.list.size();
        for ( int cntr = 0; cntr < max; cntr++ ) {
            try {
                last = eval(st.list.get(cntr));
            } catch ( BlockExit e) {
                if ( e.block == st ) return PrimVoid.VALUE; // done with this block
                else throw e; // propagate exit up
            } catch ( BlockReentry e) {
                if ( e.block == st ) cntr = -1; // go back to beginning
                else throw e; // propagate exit up
            }
        }
        return last;
    }

    public Value visit(TIROperator o, Frame frame) {

        try {
            if (monitor != null) monitor.fireBeforeApply(o.getSourcePoint(), o.operator);
            if (o.operator instanceof Operator.Op1) return apply1((Operator.Op1)o.operator, o.operands);
            if (o.operator instanceof Operator.Op2) return apply2((Operator.Op2)o.operator, o.operands);
            if (o.operator instanceof Operator.Op3) return apply3((Operator.Op3)o.operator, o.operands);
            if (o.operator instanceof Operator.Op0) return apply0((Operator.Op0)o.operator);
            return applyN(o.operator, o.operands);
        } catch (Operator.Exception e) {
            throw new SourceException(e.type, makeStackTrace(o.getSourcePoint(), frame), e.msg, StringUtil.EMPTY_STRING_ARRAY);
        }
    }

    public Value apply0(Operator.Op0 operator) throws Operator.Exception {

        try {
            return operator.apply0();
        } catch (VirgilComponent.InitCheckException e) {
            // retry operator (once only) if failure due to init dependency
            initComponent(e.component);
            return operator.apply0();
        }
    }

    public Value apply1(Operator.Op1 operator, TIRExpr[] operands) throws Operator.Exception {

        assert operands.length == 1;
        Value v0 = eval(operands[0]);
        try {
            return operator.apply1(v0);
        } catch (VirgilComponent.InitCheckException e) {
            // retry operator (once only) if failure due to init dependency
            initComponent(e.component);
            return operator.apply1(v0);
        }
    }

    public Value apply2(Operator.Op2 operator, TIRExpr[] operands) throws Operator.Exception {
        assert operands.length == 2;
        Value v0 = eval(operands[0]);
        Value v1 = eval(operands[1]);
        try {
            return operator.apply2(v0, v1);
        } catch (VirgilComponent.InitCheckException e) {
            // retry operator (once only) if failure due to init dependency
            initComponent(e.component);
            return operator.apply2(v0, v1);
        }
    }

    public Value apply3(Operator.Op3 operator, TIRExpr[] operands) throws Operator.Exception {
        assert operands.length == 3;
        Value v0 = eval(operands[0]);
        Value v1 = eval(operands[1]);
        Value v2 = eval(operands[2]);
        try {
            return operator.apply3(v0, v1, v2);
        } catch (VirgilComponent.InitCheckException e) {
            // retry operator (once only) if failure due to init dependency
            initComponent(e.component);
            return operator.apply3(v0, v1, v2);
        }
    }

    public Value applyN(Operator operator, TIRExpr[] operands) throws Operator.Exception {
        Value[] vals = eval(operands);
        try {
            return operator.apply(env, vals);
        } catch (VirgilComponent.InitCheckException e) {
            // retry operator (once only) if failure due to init dependency
            initComponent(e.component);
            return operator.apply(env, vals);
        }
    }

    public Value visit(TIRBlock.Break b, Frame frame) {
        throw new BlockExit(b.block);
    }

    public Value visit(TIRBlock.Continue c, Frame frame) {
        throw new BlockReentry(c.block);
    }

    public Value visit(TIRIfExpr s, Frame frame) {
        Value val = eval(s.condition);
        if (PrimBool.fromValue(val)) return eval(s.true_target);
        else return eval(s.false_target);
    }

    public Value visit(TIRReturn r, Frame frame) {
        RETURN.value = eval(r.value); // evaluate return value
        throw RETURN;
    }

    public Value visit(TIRThrow r, Frame frame) {
        return throwException(r.exception, makeStackTrace(r.point, frame));
    }

    private Value throwException(Class<? extends Operator.Exception> eclass, StackTrace trace) {
        SourceException se;
        try {
            Operator.Exception e = eclass.newInstance();
            se = new SourceException(e.type, trace, e.msg, StringUtil.EMPTY_STRING_ARRAY);
        } catch (Throwable e1) {
            throw Util.unexpected(e1);
        }
        throw se;
    }

    public Value visit(TIRSwitch s, Frame frame) {
        boolean eval = false;
        Value val = eval(s.expr);
        // linear search for now; could be hashtable or array
        for (TIRSwitch.Case c : s.cases) {
            for (TIRConst v : c.value) {
                if (Value.compareValues(val, eval(v))) {
                    eval(c.body);
                    eval = true;
                }
            }
        }
        // no match; execute default case
        if (!eval && s.defcase != null) eval(s.defcase.body);
        return PrimVoid.VALUE;
    }

    public Value visit(TIRCall d, Frame frame) {

    	//TODO: skip the instruction InstrInvoke and InstrUse
    	TIRExpr e = d.func;
    	if(e instanceof TIROperator){
    		TIROperator o = (TIROperator)e;
    		if(o.operator instanceof Device.InstrInvoke || o.operator instanceof Device.InstrUse){
    			//System.out.println("A instruction call!!!!!!!!!!!!");
    			return PrimVoid.VALUE;
    		}
    	}
    	
        Value val = eval(d.func);
        SourcePoint callpt = d.getSourcePoint();
        if (Reference.isNull(val) || VirgilDelegate.isNull(val)) {
            throwException(Reference.NullCheckException.class, makeStackTrace(d.getSourcePoint(), frame));
        }
        VirgilDelegate.Val m = VirgilDelegate.fromValue(val);
        Value[] vals = eval(d.arguments);
        return invoke(callpt, m.method, m.record, vals, m.typeEnv);
    }

    public Value visit(TIRLocal.Set l, Frame frame) {
        // a = value;
        Value val = eval(l.value);
        frame.locals[l.temp.id] = val;
        return val;
    }

    public Value visit(TIRLocal.Get l, Frame frame) {
        return frame.locals[l.temp.id];
    }

    public Value visit(TIRConst.Value c, Frame frame) {
        return c.value;
    }

    public Value visit(TIRConst.Symbol c, Frame frame) {
        return fromJavaString(program, c.value());
    }

    public Value visit(TIRExpr e, Frame frame) {
        throw fail("eval(" + e.getClass() + ") unimplemented", e);
    }

    public Heap.Record initComponent(VirgilComponent comp) {
        Heap.Record compRecord = comp.getRecord();
        if (compRecord == null) {
            compRecord = buildComponentRecord(comp);
            comp.setRecord(compRecord);
            Constructor constructor = comp.getConstructor();
            if (constructor != null) {
                invoke(null, constructor, compRecord, NONE, Type.NOTYPES);
            }
        }
        return compRecord;
    }

    private Heap.Record buildComponentRecord(VirgilComponent comp) {
        Heap.Layout layout = program.closure.getLayout(comp);
        if (layout == null)
            throw fail("No layout for component " + comp.getName());
        Heap.Record compRecord = program.heap.allocRecord(layout.type, layout.size());
        program.heap.addRoot(compRecord);
        return compRecord;
    }

    private Value invoke(SourcePoint pt, Method m, Heap.Record rec, Value[] args, Type[] te) {
        TIRRep body = TIRUtil.getRep(m);
        Frame nf = newFrame(pt, m.getFullName(), body);
        initFrame(nf, rec, args);
        nf.typeEnv = te;
        Value result;
        try {
            if (monitor != null) monitor.fireBeforeCall(pt, m);
            frame = nf;
            eval(body.getBody());
            result = PrimVoid.VALUE;
        } catch (MethodReturn r) {
            result = r.value;
        } catch (StackOverflowError e) {
            if (overflowRethrows-- > 0) {
                throw e;
            }
            throw new SourceException("StackOverflow", makeStackTrace(pt, frame), "too many recursive invocations", StringUtil.EMPTY_STRING_ARRAY);
        } finally {
            if (monitor != null) monitor.fireAfterReturn(pt);
            frame = (Frame) frame.prev;
        }
        return result;
    }

    private void initFrame(Frame nf, Heap.Record this_, Value[] args) {
        nf.locals[0] = this_;
        if(args != null){
        	try {
        		System.arraycopy(args, 0, nf.locals, 1, args.length);
        	} catch (Throwable e) {
        		e.printStackTrace();
        	}
        }
    }

    private Frame newFrame(SourcePoint callpt, String name, TIRRep meth) {
        if (frame != null) frame.setSourcePoint(callpt);
        return new Frame(frame, name, meth.getNumberOfTemps());
    }

    private StackTrace makeStackTrace(SourcePoint p, Frame frame) {
        frame.setSourcePoint(p);
        return frame;
    }

    private SourceException fail(String msg, TIRExpr e) {
        return fail(msg, e.getSourcePoint());
    }

    private SourceException fail(String msg) {
        return fail(msg, (SourcePoint)null);
    }

    private SourceException fail(String msg, SourcePoint pt) {
        throw new SourceException("Failure", makeStackTrace(pt, frame), msg, StringUtil.EMPTY_STRING_ARRAY);
    }

    public static Heap.Record fromJavaString(Program p, String javaStr) {
        VirgilArray.IType charArray = VirgilArray.getArrayType(p.typeCache, PrimChar.TYPE);
        Heap.Record str = VirgilArray.allocArray(p.heap, charArray, javaStr.length());
        for ( int cntr = 0; cntr < javaStr.length(); cntr++ ) {
            str.setValue(cntr, PrimChar.toValue(javaStr.charAt(cntr)));
        }
        return str;
    }

    public static String toJavaString(Value v) {
        Heap.Record r = (Heap.Record) v;
        char[] a = new char[r.getSize()];
        for ( int cntr = 0; cntr < a.length; cntr++ )
            a[cntr] = PrimChar.fromValue(r.getValue(cntr));
        return new String(a);
    }

    public static Heap.Record fromByteArray(Program p, byte[] array) {
        VirgilArray.IType charArray = VirgilArray.getArrayType(p.typeCache, PrimChar.TYPE);
        Heap.Record str = VirgilArray.allocArray(p.heap, charArray, array.length);
        for ( int cntr = 0; cntr < array.length; cntr++ ) {
            byte b = array[cntr];
            if (b != 0) {
                str.setValue(cntr, PrimChar.toValue((char) b));
            }
        }
        return str;
    }

    public static byte[] toByteArray(Value v) {
        Heap.Record r = (Heap.Record) v;
        byte[] a = new byte[r.getSize()];
        for ( int cntr = 0; cntr < a.length; cntr++ ) {
            final Value val = r.getValue(cntr);
            if (val != null) {
                a[cntr] = (byte) PrimChar.fromValue(val);
            }
        }
        return a;
    }


}
