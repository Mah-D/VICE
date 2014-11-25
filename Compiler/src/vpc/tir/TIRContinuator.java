/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Dec 8, 2007
 */
package vpc.tir;

import cck.parser.*;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.*;
import vpc.core.base.PrimBool;
import vpc.core.base.Reference;
import vpc.core.decl.Method;
import vpc.core.types.*;
import vpc.core.virgil.VirgilClass;
import vpc.core.virgil.VirgilDelegate;
import vpc.tir.expr.Operator;
import vpc.util.Cache;

/**
 * The <code>TIRContinuator</code> definition.
 *
 * @author Ben L. Titzer
 */
public class TIRContinuator {

    public static final TIRExpr[] NOARGS = new TIRExpr[0];
    public static final Value[] NONE = new Value[0];

    private static final int MAX_INSTR = 65536;
    private static final int MAX_VALUE = 65536;

    protected final Program program;
    protected final Environment env;

    final Instruction[] stackI;
    final Value[] stackV;

    int posI;
    int posV;
    int posF;

    Frame frame;

    public TIRContinuator(Program program) {
        this.program = program;
        this.env = new Environment();
        this.stackI = new Instruction[MAX_INSTR];
        this.stackV = new Value[MAX_VALUE];
    }

    void pushI(Instruction instr) {
        stackI[posI++] = instr;
    }

    void pushV(Value val) {
        stackV[posV++] = val;
    }

    Instruction popI() {
        return stackI[--posI];
    }

    Value popV() {
        return stackV[--posV];
    }

    Value peekV(int pos) {
        return stackV[posV - 1 - pos];
    }

    void pokeV(int pos, Value val) {
        stackV[posV - 1 - pos] = val;
    }

    Value[] popV(int num) {
        if (num == 0) {
            return NONE;
        } else {
            Value[] vals = allocValues(num);
            System.arraycopy(stackV, posV - num, vals, 0, num);
            posV -= num;
            return vals;
        }
    }

    void popF() {
        frame = (Frame)frame.prev;
        posV = frame.baseV;
        posI = frame.baseI;
    }

    private Value[] allocValues(int length) {
        if (length < cache.length) {
            Value[] result = cache[length];
            if (result == null) result = cache[length] = new Value[length];
            return result;
        } else {
            return new Value[length];
        }
    }

    protected Value[][] cache = new Value[16][];

    Instruction[] compile(TIRExpr[] exprs) {
        Instruction[] result = new Instruction[exprs.length];
        for (int i = 0; i < exprs.length; i++) {
            result[i] = compile(exprs[i]);
        }
        return result;
    }

    Instruction compile(TIRExpr expr) {
        throw Util.unimplemented();
    }

    Continuation compile(TIRExpr[] exprs, Instruction instr) {
        Continuation c = new Continuation(instr, null);
        for (int i = exprs.length - 1; i >= 0; i--) {
            c = new Continuation(compile(exprs[i]), c);
        }
        return c;
    }

    class Frame extends StackTrace {
        final Type[] typeEnv;
        final int baseV;
        final int baseI;
        Frame(Frame prev, Method method, Type[] typeEnv, int baseV, int baseI) {
            super(prev, method.getFullName());
            this.typeEnv = typeEnv;
            this.baseV = baseV;
            this.baseI = baseI;
        }
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
            Value this_ = stackV[frame.baseV];
            Heap.Record r = (Heap.Record) this_;
            VirgilClass.IType ctype = (VirgilClass.IType)r.getType();
            return ctype.getTypeEnv(program.typeCache)[tp.index];
        }
    }

    abstract class Instruction {
        abstract void apply() throws Operator.Exception;
    }

    class Continuation extends Instruction {
        final Instruction thisInstr;
        final Continuation nextInstr;
        Continuation(Instruction t, Continuation c) {
            thisInstr = t;
            nextInstr = c;
        }
        void apply() {
            // if there is a continuation, push it first
            if (nextInstr != null) pushI(nextInstr);
            // push the instruction to execute
            pushI(thisInstr);
        }
    }

    class Instruction_Op extends Instruction {
        final Operator operator;
        final Instruction[] opInstr;
        Instruction_Op(TIROperator op) {
            operator = op.operator;
            opInstr = compile(op.operands);
        }
        void apply() throws Operator.Exception {
            // apply the specified operator to the operands off the stack
            pushV(operator.apply(env, popV(opInstr.length)));
        }
    }

    class Instruction_Call {
        final TIRCall call;
        Instruction_Call(TIRCall c) {
            call = c;
        }
        void apply() {
            // get the function reference #args down in the stack
            int numArgs = call.arguments.length;
            Value func = peekV(numArgs);
            SourcePoint callpt = call.getSourcePoint();
            if (Reference.isNull(func) || VirgilDelegate.isNull(func)) {
                throwException(Reference.NullCheckException.class, makeStackTrace(callpt, frame));
            }
            // get the body of the method and set the caller's source point
            VirgilDelegate.Val del = VirgilDelegate.fromValue(func);
            TIRRep body = TIRUtil.getRep(del.method);
            if (frame != null) frame.setSourcePoint(callpt);
            // create a new frame and set the "this" object in the stack
            frame = new Frame(frame, del.method, del.typeEnv, posV - numArgs, posI);
            pokeV(numArgs, del.record);
            // compile if necessary and then push the first instruction
            pushI(compile(body.getBody()));
        }
    }

    class Instruction_Return {
        void apply() {
            // pop the return value and then pop the current frame.
            Value value = popV();
            popF();
            pushV(value);
        }
    }

    class Instruction_Block {

    }

    class Instruction_Break {

    }

    class Instruction_Continue {

    }

    class Instruction_If {
        final TIRExpr trueBranch;
        final TIRExpr falseBranch;
        Instruction_If(TIRExpr t, TIRExpr f) {
            trueBranch = t;
            falseBranch = f;
        }
        void apply() {
            Value val = popV();
            if (PrimBool.fromValue(val)) {
                pushI(compile(trueBranch));
            } else {
                pushI(compile(falseBranch));
            }
        }
    }

    class Instruction_Switch {
        final TIRSwitch.Case[] cases;
        final TIRSwitch.Case defcase;
        Instruction_Switch(TIRSwitch isw) {
            cases = isw.cases;
            defcase = isw.defcase;
        }
        void apply() {
            Value val = popV();
            for (int i = 0; i < cases.length; i++) {
                TIRConst.Value[] vals = cases[i].value;
                for (int j = 0; j < vals.length; j++)
                if (Value.compareValues(val, vals[j].value)) {
                    pushI(compile(cases[i].body));
                    return;
                }
            }
            if (defcase != null) pushI(compile(defcase.body));
        }
    }

    class Instruction_GetLocal extends Instruction {
        final Method.Temporary local;
        Instruction_GetLocal(Method.Temporary l) {
            local = l;
        }
        void apply() {
            // read the local variable at the specified location
            pushV(stackV[frame.baseV + local.id]);
        }
    }

    class Instruction_SetLocal extends Instruction {
        final Method.Temporary local;
        Instruction_SetLocal(Method.Temporary l) {
            local = l;
        }
        void apply() {
            // assign to the local variable at the correct stack offset
            stackV[frame.baseV + local.id] = peekV(0);
        }
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

    private StackTrace makeStackTrace(SourcePoint p, Frame frame) {
        frame.setSourcePoint(p);
        return frame;
    }

}
