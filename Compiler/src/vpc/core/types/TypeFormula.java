/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Mar 3, 2007
 */
package vpc.core.types;

import vpc.util.Cache;

/**
 * A <code>TypeFormula</code> represents a type that may not be fully resolved,
 * but requires a dynamic type environment to be supplied. An element of a type
 * formula might be a fully resolved type or a type that has nested free
 * type parameters.
 *
 * @author Ben L. Titzer
 */
public abstract class TypeFormula<T extends Type> {

    public interface TypeEnv {
        public Type getMethodTypeParam(TypeParam.IType tp);
        public Type getClassTypeParam(TypeParam.IType tp);
        public Cache<Type> getTypeCache();
    }


    public static final TypeFormula[] NOTYPES = new TypeFormula[0];

    public abstract T instantiate(TypeEnv env);

    public static class Param<U extends Type> extends TypeFormula<U> {
        public final TypeParam.IType typeParam;

        protected Param(TypeParam.IType t) {
            typeParam = t;
        }
        public U instantiate(TypeEnv env) {
            if ( typeParam.inMethod ) return (U)env.getMethodTypeParam(typeParam);
            else return (U)env.getClassTypeParam(typeParam);
        }

    }

    public static class Mono<U extends Type> extends TypeFormula<U> {
        public final U type;
        protected Mono(U t) {
            type = t;
        }
        public U instantiate(TypeEnv env) {
            return type;
        }
    }

    public static class Open<U extends Type> extends TypeFormula<U> {
        public final U typeCon;
        public final TypeFormula[] elem;
        public Open(U t, TypeFormula[] ef) {
            typeCon = t;
            elem = ef;
        }
        public U instantiate(TypeEnv env) {
            Type[] nt = new Type[elem.length];
            for ( int cntr = 0; cntr < nt.length; cntr++ ) {
                nt[cntr] = elem[cntr].instantiate(env);
            }
            Cache<Type> cache = env.getTypeCache();
            return (U)cache.getCached(typeCon.rebuild(nt));
        }
    }

    public static <U extends Type> TypeFormula<U> newFormula(U t) {
        // if this is a type parameter, return a type param
        if ( t instanceof TypeVar ) {
            U binding = (U)((TypeVar) t).binding;
            return newFormula(binding);
        }
        // if this is a type parameter, return a type param
        if ( t instanceof TypeParam.IType ) {
            return new Param<U>((TypeParam.IType)t);
        }
        // is this a type with no nested types?
        Type[] et = t.elements();
        if ( et.length == 0 ) return new Mono<U>(t);

        // build a formula for a polymorphic type
        boolean poly = false;
        TypeFormula[] tf = new TypeFormula[et.length];
        for ( int cntr = 0; cntr < et.length; cntr++ ) {
            TypeFormula f = newFormula(et[cntr]);
            if ( !(f instanceof Mono) ) poly = true;
            tf[cntr] = f;
        }
        if ( poly ) return new Open<U>(t, tf);
        else return new Mono<U>(t);
    }

    public static <U extends Type> TypeFormula<U> mono(U t) {
        return new Mono<U>(t);
    }

    public static Type[] instantiate(TypeEnv env, TypeFormula[] tf) {
        if ( tf.length == 0 ) return Type.NOTYPES;
        Type[] result = new Type[tf.length];
        for ( int cntr = 0; cntr < tf.length; cntr++ ) {
            result[cntr] = tf[cntr].instantiate(env);
        }
        return result;
    }

    public static TypeFormula[] newFormula(Type[] tv) {
        TypeFormula[] tf = new TypeFormula[tv.length];
        for (int cntr = 0; cntr < tv.length; cntr++)
            tf[cntr] = newFormula(tv[cntr]);
        return tf;
    }

}
