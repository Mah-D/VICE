/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 7, 2006
 */

package vpc.hil;

import vpc.core.Value;
import vpc.core.types.Typeable;
import vpc.core.types.Type;
import vpc.core.base.*;
import vpc.core.base.PrimRaw.IType;
import vpc.core.csr.CSRStruct;
import vpc.core.decl.BaseDecl;
import vpc.core.decl.Decl;
import vpc.hil.parser.Token;
import vpc.simu.IntptrScheduler;
import vpc.tir.expr.Operator;
import vpc.util.HashList;
import cck.text.StringUtil;
import cck.util.Util;

/**
 * The <code>Device</code> class represents a hardware device that has interrupts, memory spaces, regions, and
 * hardware registers.
 *
 * @author Ben L. Titzer
 */
public class Device extends BaseDecl {

    public class Instruction extends BaseDecl {

        public Instruction(Token n) {
            super(n);
        }
    }

    public class Interrupt extends BaseDecl {

        public final Token number;

        public final Token flagReg;
        public final Token flagBit;
        public final Token maskReg;
        public final Token maskBit;

        public Interrupt(Token n, Token i, Token fr, Token fb, Token mr, Token mb) {
            super(n);
            number = i;
            flagReg = fr;
            flagBit = fb;
            maskReg = mr;
            maskBit = mb;
        }
    }

    public class Region extends BaseDecl {

        protected final Token space;
        protected final Token low;
        protected final Token high;

        public Region(Token n, Token s, Token l, Token h) {
            super(n);
            space = s;
            low = l;
            high = h;
        }

        public int getBaseAddress() {
            return StringUtil.evaluateIntegerLiteral(low.image);
        }
    }

    public class Register extends BaseDecl implements Typeable {

        public final Token regionToken;
        public final Token index;
        protected Region region;

        public Register(Token n, Token r, Token i) {
            super(n);
            regionToken = r;
            index = i;
        }

        public int getAbsoluteAddress() {
            if (region == null) region = regions.get(regionToken.image);
            int ind = StringUtil.evaluateIntegerLiteral(index.image);
            int base = region.getBaseAddress();
            return base + ind;
        }

        public Type getType() {
            // TODO: get the raw width from the address space (or region)
            return PrimRaw.getType(8);
        }
    }

    public class Space extends BaseDecl {

        public int addressWidth;

        public Space(Token n, Token w, Token t) {
            super(n);
        }
    }

    public final HashList<String, Space> spaces = new HashList<String, Space>();
    public final HashList<String, Region> regions = new HashList<String, Region>();
    public final HashList<String, Register> registers = new HashList<String, Register>();
    public final HashList<String, Interrupt> interrupts = new HashList<String, Interrupt>();
    public final HashList<String, Instruction> instructions = new HashList<String, Instruction>();

    public Device(Token n) {
        super(n);
    }

    private <T extends Decl> T add(HashList<String, T> map, T t) {
        map.add(t.getName(), t);
        return t;
    }

    public Space newSpace(Token n, Token w, Token t) {
        return add(spaces, new Space(n, w, t));
    }

    public Region newRegion(Token n, Token s, Token l, Token h) {
        return add(regions, new Region(n, s, l, h));
    }

    public Register newRegister(Token n, Token r, Token i) {
        return add(registers, new Register(n, r, i));
    }

    public Interrupt newInterrupt(Token n, Token in, Token fr, Token fb, Token mr, Token mb) {
        return add(interrupts, new Interrupt(n, in, fr, fb, mr, mb));
    }

    public Instruction newInstruction(Token n) {
        return add(instructions, new Instruction(n));
    }

    public static class GetRegister extends Operator.Op0 implements Operator.Location {

        public final Register register;

        public GetRegister(Register r) {
            super(PrimChar.TYPE);
            register = r;
        }

        public Value apply0() throws RegisterAccessError {
//	        throw new RegisterAccessError(register);
//	        System.out.println(" try to read register " + register.getName());
        	return IntptrScheduler.Instance.getRegValue(register);
        }

        public Operator getOperator() {
            return this;
        }

        public Operator setOperator() {
            return new SetRegister(register);
        }
    }

    public static class SetRegister extends Operator.Op1 {

        public final Register register;

        public SetRegister(Register r) {
            super(PrimChar.TYPE, PrimChar.TYPE);
            register = r;
        }

        public Value apply1(Value v1) throws RegisterAccessError {
            //throw new RegisterAccessError(register);
        	//System.out.println(" try to write register " + register.getName());
        	IntptrScheduler.Instance.setRegValue(register, v1);
        	return v1;
        }
    }

    public static class InstrInvoke extends Operator.Op0 {

        public final Instruction instr;

        public InstrInvoke(Instruction i) {
            super(PrimVoid.TYPE);
            instr = i;
        }

        public Value apply0() {
            throw Util.unimplemented();
        }
    }

    public static class InstrUse extends Operator.Op0 {

        public final Instruction instr;

        public InstrUse(Instruction i, Type r) {
            super(r);
            instr = i;
        }

        public Value apply0() {

            throw Util.unimplemented();
        }
    }

    public static class RegisterAccessError extends Operator.Exception {

        public RegisterAccessError(Register r) {
            super("register access is not supported at compile time");
        }
    }
}
