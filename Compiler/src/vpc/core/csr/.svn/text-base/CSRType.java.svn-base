/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 26, 2006
 */

package vpc.core.csr;

import cck.util.Util;
import vpc.core.Value;
import vpc.core.base.*;
import vpc.core.types.Type;
import vpc.tir.expr.Operator;

/**
 * @author Ben L. Titzer
 */
public abstract class CSRType extends Type {

    protected String typename;


    protected CSRType(String tn) {
        super(tn);
        typename = tn;
    }

    public String toString() {
        return typename;
    }

    public String renderValue(boolean init, Value v) {
        throw Util.failure("cannot render value: "+v);
    }

    /**
     * The <code>Unit</code> class represents a C type that corresponds to a primitive,
     * or unit, type. Such types include <code>int</code>, <code>char</code>, etc.
     */
    public static class Simple extends CSRType {
        public Simple(String n) {
            super(n);
        }
        public Type rebuild(Type[] elements) {
            return this;
        }
    }

    /**
     * The <code>Integral</code> class represents a C type that corresponds to a integral.
     * Such types include <code>int</code>, <code>char</code>, unsigned char, etc.
     */
    public static class Integral extends Simple {
        public Integral(String n) {
            super(n);
        }
        public String renderValue(boolean init, Value value) {
            return renderIntegral(value);
        }
    }

    protected static String renderIntegral(Value value) {
        if ( value == Value.BOTTOM ) return "0";
        if ( value instanceof PrimRaw.Val ) return Long.toString(PrimRaw.fromValue(value));
        if ( value instanceof PrimInt32.Val ) return Integer.toString(PrimInt32.fromValue(value));
        if ( value instanceof PrimChar.Val ) return Integer.toString(PrimChar.fromValue(value));
        if ( value instanceof PrimBool.Val ) return PrimBool.fromValue(value) ? "1" : "0";
        throw Util.failure("unknown value type: "+ value.getType() +" of class "+value.getClass());
    }

    /**
     * The <code>Void</code> class represents the C type <code>void</code>, which
     * is used only as the return types for procedures that do not return a value.
     */
    public static class Void extends CSRType.Simple {
        public Void() {
            super("void");
        }
        public Type rebuild(Type[] elements) {
            return this;
        }
    }

    public String varDecl(String varname) {
        return toString() + " " + varname;
    }

    public String localDecl(String varname) {
        return toString() + " " + varname;
    }

    public String globalDecl(String varname) {
        return localDecl(varname);
    }

    public String fieldDecl(String varname) {
        return localDecl(varname);
    }

    public static CSRArray.IType newArray(CSRProgram csr, CSRType inner) {
        return csr.getCachedType(new CSRArray.IType(inner));
    }

    public static CSRPointer.IType newPointer(CSRProgram csr, CSRType pt) {
        return csr.getCachedType(new CSRPointer.IType(pt));
    }

    public static CSRFunction.IType newFuncPtr(CSRProgram csr, Type pt, CSRType rt) {
        return csr.getCachedType(new CSRFunction.IType(pt, rt));
    }

    public static class Coerce extends Operator.Op1 {

        public Coerce(CSRType a, CSRType b) {
            super(a, b);
        }

        public Value apply1(Value v1) throws Exception {
            throw Util.unimplemented();
        }
    }
}
