/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Jan 29, 2006
 */
package vpc.core.csr;

import cck.text.*;
import cck.util.Util;
import vpc.core.Value;
import vpc.core.Program;
import vpc.core.base.Callable;
import vpc.core.base.Tuple;
import vpc.core.decl.*;
import vpc.core.types.Type;
import vpc.tir.expr.Operator;
import vpc.tir.tir2c.CIR;
import vpc.util.Ovid;

import java.util.LinkedList;
import java.util.List;

/**
 * The <code>CSRFunction</code> class represents a function in the C source representation. It
 * may be the result of compiling a Virgil method in the TIR format, or it may be generated
 * as part of the linkage process. A <code>CSRFunction</code> instance contains a block of code
 * and a list of temporaries and includes the ability to print itself as (well-formatted) text
 * to be output to a .c file.
 *
 * @author Ben L. Titzer
 */
public class CSRFunction extends Method.BaseMethodRep<CIR.CStmt, CSRType> implements Printable {

    public final String name;
    public final CSRType retType;
    public final List<String> attributes;

    public final Method method;

    /**
     * This constructor for the <code>CSRFunction</code> class creates a new C function that corresponds
     * to the given source method. Using the source method's name and types, it will compute an
     * appropriate (mangled) name and (C) types.
     *
     * @param csr the program representation as C source code
     * @param m the method that this function corresponds to
     */
    public CSRFunction(CSRProgram csr, Method m) {
        method = m;
        name = getCFunctionName(m);
        retType = csr.encodeType(m.getReturnType().getType());
        attributes = Ovid.newList();
    }

    /**
     * This constructor for the <code>CSRFunction</code> class creates a new C function that
     * does not correspond to any source method, but has the specified C types and parameters.
     *
     * @param csr the program representation as C source code
     * @param n  the name of the function as a string
     * @param rt the return type of the function
     * @param p  the list of parameters to the function
     */
    public CSRFunction(CSRProgram csr, String n, CSRType rt, List<? extends Variable> p) {
        method = null;
        name = n;
        retType = rt;
        attributes = Ovid.newList();
        addParams(csr, p);
    }

    /**
     * This constructor for the <code>CSRFunction</code> class creates a new C function that
     * does not correspond to any source method, but has the specified C types and parameters.
     *
     * @param n  the name of the function as a string
     * @param rt the return type of the function
     */
    public CSRFunction(String n, CSRType rt) {
        method = null;
        name = n;
        retType = rt;
        attributes = Ovid.newList();
    }

    public void addParams(CSRProgram csr, Iterable<? extends Variable> l) {
        for ( Variable v : l )
            newParam(v.getName(), csr.encodeType(v.getType()));
    }

    public <T extends Variable> void addParams(CSRProgram csr, T... l) {
        for ( Variable v : l )
            newParam(v.getName(), csr.encodeType(v.getType()));
    }

    /**
     * The <code>print()</code> method prints this C function and its complete body to the specified
     * printer in a well-formatted (indented) way.
     *
     * @param pr the printer to which to output the code as text
     */
    public void print(Printer pr) {
        declareMethod(pr);
        pr.startblock("");
        for (Method.Temporary<CSRType> v : temps) {
            pr.print(v.getType().localDecl(v.getName()));
            pr.println(";");
        }
        body.print(pr);
        pr.endblock();
    }

    public void addAttribute(String a) {
        attributes.add(a);
    }

    public void printPrototype(Printer pr) {
        declareMethod(pr);
        pr.println(";");
    }

    private void declareMethod(Printer pr) {
        pr.print(retType.toString());
        if (!attributes.isEmpty()) {
            for (String str : attributes) {
                pr.print(" ");
                pr.print("__attribute((");
                pr.print(str);
                pr.print("))");
            }
        }

        pr.beginList(" " + name + "(");
        for (Method.Temporary<CSRType> v : params) {
            pr.print(v.getType().localDecl(v.getName()));
        }
        pr.endList(")");
    }

    public static <T extends Variable> List<T> varList(T... vs) {
        LinkedList<T> list = Ovid.newLinkedList();
        for (T v : vs) list.add(v);
        return list;
    }

    public static String getCFunctionName(Method m) {
        String name = m instanceof Constructor ? "constructor" : m.getName();
        return m.getCompoundDecl().getName() + "_" + name;
    }

    /**
     * The <code>FuncPtr</code> class represents a C type corresponding to a function
     * pointer. The function pointer contains the type of the return value and the
     * arguments.
     */
    public static class IType extends CSRType implements Callable {
        Type paramType;
        CSRType returnType;

        IType(Type pt, CSRType rt) {
            super(rt + " (*)(" + Tuple.toCommaString(pt) + ")");
            paramType = pt;
            returnType = rt;
        }

        public String localDecl(String varname) {
            return returnType + " (*" + varname + ")(" + Tuple.toCommaString(paramType) + ")";
        }

        public Type getResultType() {
            return returnType;
        }

        public Type getParameterType() {
            return paramType;
        }

        public String renderValue(boolean init, Value value) {
            return renderFuncVal(init, (CSRFunction.Val)value);
        }
        public Type rebuild(Type[] elements) {
            assert elements.length == 2;
            return new IType(elements[0], (CSRType)elements[1]);
        }

    }

    public static String renderFuncVal(boolean init, Val value) {
        if ( value == Value.BOTTOM ) return "(void *)0";
        Method m = value.method;
        if ( m == null ) return "(void *)0";
        if ( init ) return "(void *)"+ getCFunctionName(m);
        return getCFunctionName(m);
    }

    /**
     * The <code>CSRFunction.Extern</code> class represents an operator that
     * applys an external function to a list of arguments.
     */
    public static class Extern extends Operator {
        public final String name;
        public Extern(CSRType t, String n) {
            super(t);
            name = n;
        }

        public Type[] getOperandTypes() {
            throw Util.unimplemented();
        }

        public Value apply(Program.DynamicEnvironment env, Value... vals) throws Exception {
            throw Util.unimplemented();
        }

    }

    /**
     * The <code>Val</code> class represents a value that is simply a reference
     * to a function. Unlike a delegate, a reference to a function does not include
     * a bound object.
     */
    public static class Val extends CSRData.CSRValue {
        public final Method method;

        public Val(Method m, CSRType t) {
            super(t);
            method = m;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Val)) return false;
            if (o == Value.BOTTOM ) return method == null; // 0 == bottom
            Val meth = (Val) o;
            return meth.method == method;
        }

        public String toString() {
            return "&" + method.getFullName();
        }
    }
}
