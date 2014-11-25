/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Nov 1, 2007
 */
package vpc.core.base;

import cck.text.StringUtil;
import vpc.core.Value;
import vpc.core.Program;
import vpc.core.types.*;
import vpc.tir.expr.Operator;
import vpc.util.Cache;

import java.util.*;

/**
 * This <code>Tuple</code> contain class collects the types, type constructors,
 * values, and operators associated with tuples.
 *
 * @author Ben L. Titzer
 */
public class Tuple {
    public static final Tuple.ITypeCon TYPECON = new Tuple.ITypeCon();

    public static class ITypeCon extends TypeCon {
        public ITypeCon() {
            super("tuple", UNBOUNDED);
        }

        public Type newType(Cache<Type> cache, Type... t) {
            if (t.length == 0) return PrimVoid.TYPE;
            if (t.length == 1) return t[0];
            return cache.getCached(new IType(t));
        }

        public String render(TypeRef... p) {
            if (p.length == 0) return PrimVoid.TYPE.toString();
            if (p.length == 1) return p[0].toString();
            return buildTupleName(p);
        }
    }

    public static class IType extends Type {
        protected final Type[] elementTypes;

        IType(Type[] p) {
            super(buildTupleName(p));
            elementTypes = p;
        }

        public boolean equals(Object o) {
            return o == this || o instanceof IType && Arrays.equals(((IType) o).elementTypes, elementTypes);
        }

        public Type[] getElementTypes() {
            return elementTypes;
        }

        public Type[] elements() {
            return elementTypes;
        }

        public Type rebuild(Type[] ne) {
            return new IType(ne);
        }
    }

    public static class GetElement extends Operator.Op1 {
        public final IType tupleType;
        public final int position;

        public GetElement(IType t, int pos) {
            super(t, t.elementTypes[pos]);
            assert pos >= 0 && pos < t.elementTypes.length;
            tupleType = t;
            position = pos;
        }

        public Value apply1(Value val) {
            if (val == Value.BOTTOM) {
                return Value.BOTTOM;
            }
            return ((Val)val).values[position];
        }
    }

    public static class Create extends Operator {
        public final IType tupleType;

        public Create(IType t) {
            super(t);
            tupleType = t;
        }

        public Type[] getOperandTypes() {
            return tupleType.elementTypes;
        }

        public Value apply(Program.DynamicEnvironment env, Value... v) {
            assert v.length == tupleType.elementTypes.length;
            return new Val(tupleType, v.clone());
        }
    }

    public static <T extends TypeToken> String buildTupleName(T[] args) {
        StringBuffer buf = new StringBuffer();
        buf.append("(");
        StringUtil.commalist(args, buf);
        buf.append(")");
        return buf.toString();
    }

    /**
     * The <code>Val</code> class represents a tuple value.
     */
    public static class Val extends Value {
        public final IType type;
        public final Value[] values;

        public Val(IType t, Value... vals) {
            super();
            type = t;
            values = vals;
        }

        public Type getType() {
            return type;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == Value.BOTTOM ) {
                for (Value v : values) {
                    if (!Value.compareValues(v, Value.BOTTOM)) return false;
                }
                return true;
            }
            return o instanceof Val && Arrays.equals(values, ((Val) o).values);
        }

        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("(");
            for (int i = 0; i < values.length; i++) {
                buf.append(values[i]);
                if (i < values.length - 1) buf.append(", ");
            }
            return buf.toString();
        }
    }

    public static Type[] toParameterTypes(Type type) {
        if (type == PrimVoid.TYPE) return Type.NOTYPES;
        if (type instanceof IType) {
            return toParameterTypes0((Tuple.IType)type);
        }
        return new Type[] { type };
    }

    private static Type[] toParameterTypes0(Tuple.IType type) {
        Type[] et = type.elementTypes;
        if (et.length == 1 && et[0] == PrimVoid.TYPE) {
            // no nested types.
            return Type.NOTYPES;
        }
        return et;
    }

    public static TypeRef[] toParameterTypeRefs(TypeRef tref) {
        TypeCon typeCon = tref.getTypeCon();
        if (typeCon == PrimVoid.TYPECON) return TypeRef.NOPARAMS;
        if (typeCon == TYPECON) {
            // this is a tuple type reference.
            List<? extends TypeRef> nlist = tref.getNestedTypeRefs();
            TypeRef[] nested = nlist.toArray(new TypeRef[nlist.size()]);
            if (nested.length == 1 && nested[0].getTypeCon() == PrimVoid.TYPECON) {
                return TypeRef.NOPARAMS;
            }
            return nested;
        }
        return new TypeRef[] { tref };
    }

    public static Type toTuple(Cache<Type> cache, List<? extends Type> list) {
        return TYPECON.newType(cache, list.toArray(new Type[list.size()]));
    }

    public static Type toTuple(Cache<Type> cache, Type[] types) {
        return TYPECON.newType(cache, types);
    }

    public static String toCommaString(Type t) {
        return StringUtil.commalist(toParameterTypes(t));
    }

    public static void toCommaString(Type t, StringBuffer buf) {
        StringUtil.commalist(toParameterTypes(t), buf);
    }

    public static Type[] getParameterTypes(Callable callable) {
        return toParameterTypes(callable.getParameterType());
    }
}
