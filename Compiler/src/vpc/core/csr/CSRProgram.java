/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 30, 2007
 */

package vpc.core.csr;

import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.*;
import vpc.core.base.*;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.core.virgil.*;
import vpc.core.virgil.model.ObjectModel;
import vpc.util.Ovid;

import java.util.*;

/**
 * The <code>CSRProgram</code> class implements the functionality and data structures that
 * are global to the CSR (C source representation) of the program.
 *
 * @author Ben L. Titzer
 */
public class CSRProgram {

    public static int ABORT_TYPE_CODE = 127;
    public static int ABORT_NULL_CODE = 126;
    public static int ABORT_BOUNDS_CODE = 125;
    public static int ABORT_DIV_CODE = 124;
    public static int ABORT_ALLOC_CODE = 123;
    public static int ABORT_UNIMP_CODE = 122;

    private static Map<Class, Integer> exceptionCodes = Ovid.newMap();
    private static Map<String, Integer> stringCodes = Ovid.newMap();

    static {
        addExceptionCode(VirgilClass.TypeCheckException.class, ABORT_TYPE_CODE);
        addExceptionCode(Reference.NullCheckException.class, ABORT_NULL_CODE);
        addExceptionCode(VirgilArray.BoundsCheckException.class, ABORT_BOUNDS_CODE);
        addExceptionCode(PrimInt32.DivideByZeroException.class, ABORT_DIV_CODE);
        addExceptionCode(Heap.AllocationException.class, ABORT_ALLOC_CODE);
        addExceptionCode(Method.UnimplementedException.class, ABORT_UNIMP_CODE);
    }

    private static void addExceptionCode(Class c, int val) {
        exceptionCodes.put(c, val);
        stringCodes.put(StringUtil.getShortName(c), val);
    }

    public static final String NULL = "(void*)0";
    public static final CSRType.Simple UINT64 = new CSRType.Integral("unsigned long long");
    public static final CSRType.Simple UINT32 = new CSRType.Integral("unsigned long");
    public static final CSRType.Simple UINT16 = new CSRType.Integral("unsigned short");
    public static final CSRType.Simple UINT8 = new CSRType.Integral("unsigned char");
    public static final CSRType.Simple INT32 = new CSRType.Integral("long");
    public static final CSRType.Simple INT16 = new CSRType.Integral("short");
    public static final CSRType.Simple INT8 = new CSRType.Integral("char");
    public static final CSRType.Simple CHAR = new CSRType.Integral("char");
    public static final CSRType.Void VOID = new CSRType.Void();
    public static final CSRPointer.VoidPtr VOIDPTR = new CSRPointer.VoidPtr();
    public static final CSRType.Simple BOOL = CHAR;

    public final Program program;
    public final LinkedList<CSRStruct.IType> structs = Ovid.newLinkedList();
    public final List<CSRFunction> functions = Ovid.newList();
    public final List<String> includes = Ovid.newList();
    public final List<CSRData.Global> globals = Ovid.newList();
    protected final Map<Type, CSRStruct.IType> tupleMap = Ovid.newMap();

    public ObjectModel model;

    protected final Map<String, CSRType> types = Ovid.newMap();

    public CSRProgram(Program p) {
        program = p;
    }

    public CSRStruct.IType newStruct(String name) {
        return getCachedType(new CSRStruct.IType(name));
    }

    public <T extends CSRType> T getCachedType(T t) {
        CSRType ct = types.get(t.typename);
        if ( ct != null ) {
            return (T)ct;
        } else {
            if ( t instanceof CSRStruct.IType )
                structs.add((CSRStruct.IType)t);
            types.put(t.typename, t);
            return t;
        }
    }

    public CSRType getPrimitiveType(Type t) {
        if (t == Reference.NULL_TYPE) return VOIDPTR;
        if (t == PrimVoid.TYPE) return VOID;
        if (t == PrimInt32.TYPE) return INT32;
        if (t == PrimChar.TYPE) return CHAR;
        if (t == PrimBool.TYPE) return CHAR;
        if ( t instanceof PrimRaw.IType ) {
            PrimRaw.IType rt = (PrimRaw.IType)t;
            return CSRBitField.getBitType(rt.width);
        }
        return null;
    }

    public void makeSafetyChecks() {
        makeZeroCheck();
    }

    public CSRData.Global newGlobal(CSRData.Space s, String name, CSRType t, Value v) {
        CSRData.Global i = new CSRData.Global(s, name, t);
        i.value = v;
        globals.add(i);
        return i;
    }

    void makeZeroCheck() {
        CSRFunction nc = new CSRFunction("zero_check", INT32);
        nc.addParams(this, CSRGen.INTVAR("val"), CSRGen.INTVAR("loc"));
        nc.setBody(CSRGen.IF(CSRGen.EXPR("val == 0"),
                CSRGen.STMT("throw("+ ABORT_DIV_CODE +", loc)"),
                CSRGen.RETURN(CSRGen.EXPR("val"))));
        functions.add(nc);
    }

    public CSRType encodeType(Type t) {
        if ( t instanceof CSRType ) return (CSRType)t;
        CSRType ct = getPrimitiveType(t);
        if ( ct != null ) return ct;
        if ( V2TypeSystem.isClass(t) ) return model.encodeClassType((VirgilClass.IType)t);
        if ( V2TypeSystem.isComponent(t) ) return model.encodeComponentType((VirgilComponent.IType)t);
        if ( V2TypeSystem.isFunction(t) ) return model.encodeDelegateType((Function.IType)t);
        if ( V2TypeSystem.isArray(t) ) return model.encodeArrayType((VirgilArray.IType)t);
        if ( V2TypeSystem.isTuple(t) ) {
            return encodeTupleType(t);
        }
        throw Util.failure("unknown encoding for type: "+t);
    }

    private CSRStruct.IType encodeTupleType(Type t) {
        CSRStruct.IType stype = tupleMap.get(t);
        if (stype == null) {
            stype = new CSRStruct.IType("tuple_"+tupleMap.size());
            structs.add(stype);
            tupleMap.put(t, stype);
            int cntr = 0;
            for (Type et : t.elements()) {
                stype.addField("f" + (cntr++), encodeType(et));
            }
        }
        return stype;
    }

    public Value encodeValue(Value v, Type t) {
        if ( V2TypeSystem.isClass(t) ) return model.encodeObjectValue(v, (VirgilClass.IType) t);
        if ( V2TypeSystem.isComponent(t) ) return model.encodeComponentValue(v, (VirgilComponent.IType) t);
        if ( V2TypeSystem.isFunction(t) ) return model.encodeDelegateValue(v, (Function.IType) t);
        if ( V2TypeSystem.isArray(t) ) return model.encodeArrayValue(v, (VirgilArray.IType) t);
        if ( V2TypeSystem.isTuple(t) ) {
            if (v == Value.BOTTOM) return v;
            CSRStruct.IType stype = encodeTupleType(t);
            Tuple.Val val = (Tuple.Val)v;
            Value[] tvals = new Value[val.values.length];
            Type[] types = t.elements();
            for (int i = 0; i < val.values.length; i++) {
                tvals[i] = encodeValue(val.values[i], types[i]);
            }
            return new CSRStruct.Val(stype, null, tvals);
        }
        return v;
    }

    public static int getExceptionCode(Class c) {
        return exceptionCodes.get(c);
    }

    public static int getExceptionCode(String s) {
        return stringCodes.get(s);
    }
}
