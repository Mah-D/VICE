/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 21, 2007
 */

package vpc.core.types;

import cck.parser.AbstractToken;
import cck.parser.SourcePoint;
import cck.text.StringUtil;
import vpc.util.ArrayUtil;
import vpc.util.Cache;

import java.util.List;

/**
 * The <code>VirgilTypeParam</code> class represents a type parameter and related
 * functionality with the compiler.
 * 
 * @author Ben L. Titzer
 */
public class TypeParam {

    public static final TypeParam[] NOTYPEPARAMS = new TypeParam[0];

    public final String name;
    public final SourcePoint point;

    public final TypeCon typeCon;
    public final IType type;

    public TypeParam(AbstractToken tok) {
        name = tok.image;
        point = tok.getSourcePoint();
        type = new IType(tok.image);
        typeCon = new TypeCon.Singleton(type);
    }

    public static TypeParam[] toTypeParamArray(List<TypeParam> typeParams) {
        if ( typeParams == null || typeParams.isEmpty() ) return NOTYPEPARAMS;
        return typeParams.toArray(new TypeParam[typeParams.size()]);
    }

    public static <T extends TypeToken> String buildParameterizedName(String n, T[] et) {
        if ( et.length > 0 ) {
            StringBuffer buf = new StringBuffer(n);
            buf.append('<');
            StringUtil.commalist(et, buf);
            buf.append('>');
            return buf.toString();
        }
        return n;
    }

    public static <T extends TypeToken> String buildParameterizedName(String n, List<T> et) {
        if (!et.isEmpty() ) {
            StringBuffer buf = new StringBuffer(n);
            buf.append('<');
            StringUtil.commalist(et, buf);
            buf.append('>');
            return buf.toString();
        }
        return n;
    }

    public static class IType extends Type {
        public boolean inMethod;
        public int index;

        public IType(String n) {
            super(n);
            index = -1;
        }
        public Type rebuild(Type[] elements) {
            return this;
        }
    }

    public static Type substitute(Cache<Type> cache, TypeParam[] p, Type[] t, Type expr) {
        assert p.length == t.length;
        if ( p.length == 0 ) return expr; // no substitution necessary
        return ArrayUtil.substitute(cache, Type.class, toTypes(p), t, expr);
    }

    public static Type[] toTypes(TypeParam[] p) {
        if ( p.length == 0 ) return Type.NOTYPES;
        Type[] nt = new Type[p.length];
        for ( int cntr = 0; cntr < nt.length; cntr++) nt[cntr] = p[cntr].type;
        return nt;
    }

    public String getName() {
        return name;
    }

    public SourcePoint getSourcePoint() {
        return point;
    }

    public TypeCon getTypeCon() {
        return typeCon;
    }

    public IType getType() {
        return type;
    }

}
