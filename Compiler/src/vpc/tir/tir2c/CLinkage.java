/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 17, 2006
 */

package vpc.tir.tir2c;

import vpc.core.Program;
import vpc.core.ProgramDecl;
import vpc.core.base.PrimChar;
import vpc.core.csr.*;
import vpc.core.types.Type;
import vpc.core.virgil.VirgilArray;
import vpc.core.virgil.VirgilError;
import vpc.hil.Device;

/**
 * The <code>CLinkage</code> class represents a linkage model for generating C code
 * as output of the Virgil compiler. The linkage model defines how entrypoints to
 * the Virgil program are connected with C glue code. For example, what version of the
 * \"main\" C function should be generated, how interrupts should be attached to
 * entrypoints, etc.
 *
 * @author Ben L. Titzer
 */
public abstract class CLinkage extends CSRGen {

    public void generateLinkage(Program p) {
        if ( p.programDecl == null ) throw VirgilError.MissingProgramDecl(p);
        addFunction(p.csr, newMain(p));
        addFunction(p.csr, newThrowFunc(p.csr));
    }

    protected void addFunction(CSRProgram csr, CSRFunction cf) {
        csr.functions.add(cf);
    }

    protected CSRFunction newThrowFunc(CSRProgram csr) {
        CSRFunction func = new CSRFunction("throw", CSRProgram.INT32);
        func.addParams(csr, INTVAR("code"), INTVAR("loc"));
        addThrowFuncImpl(func);
        return func;
    }

    protected void addStringParams(CSRProgram csr, CSRFunction cf) {
        cf.addParams(csr, INTVAR("argc"), VAR("argv", ptr(csr, ptr(csr, CSRProgram.CHAR))));
    }

    protected abstract CSRFunction newMain(Program p);

    protected abstract void addThrowFuncImpl(CSRFunction func);

    /**
     * The <code>CTestLinkage</code> class represents a linkage model for the built-in
     * testing framework. This linkage model generates a C "main" function that converts
     * its first argument to an integer and then passes it to the main method declared
     * in the test case. The return value of the program's entrypoint point is then
     * returned by the main, making it the exit code of the process.
     */
    public static class CTestLinkage extends CLinkage {

        protected CSRFunction newMain(Program p) {
            CSRFunction cf = new CSRFunction("main", CSRProgram.INT32);
            addStringParams(p.csr, cf);
            String entry = CSRFunction.getCFunctionName(p.programDecl.mainEntry.method);
            cf.setBody(new CIR.CSingle("return " + entry + "(" + CSRProgram.NULL + ", atoi(argv[1]))"));
            return cf;
        }

        protected void addThrowFuncImpl(CSRFunction func) {
            func.setBody(STMT("exit(code)"));
        }

    }

    /**
     * The <code>CAVRLinkage</code> class represents a linkage model for the AVR
     * microcontroller. This linkage model defines main with no arguments and generates
     * the appropriate vector declarations that connect the declared interrupt entrypoints
     * in the program to the actual interrupt handlers.
     */
    public static class CAVRLinkage extends CLinkage {

        public static final int STATUS_ADDR = 0x91;
        public static final int IO_ADDR = 0x92;

        public void generateLinkage(Program p) {
            super.generateLinkage(p);
            if (p.targetDevice != null) generateHandlers(p);
        }

        protected CSRFunction newMain(Program p) {
            CSRFunction cf = new CSRFunction(p.csr, "main", CSRProgram.INT32, CSRFunction.varList());
            String entry = CSRFunction.getCFunctionName(p.programDecl.mainEntry.method);
            cf.setBody(new CIR.CSingle(entry + "(" + CSRProgram.NULL + "); asm (\"break\")"));
            return cf;
        }

        private void generateHandlers(Program p) {
            for (ProgramDecl.EntryPoint pt : p.programDecl.entryPoints) {
                if ( pt == p.programDecl.mainEntry ) continue;
                Device.Interrupt i = getInterruptNumber(pt, p);
                CSRFunction cf = new CSRFunction(p.csr, "__vector_" + i.number.image, CSRProgram.VOID, CSRFunction.varList());
                cf.addAttribute("signal");
                String entry = CSRFunction.getCFunctionName(pt.method);
                cf.setBody(STMT(entry + "(" + CSRProgram.NULL + ")"));
                p.csr.functions.add(cf);
            }
        }

        private Device.Interrupt getInterruptNumber(ProgramDecl.EntryPoint pt, Program p) {
            Device.Interrupt i = p.targetDevice.interrupts.get(pt.getName());
            if (i == null)
                throw VirgilError.UnresolvedEntrypoint.gen(pt.getSourcePoint(), pt.getName());
            return i;
        }

        protected void addThrowFuncImpl(CSRFunction cf) {
            String staddr = "*(char*)"+ STATUS_ADDR;
            String ioaddr = "*(int*)"+ IO_ADDR;
            cf.setBody(NEST(
                    STMT(staddr+" = (char)code"),
                    STMT(ioaddr+" = loc"),
                    STMT("asm(\"break\")")));
        }
    }

    /**
     * The <code>CAvroraTestLinkage</code> class represents a linkage model for the AVR
     * microcontroller using the avrora simulator as a test harness.
     */
    public static class CAvroraTestLinkage extends CLinkage {

        protected CSRFunction newMain(Program p) {
            CSRFunction cf = new CSRFunction(p.csr, "main", CSRProgram.INT32, CSRFunction.varList());
            String entry = CSRFunction.getCFunctionName(p.programDecl.mainEntry.method);
            String staddr = "*(char*)"+ CAVRLinkage.STATUS_ADDR;
            String ioaddr = "*(int*)" + CAVRLinkage.IO_ADDR;
            cf.setBody(NEST(
                    STMT(staddr+" = 0"),
                    STMT(ioaddr+" = "+ entry + "(" + CSRProgram.NULL + ", "+ioaddr+")"),
                    STMT("asm(\"break\")")));
            return cf;
        }

        protected void addThrowFuncImpl(CSRFunction cf) {
            String staddr = "*(char*)"+ CAVRLinkage.STATUS_ADDR;
            String ioaddr = "*(int*)"+ CAVRLinkage.IO_ADDR;
            cf.setBody(NEST(
                    STMT(staddr+" = (char)code"),
                    STMT(ioaddr+" = loc"),
                    STMT("asm(\"break\")")));
        }
    }

    /**
     * The <code>CUserLinkage</code> class represents a linkage model for generating
     * user-space C programs that run as a native process. It generates a main method
     * that converts the arguments into the character array representation used by
     * the Virgil language implementation.
     */
    public static class CUserLinkage extends CLinkage {
        protected CSRFunction newMain(Program p) {
            CSRFunction cf = new CSRFunction("main", CSRProgram.INT32);
            addStringParams(p.csr, cf);
            String entry = CSRFunction.getCFunctionName(p.programDecl.mainEntry.method);
            CSRPointer.IType charArrayType = getArrayType(p, PrimChar.TYPE);
            CSRPointer.IType ptrArrayType = getArrayType(p, getArrayType(p, PrimChar.TYPE));
            cf.setBody(NEST(
                    DECLARE(cf, "cntr", CSRProgram.INT32, EXPR("1")),
                    DECLARE(cf, "aa", ptrArrayType,
                            EXPR("("+ptrArrayType+")malloc(sizeof("+ptrArrayType.ptype+")+argc*sizeof(void*))")),
                    ASSIGN("aa->length", "argc-1"),
                    FOR(null,
                            EXPR("cntr < argc"),
                            EXPR("cntr++"),
                            NEST(DECLARE(cf, "len", CSRProgram.INT32,
                                    EXPR("strlen(argv[cntr])")),
                                    DECLARE(cf, "buf", charArrayType,
                                            EXPR("("+charArrayType+")malloc(sizeof("+charArrayType.ptype+")+len+1)")),
                                    ASSIGN("buf->length", "len"),
                                    STMT("strcpy(buf->values, argv[cntr])"),
                                    ASSIGN("aa->values[cntr-1]", "buf"))),
                    RETURN(CALL(entry,
                            EXPR(CSRProgram.NULL),
                            EXPR("aa")))));
            return cf;
        }

        private CSRPointer.IType getArrayType(Program p, Type et) {
            return (CSRPointer.IType)p.csr.encodeType(VirgilArray.getArrayType(p.typeCache, et));
        }

        protected void addThrowFuncImpl(CSRFunction func) {
            func.setBody(STMT("exit(code)"));
        }

    }

    private static CSRPointer.IType ptr(CSRProgram csr, CSRType type) {
        return CSRType.newPointer(csr, type);
    }

}
