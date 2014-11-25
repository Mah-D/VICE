/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Sep 23, 2006
 */
package vpc.core.virgil;

import cck.util.Arithmetic;
import vpc.core.base.*;
import vpc.core.types.*;
import vpc.util.Cache;

import java.util.Iterator;

/**
 * The <code>VirgilTypeSystem</code> class implements the type system for
 * the virgil language, including the special rules for raw types and classes
 * that cannot be fit into the general typesystem framework.
 *
 * @author Ben L. Titzer
 */
public class V2TypeSystem extends TypeSystem {

    public static final int RAW_INT_WIDTH = 32;
    public static final int RAW_CHAR_WIDTH = 8;

    public static final int KIND_UNKNOWN   = -1;
    public static final int KIND_VOID      = 0;
    public static final int KIND_PRIMITIVE = 1;
    public static final int KIND_CLASS     = 2;
    public static final int KIND_COMPONENT = 3;
    public static final int KIND_ARRAY     = 4;
    public static final int KIND_DELEGATE  = 5;

    protected static class RawTriple {
        PrimRaw.IType t1;
        PrimRaw.IType t2;
        PrimRaw.IType t3;
    }

    public V2TypeSystem() {
        // register operators for booleans
        registerOps();
    }

    protected void registerOps() {
        registerBinOp(PrimBool.TYPE, new Capability.BinOp("and", new PrimBool.AND()));
        registerBinOp(PrimBool.TYPE, new Capability.BinOp("or", new PrimBool.OR()));
        registerUnaryOp(PrimBool.TYPE, new Capability.UnaryOp("!", new PrimBool.NOT()));

        // register the operators for characters
        registerBinOp(PrimChar.TYPE, new Capability.BinOp("<", new PrimChar.LT()));
        registerBinOp(PrimChar.TYPE, new Capability.BinOp(">", new PrimChar.GR()));
        registerBinOp(PrimChar.TYPE, new Capability.BinOp("<=", new PrimChar.LTEQ()));
        registerBinOp(PrimChar.TYPE, new Capability.BinOp(">=", new PrimChar.GREQ()));

        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("+", new PrimInt32.ADD()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("-", new PrimInt32.SUB()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("*", new PrimInt32.MUL()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("/", new PrimInt32.DIV()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("%", new PrimInt32.MOD()));

        // add the relational operators
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("<", new PrimInt32.LT()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp(">", new PrimInt32.GR()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp("<=", new PrimInt32.LTEQ()));
        registerBinOp(PrimInt32.TYPE, new Capability.BinOp(">=", new PrimInt32.GREQ()));

        // add the increment operators
        registerAutoOp(PrimInt32.TYPE, new Capability.AutoOp("++", new PrimInt32.ADD(), PrimInt32.toValue(1)));
        registerAutoOp(PrimInt32.TYPE, new Capability.AutoOp("--", new PrimInt32.SUB(), PrimInt32.toValue(1)));

        registerUnaryOp(PrimInt32.TYPE, new Capability.UnaryOp("-", new PrimInt32.NEG()));
    }

    public Capability.BinOp resolveBinOp(String op, Type t1, Type t2) {
        Capability.BinOp binop = resolveRawOp(op, t1, t2);
        if (binop != null) {
            return binop;
        } else {
            return super.resolveBinOp(op, t1, t2);
        }
    }

    protected Capability.BinOp resolveRawOp(String op, Type t1, Type t2) {
        if ( "&".equals(op) ) {
            RawTriple t = getMaxRawType(getRawTypes(t1, t2));
            if ( t.t3 == null ) return null;
            return new Capability.BinOp("&", new PrimRaw.AND(t.t3.width, Arithmetic.min(t.t1.width, t.t2.width)));
        } else if ( "|".equals(op) ) {
            RawTriple t = getMaxRawType(getRawTypes(t1, t2));
            if ( t.t3 == null ) return null;
            return new Capability.BinOp("|", new PrimRaw.OR(t.t3.width));
        } else if ( "^".equals(op) ) {
            RawTriple t = getMaxRawType(getRawTypes(t1, t2));
            if ( t.t3 == null ) return null;
            return new Capability.BinOp("^", new PrimRaw.XOR(t.t3.width));
        } else if ( "<<".equals(op) ) {
            PrimRaw.IType rt1 = getRawType(t1);
            if ( rt1 == null ) return null;
            return new Capability.BinOp("<<", new PrimRaw.SHL(rt1.width));
        } else if ( ">>".equals(op) ) {
            PrimRaw.IType rt1 = getRawType(t1);
            if ( rt1 == null ) return null;
            return new Capability.BinOp(">>", new PrimRaw.SHR(rt1.width));
        } else if ( "#".equals(op) ) {
            PrimRaw.IType rt1 = getRawType(t1);
            PrimRaw.IType rt2 = getRawType(t2);
            if ( rt1 == null || rt2 == null ) return null;
            return new Capability.BinOp("#", new PrimRaw.Concat(rt1, rt2));
        } else {
            return null;
        }
    }

    public Capability.UnaryOp resolveUnOp(String op, Type t1) {
        if ( "~".equals(op) ) {
            PrimRaw.IType rt1 = getRawType(t1);
            if ( rt1 == null ) return null;
            return new Capability.UnaryOp("~", new PrimRaw.Complement(rt1));
        } else {
            return super.resolveUnOp(op, t1);
        }
    }

    public boolean isAssignable(Type src, Type target) {
        // reflexivity: every type is assignable to itself
        if ( target == src ) return true;
        if ( isClass(target) ) return isSubtypeClass(src, target);
        if ( isArray(target) ) return isNull(src);
        if ( isFunction(target) ) return isSubtypeFunction(src, target);
        if ( isRaw(target) ) return isSubtypeRaw(src, target);
        if ( isTuple(target) ) return isSubtypeTuple(src, target);
        return false;
    }

    protected boolean isSubtypeFunction(Type src, Type target) {
        return isNull(src);
    }

    protected boolean isSubtypeTuple(Type src, Type target) {
        return false;
    }

    private boolean isSubtypeClass(Type src, Type target) {
        if (isNull(src)) return true;
        if (!isClass(src)) return false;
        VirgilClass.IType cts = (VirgilClass.IType)src;
        return cts.isSubtypeOf(target);
    }

    private boolean isSubtypeRaw(Type src, Type target) {
        PrimRaw.IType tt = (PrimRaw.IType)target;
        if ( isRaw(src) ) {
            PrimRaw.IType st = (PrimRaw.IType)src;
            return st.width <= tt.width;
        }
        return false;
    }

    public Type unifyCompare(Type t1, Type t2) {
        Type ut;
        if ( t1 == t2 ) return t1;
        if ( (ut = tryUnifyCompare(t1, t2)) != null ) return ut;
        if ( (ut = tryUnifyCompare(t2, t1)) != null ) return ut;
        return null;
    }

    private Type tryUnifyCompare(Type t1, Type t2) {
        if ( isInt(t1) ) {
            if ( isRaw(t2) ) {
                PrimRaw.IType rt = getRawType(t2);
                return PrimRaw.getType(Arithmetic.max(rt.width, RAW_INT_WIDTH));
            }
            if ( isChar(t2) ) return t1;
        }
        if ( isChar(t1) ) {
            if ( isRaw(t2) ) {
                PrimRaw.IType rt = getRawType(t2);
                return PrimRaw.getType(Arithmetic.max(rt.width, RAW_CHAR_WIDTH));
            }
        }
        if ( isRaw(t1) && isRaw(t2) )
            return getMaxRawType(getRawTypes(t1, t2)).t3;
        if ( isFunction(t1) && isNull(t2) ) return t1;
        if ( isArray(t1) && isNull(t2) ) return t1;
        if ( isClass(t1) ) {
            if ( isNull(t2) ) return t1;
            if ( isClass(t2) ) {
                VirgilClass.IType ct1 = (VirgilClass.IType)t1;
                VirgilClass.IType ct2 = (VirgilClass.IType)t2;
                if ( ct1.isSubtypeOf(ct2) ) return ct2;
                if ( ct2.isSubtypeOf(ct1) ) return ct1;
            }
        }
        return null;
    }

    public boolean isConvertible(Type src, Type target) {
        if ( src == target ) return true;
        if ( isRaw(src) ) return isConvertibleRaw(src, target);
        if ( isInt(src) ) return isRaw(target) || isChar(target);
        if ( isChar(src) ) return isRaw(target);
        return false;
    }

    private boolean isConvertibleRaw(Type t1, Type t2) {
        if ( isRaw(t2) ) return true;
        int width = getRawType(t1).width;
        return isInt(t2) && width <= RAW_INT_WIDTH || isChar(t2) && width <= RAW_CHAR_WIDTH;
    }

    public boolean isPromotable(Type t1, Type t2) {
        // reflexivity: every type is promotable to itself
        if ( t1 == t2 ) return true;
        if ( isChar(t1) ) return isInt(t2) || isRawAtLeast(t2, RAW_CHAR_WIDTH);
        if ( isInt(t1) ) return isRawAtLeast(t2, RAW_INT_WIDTH);
        if ( isRaw(t1) ) return isPromotableRaw(t1, t2);
        return false;
    }

    private boolean isPromotableRaw(Type t1, Type t2) {
        // raw m => raw n, iff m <= n
        if ( isRaw(t2) ) {
            RawTriple t = getRawTypes(t1, t2);
            return t.t1.width <= t.t2.width;
        }
        return false;
    }

    private boolean isRawAtLeast(Type t2, int min) {
        if ( isRaw(t2) ) {
            PrimRaw.IType rt = getRawType(t2);
            return rt.width >= min;
        }
        return false;
    }

    public Type unify(Cache<Type> cache, Type t1, Type t2) throws UnificationError {
        if ( t1 == t2 ) return t1;
        if ( isInt(t1) ) return unifyInt(t1, t2);
        if ( isChar(t1) ) return unifyChar(t1, t2);
        if ( isRaw(t1) ) return unifyRaw(t1, t2);
        if ( isClass(t1) ) return unifyClass(cache, (VirgilClass.IType)t1, t2);
        if ( isNull(t1) ) return unifyNull(t2);
        return unifyRecursive(cache, t1, t2);
    }

    private Type unifyInt(Type t1, Type t2) throws UnificationError {
        if ( isChar(t2) ) return t1;
        if ( isRaw(t2) ) return unifyRaw(t1, t2);
        throw new UnificationError("primitive mismatch", t1, t2);
    }

    private Type unifyChar(Type t1, Type t2) throws UnificationError {
        if ( isInt(t2) ) return t2;
        if ( isRaw(t2) ) return unifyRaw(t1, t2);
        throw new UnificationError("primitive mismatch", t1, t2);
    }

    private Type unifyRecursive(Cache<Type> cache, Type t1, Type t2) throws UnificationError {
        if ( t1 == t2 ) return t1;
        if ( isTypeVar(t1) ) return unifyTypeVar(cache, (TypeVar)t1, t2);
        if ( isNull(t2) ) return unifyNull(t1);
        return unifyTypeCon(cache, t1, t2);
    }

    private Type unifyTypeCon(Cache<Type> cache, Type t1, Type t2) throws UnificationError {
        if ( t1 == t2 ) return t1;
        Type[] e1 = t1.elements();
        if ( e1.length > 0) {
            Type[] e2 = t2.elements();
            if ( e1.length != e2.length ) throw new UnificationError("type param arity mismatch", t1, t2);
            Type[] u1 = new Type[e1.length];
            for ( int cntr = 0; cntr < e1.length; cntr++ ) {
                u1[cntr] = unifyRecursive(cache, e1[cntr], e2[cntr]);
            }
            return cache.getCached(t1.rebuild(u1));
        }
        throw new UnificationError("typecon mismatch", t1, t2);
    }

    private Type unifyTypeVar(Cache<Type> cache, TypeVar tv, Type t2) throws UnificationError {
        if ( tv.binding == null ) return tv.binding = t2;
        return tv.binding = unify(cache, tv.binding, t2);
    }

    private Type unifyNull(Type t2) throws UnificationError {
        if ( isClass(t2) ) return t2;
        if ( isArray(t2) ) return t2;
        if ( isFunction(t2) ) return t2;
        throw new UnificationError("not a reference type", Reference.NULL_TYPE, t2);
    }

    private Type unifyClass(Cache<Type> cache, VirgilClass.IType ct1, Type t2) throws UnificationError {
        if (isNull(t2)) return ct1;
        if (isClass(t2)) {
            VirgilClass.IType ct2 = (VirgilClass.IType)t2;
            LeastUpperBound lub = leastUpperBound(ct1, ct2);
            if ( lub != null ) {
                // unify the type using the computed least upper bounds
                return unifyTypeCon(cache, lub.left, lub.right);
            }
            throw new UnificationError("classes are not related", ct1, t2);
        }
        throw new UnificationError("not a class type", ct1, t2);
    }

    public static LeastUpperBound leastUpperBound(VirgilClass.IType ct1, VirgilClass.IType ct2) {
        VirgilClass.IType lubA = null;
        VirgilClass.IType lubB = null;
        // get top down iterators for each class
        Iterator<VirgilClass.IType> itA = VirgilClass.getChain(ct1).iterator();
        Iterator<VirgilClass.IType> itB = VirgilClass.getChain(ct2).iterator();
        while (itA.hasNext() && itB.hasNext()) {
            // get the type from each chain
            VirgilClass.IType tA = itA.next();
            VirgilClass.IType tB = itB.next();
            // if the type constructors match, record and continue
            if ( tA.getTypeCon() == tB.getTypeCon() ) {
                lubA = tA;
                lubB = tB;
            } else break;
        }
        if ( lubA == null ) return null;
        return new LeastUpperBound(lubA, lubB);
    }

    private Type unifyRaw(Type t1, Type t2) {
        return getMaxRawType(getRawTypes(t1, t2)).t3;
    }

    protected RawTriple getRawTypes(Type t1, Type t2) {
        RawTriple triple = new RawTriple();
        triple.t1 = getRawType(t1);
        triple.t2 = getRawType(t2);
        return triple;
    }

    protected PrimRaw.IType getRawType(Type t1) {
        if ( isRaw(t1) ) return (PrimRaw.IType) t1;
        if ( isChar(t1) ) return PrimRaw.getType(RAW_CHAR_WIDTH);
        if ( isInt(t1) ) return PrimRaw.getType(RAW_INT_WIDTH);
        return null;
    }

    private RawTriple getMaxRawType(RawTriple t) {
        if ( t.t1 != null && t.t2 != null )
            t.t3 = PrimRaw.getMaxType(t.t1, t.t2);
        return t;
    }

    private RawTriple getMinRawType(RawTriple t) {
        if ( t.t1 != null && t.t2 != null )
            t.t3 = PrimRaw.getMinType(t.t1, t.t2);
        return t;
    }

    public static boolean isRaw(Type t) {
        return t instanceof PrimRaw.IType;
    }

    public static boolean isClass(Type t) {
        return t instanceof VirgilClass.IType;
    }

    public static boolean isTuple(Type t) {
        return t instanceof Tuple.IType;
    }

    public static boolean isNull(Type t) {
        return t == Reference.NULL_TYPE;
    }

    public static boolean isVoid(Type t) {
        return t == PrimVoid.TYPE;
    }

    public static boolean isFunction(Type t) {
        return t instanceof Function.IType;
    }

    public static boolean isComponent(Type t) {
        return t instanceof VirgilComponent.IType;
    }

    public static boolean isArray(Type t) {
        return t instanceof VirgilArray.IType;
    }

    public static boolean isAssociation(Type t) {
        return t instanceof Association.IType;
    }

    public static boolean isChar(Type t) {
        return t == PrimChar.TYPE;
    }

    public static boolean isTypeVar(Type t) {
        return t instanceof TypeVar;
    }

    public static boolean isInt(Type t) {
        return t == PrimInt32.TYPE;
    }

    public static boolean isBool(Type t) {
        return t == PrimBool.TYPE;
    }

    public static boolean isTypeParam(Type t) {
        return t instanceof TypeParam.IType;
    }

    public static class LeastUpperBound {
        public final Type left;
        public final Type right;
        public LeastUpperBound(Type l, Type r) {
            left = l;
            right = r;
        }
    }

    public static Type getRootType(Type t) {
        if ( isClass(t) ) return ((VirgilClass.IType)t).getRootType();
        return t;
    }

    public static int getKind(Type t) {
        if ( t == PrimVoid.TYPE ) return KIND_VOID;
        if ( t == PrimChar.TYPE ) return KIND_PRIMITIVE;
        if ( t == PrimBool.TYPE ) return KIND_PRIMITIVE;
        if ( t == PrimInt32.TYPE ) return KIND_PRIMITIVE;
        if ( isRaw(t) ) return KIND_PRIMITIVE;
        if ( isClass(t) ) return KIND_CLASS;
        if ( isFunction(t) ) return KIND_DELEGATE;
        if ( isComponent(t) ) return KIND_COMPONENT;
        if ( isArray(t) ) return KIND_ARRAY;
        return KIND_UNKNOWN;
    }
}
