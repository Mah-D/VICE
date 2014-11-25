/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Nov 1, 2007
 */
package vpc.core.base;

import vpc.core.types.*;
import vpc.util.Cache;

/**
 * The <code>Function</code> class encapsulates concepts related to functions,
 * including typenames for functions, type constructors, and actual types. It
 * also contains an implementation of <code>Value</code> that represents
 * a reference to a method.
 *
 * @author Ben L. Titzer
 */
public class Association {
    public static final Association.ITypeCon TYPECON = new Association.ITypeCon();

    public static class ITypeCon extends TypeCon {
        public ITypeCon() {
            super("association", 2);
        }

        public Association.IType newType(Cache<Type> cache, Type... t) {
            assert t.length == 2;
            return cache.getCached(new IType(t[0], t[1]));
        }

        public String render(TypeRef... p) {
            assert p.length == 2;
            return buildAssociationName(p[0], p[1]);
        }
    }

    public static class IType extends Type implements Callable {
        protected final Type resultType;
        protected final Type parameterType;

        public IType(Type pt, Type rt) {
            super(buildAssociationName(pt, rt));
            parameterType = pt;
            resultType = rt;
        }

        public boolean equals(Object o) {
            if ( o == this ) return true;
            if (o instanceof IType) {
                IType ot = (IType) o;
                return resultType.equals(ot.resultType) && parameterType.equals(ot.parameterType);
            }
            return false;
        }

        public Type getResultType() {
            return resultType;
        }

        public Type getParameterType() {
            return parameterType;
        }

        public Type[] elements() {
            return new Type[] {parameterType, resultType };
        }

        public Type rebuild(Type[] ne) {
            assert ne.length == 2;
            return new IType(ne[0], ne[1]);
        }

    }

    public static <T extends TypeToken> String buildAssociationName(T parType, T retType) {
        StringBuffer buf = new StringBuffer();
        buf.append(parType);
        buf.append(" => ");
        buf.append(retType);
        return buf.toString();
    }

    private static Association.IType newAssociationType(Type... t) {
        assert t.length == 2;
        return new Association.IType(t[0], t[1]);
    }
}
