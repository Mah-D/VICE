/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Mar 25, 2006
 */
package vpc.core.base;

import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.Value;
import vpc.core.decl.Method;
import vpc.core.types.*;
import vpc.util.Cache;

import java.util.List;

/**
 * The <code>Function</code> class encapsulates concepts related to functions,
 * including typenames for functions, type constructors, and actual types. It
 * also contains an implementation of <code>Value</code> that represents
 * a reference to a method.
 *
 * @author Ben L. Titzer
 */
public class Function {
    public static final ITypeCon TYPECON = new ITypeCon();

    public static class ITypeCon extends TypeCon {
        public ITypeCon() {
            super("function", UNBOUNDED);
        }

        public IType newType(Cache<Type> cache, Type... t) {
            assert t.length == 2;
            return cache.getCached(new IType(t[0], t[1]));
        }

        public String render(TypeRef... p) {
            assert p.length == 2;
            return buildCallableName("function", Tuple.toParameterTypeRefs(p[0]), p[1]);
        }
    }

    public static class IType extends Type implements Callable {
        protected final Type returnType;
        protected final Type parameterType;

        public IType(Type pt, Type rt) {
            super(buildFuncName(pt, rt));
            returnType = rt;
            parameterType = pt;
        }

        public boolean equals(Object o) {
            if ( o == this ) return true;
            if ( !(o instanceof IType) ) return false;
            IType ot = (IType)o;
            return returnType.equals(ot.returnType) && parameterType.equals(ot.parameterType);
        }

        public Type getResultType() {
            return returnType;
        }

        public Type getParameterType() {
            return parameterType;
        }

        public Type[] elements() {
            return new Type[] { parameterType, returnType };
        }

        public Type rebuild(Type[] ne) {
            assert ne.length == 2;
            return new IType(ne[0], ne[1]);
        }

    }

    public static String buildFuncName(Type pt, Type rt) {
        String result;
        StringBuffer buf = new StringBuffer("function");
        buf.append("(");
        Tuple.toCommaString(pt, buf);
        buf.append(")");
        appendReturnType(buf, rt);
        result = buf.toString();
        return result;
    }

    public static <T extends TypeToken> String buildCallableName(String tycon, T[] args, T rt) {
        StringBuffer buf = new StringBuffer(tycon);
        buf.append("(");
        StringUtil.commalist(args, buf);
        buf.append(")");
        appendReturnType(buf, rt);
        return buf.toString();
    }

    public static TypeRef getReturnType(TypeRef tref) {
        List<? extends TypeRef> list = checkFuncTypeRef(tref);
        return list.get(list.size() - 1);
    }

    public static TypeRef[] getParameterTypes(TypeRef tref) {
        List<? extends TypeRef> list = checkFuncTypeRef(tref);
        assert list.size() == 2;
        return Tuple.toParameterTypeRefs(list.get(0));
    }

    private static List<? extends TypeRef> checkFuncTypeRef(TypeRef tref) {
        assert tref.getTypeCon() == TYPECON;
        return tref.getNestedTypeRefs();
    }

    public static void appendReturnType(StringBuffer buf, TypeToken t) {
        if (t != null && !PrimVoid.isVoid(t)) {
            buf.append(": ");
            buf.append(t);
        }
    }

    public static String asReturnType(TypeToken t) {
        if (t != null && !PrimVoid.isVoid(t)) {
            return ": "+ t;
        }
        return "";
    }

    public static Method fromValue(Value v) {
        if ( v == Value.BOTTOM ) return null;
        if ( v instanceof Val ) return ((Val)v).method;
        throw Util.failure("cannot convert " + v + " to Function value");
    }

    /**
     * The <code>Val</code> class represents a value that is simply a reference
     * to a function. Unlike a delegate, a reference to a function does not include
     * a bound object.
     */
    public static class Val extends Value {
        public final Method method;

        public Val(Method m) {
            super();
            method = m;
        }

        public Type getType() {
            return method.getType();
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

    public static IType voidFunc(Cache<Type> cache) {
        return cache.getCached(new IType(PrimVoid.TYPE, PrimVoid.TYPE));
    }
}
