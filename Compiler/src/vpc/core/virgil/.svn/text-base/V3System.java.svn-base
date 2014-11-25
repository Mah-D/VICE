/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Dec 2, 2007
 */
package vpc.core.virgil;

import cck.parser.AbstractToken;
import cck.parser.SourcePoint;
import cck.util.Util;
import vpc.core.*;
import vpc.core.decl.Constructor;
import vpc.core.base.*;
import vpc.core.types.*;
import vpc.tir.*;
import vpc.tir.expr.Operator;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The <code>V3System</code> class contains system routines that allow the interpreted
 * program to access the file system, terminal, etc.
 *
 * @author Ben L. Titzer
 */
public class V3System {

    private static final int MAX_FILES = 128;

    public final Program program;

    public final File[] files;
    public final FileInputStream[] fileInput;
    public final FileOutputStream[] fileOutput;
    public final VirgilComponent component;
    public final Type stringType;

    public V3System(Program p) {
        this.program = p;
        files = new File[MAX_FILES];
        fileInput = new FileInputStream[MAX_FILES];
        fileOutput = new FileOutputStream[MAX_FILES];

        stringType = program.typeEnv.resolveTypeCon("string").newType(program.typeCache);

        component = new VirgilComponent(newToken("System"));

        addConstructor();

        addMethod("fileOpen");
        addMethod("fileClose");
        addMethod("fileRead");
        addMethod("fileWrite");
        addMethod("fileLeft");
        addMethod("fileLoad");
        addMethod("putc");
        addMethod("puti");
        addMethod("puts");
        addMethod("error");
    }

    private void addConstructor() {
        Constructor c = component.newConstructor(TypeRef.refOf(Function.TYPECON, Function.voidFunc(program.typeCache)));
        TIRRep trep = new TIRRep();
        trep.setBody(TIRUtil.$VAL(PrimVoid.VALUE));
        c.addMethodRep(TIRRep.REP_NAME, trep);
    }

    private AbstractToken newToken(String string) {
        return AbstractToken.newToken(string, new SourcePoint("System.v3", 1, 1, 1, 1));
    }

    protected void addMethod(String mname) {
        try {
            Method javaMethod = findMethod(mname);
            Type[] vtypes = getVirgilTypes(javaMethod);
            Type rtype = toVirgil(javaMethod.getReturnType());
            JavaOperator op = new JavaOperator(rtype, this, vtypes, javaMethod);
            buildMethod(mname, vtypes, rtype, op);
        } catch (NoSuchMethodException e) {
            // do nothing.
        }
    }

    private Method findMethod(String mname) throws NoSuchMethodException {
        for (Method aM : V3System.class.getMethods()) {
            if (aM.getName().equals(mname)) return aM;
        }
        throw new NoSuchMethodException(mname);
    }

    private void buildMethod(String mname, Type[] vtypes, Type rtype, JavaOperator op) {
        vpc.core.decl.Method virgilMethod = newMethod(mname, vtypes, rtype);
        TIRRep trep = new TIRRep();
        vpc.core.decl.Method.Temporary[] temps = new vpc.core.decl.Method.Temporary[vtypes.length];
        // add all the parameters
        trep.newParam("this", component.getCanonicalType());
        for (int i = 0; i < vtypes.length; i++) {
            temps[i] = trep.newParam("p"+i, vtypes[i]);
        }
        // build a get expression for each parameter
        TIRExpr[] oper = new TIRExpr[vtypes.length];
        for (int i = 0; i < temps.length; i++) {
            oper[i] = TIRUtil.$GET(temps[i]);
        }
        // build the actual operator
        TIRExpr te = TIRUtil.$OP(op, oper);
        trep.setBody(new TIRReturn(te));
        virgilMethod.addMethodRep(TIRRep.REP_NAME, trep);
    }

    private Type[] getVirgilTypes(Method method) {
        Class[] jtypes = method.getParameterTypes();
        Type[] vtypes = new Type[jtypes.length];
        for (int i = 0; i < jtypes.length; i++) {
            vtypes[i] = toVirgil(jtypes[i]);
        }
        return vtypes;
    }

    private vpc.core.decl.Method newMethod(String mname, Type[] vtypes, Type rtype) {
        Type ptype = Tuple.toTuple(program.typeCache, vtypes);
        Type ftype = Function.TYPECON.newType(program.typeCache, ptype, rtype);
        return component.newMethod(false, newToken(mname), TypeRef.refOf(Function.TYPECON, ftype), TypeParam.NOTYPEPARAMS);
    }

    public static class JavaOperator extends Operator {
        public final V3System system;
        public final Type[] operands;
        public final Method method;

        protected JavaOperator(Type result, V3System system, Type[] operands, Method method) {
            super(result);
            this.system = system;
            this.operands = operands;
            this.method = method;
        }

        public Type[] getOperandTypes() {
            return operands;
        }

        public Value apply(Program.DynamicEnvironment env, Value... vals) throws Exception {
            Object[] objs = new Object[vals.length];
            for (int i = 0; i < vals.length; i++) {
                objs[i] = system.toJava(vals[i], operands[i]);
            }
            try {
                return system.toVirgil(method.invoke(system, objs));
            } catch (IllegalAccessException e) {
                throw Util.unexpected(e);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof Operator.Exception) {
                    throw ((Operator.Exception) e.getCause());
                }
                throw Util.unexpected(e.getTargetException());
            }
        }
    }

    private Class toJava(Type t) {
        if (t == PrimInt32.TYPE) {
            return int.class;
        }
        if (t == PrimChar.TYPE) {
            return char.class;
        }
        if (t == PrimBool.TYPE) {
            return boolean.class;
        }
        if (t == stringType) {
            return byte[].class;
        }
        if (t == PrimVoid.TYPE) {
            return void.class;
        }
        throw Util.failure("unknown virgil type " + t);
    }

    private Object toJava(Value val, Type t) {
        if (t == PrimChar.TYPE) {
            return PrimChar.fromValue(val);
        }
        if (t == PrimInt32.TYPE) {
            return PrimInt32.fromValue(val);
        }
        if (t == stringType) {
            return TIRInterpreter.toByteArray(val);
        }
        if (t == PrimBool.TYPE) {
            return PrimBool.fromValue(val);
        }
        throw Util.failure("unknown virgil type " + t);
    }

    private Value toVirgil(Object obj) {
        if (obj == null) {
            return Value.BOTTOM;
        }
        if (obj instanceof Character) {
            return PrimChar.toValue(((Character)obj).charValue());
        }
        if (obj instanceof Integer) {
            return PrimInt32.toValue(((Integer)obj).intValue());
        }
        if (obj instanceof byte[]) {
            return TIRInterpreter.fromByteArray(program, (byte[]) obj);
        }
        if (obj instanceof Boolean) {
            return PrimBool.toValue(((Boolean)obj).booleanValue());
        }
        throw Util.failure("unknown java object " + obj);
    }

    private Type toVirgil(Class cl) {
        if (cl == int.class) {
            return PrimInt32.TYPE;
        }
        if (cl == char.class) {
            return PrimChar.TYPE;
        }
        if (cl == byte[].class) {
            return stringType;
        }
        if (cl == boolean.class) {
            return PrimBool.TYPE;
        }
        if (cl == void.class) {
            return PrimVoid.TYPE;
        }
        throw Util.failure("unknown java type " + cl);
    }

    public void putc(char ch) {
        System.out.print(ch);
    }

    public void puts(byte[] str) {
        try {
            System.out.write(str);
        } catch (IOException e) {
            // ignore exceptions
        }
    }

    public void puti(int i) {
        System.out.print(i);
    }

    public char fileRead(int fd) throws IOException {
        if (files[fd] != null && fileInput[fd] != null) {
            return (char)fileInput[fd].read();
        }
        return 0;
    }

    public void fileWrite(int fd, char ch) throws IOException {
        if (files[fd] != null && fileOutput[fd] != null) {
            fileOutput[fd].write(ch);
        }
    }

    public int fileOpen(byte[] name, boolean input) {
        for (int i = 0; i < files.length; i++) {
            if (files[i] == null) {
                File file = new File(new String(name));
                files[i] = file;
                try {
                    if (input) {
                        fileInput[i] = new FileInputStream(file);
                    } else {
                        fileOutput[i] = new FileOutputStream(file);
                    }
                } catch (FileNotFoundException e) {
                    files[i] = null;
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }

    public int fileLeft(int fd) throws IOException {
        if (files[fd] != null && fileInput[fd] != null) {
            return fileInput[fd].available();
        }
        return 0;
    }

    public void fileClose(int fd) {
        if (files[fd] != null) {
            try {
                if (fileInput[fd] != null) {
                    fileInput[fd].close();
                }
                if (fileOutput[fd] != null) {
                    fileOutput[fd].close();
                }
                files[fd] = null;
                fileInput[fd] = null;
                fileOutput[fd] = null;
            } catch (IOException e) {
                throw Util.unexpected(e);
            }
        }
    }

    public byte[] fileLoad(byte[] fname) {
        try {
            File file = new File(new String(fname));
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            int pos = 0;
            while (pos < buffer.length) {
                pos += fis.read(buffer, pos, buffer.length - pos);
            }
            return buffer;
        } catch (IOException e) {
            return null;
        }
    }

    public int error(byte[] type, byte[] message) throws Operator.Exception {
        throw new Operator.Exception(new String(type), new String(message));
    }
}
