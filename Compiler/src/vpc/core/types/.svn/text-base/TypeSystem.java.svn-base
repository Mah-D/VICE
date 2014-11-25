/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Sep 17, 2006
 */
package vpc.core.types;

import cck.text.StringUtil;
import vpc.util.Cache;
import vpc.util.Ovid;

import java.util.Map;

/**
 * The <code>TypeSystem</code> class defines type rules for a particular language.
 * It includes such functionality as the rules regarding automatic type conversion,
 * assignability, subtyping, source capabilities such as binary operators, etc.
 *
 * @author Ben L. Titzer
 */
public abstract class TypeSystem {
    protected final Map<String, Map<Type, Capability.BinOp>> binopMap = Ovid.newMap();
    protected final Map<String, Map<Type, Capability.UnaryOp>> unaryMap = Ovid.newMap();
    protected final Map<String, Map<Type, Capability.AutoOp>> autoMap = Ovid.newMap();

    public Capability.AutoOp resolveAutOp(String op, Type t1) {
        Map<Type, Capability.AutoOp> map = autoMap.get(op);
        if (map == null) return null;
        return map.get(t1);
    }

    public Capability.BinOp resolveBinOp(String op, Type t1, Type t2) {
        Map<Type, Capability.BinOp> map = binopMap.get(op);
        if (map == null) return null;
        return map.get(t1);
    }

    public Capability.UnaryOp resolveUnOp(String op, Type t1) {
        Map<Type, Capability.UnaryOp> map = unaryMap.get(op);
        if (map == null) return null;
        return map.get(t1);
    }

    /**
     * The <code>isAssignable()</code> method implements the assignability relation
     * for this type system. This method should determine whether a statement which
     * assigns a value of type "src" to the destination expression of type "dst"
     * is correct.
     *
     * @param src the type of the expression being assigned to the location
     * @param target
     * @return true if the expression can be assigned to the location; false otherwise
     */
    public abstract boolean isAssignable(Type src, Type target);

    /**
     * The <code>unifyCompare()</code> method implements the comparability relation
     * on types. Two types are comparable if they can be compared with the "==" and
     * "!=" operators.
     * @param t1 the type of the left hand expression
     * @param t2 the type of the right hand expression
     * @return the comparison type if the expressions can be compared; null otherwise
     */
    public abstract Type unifyCompare(Type t1, Type t2);

    /**
     * The <code>isConvertible()</code> method implements the convertibility relationship
     * on types. A type is convertible to another type if an explicit type conversion
     * operation on an expression of the source type is runtime convertible to a value
     * of the target type.
     *
     * @param src the type of the source expression
     * @param target the target type of the conversion expression
     * @return true if the expression can be converted to the destination type; false otherwise
     */
    public abstract boolean isConvertible(Type src, Type target);

    /**
     * The <code>isPromotable()</code> method implements the promotable relationship
     * on types. A type is promotable to another type if an implicit type conversion
     * operation can be inserted by the compiler to translate a value of the source type
     * to a value of the destination type.
     *
     * @param src the type of the source expression
     * @param target the target type of the conversion expression
     * @return true if the expression can be promoted automatically to the destination type; false otherwise
     */
    public abstract boolean isPromotable(Type src, Type target);

    
    /**
     * The <code>unify()</code> method implements the unification operation on types
     * that is used in type inferrence.
     * @param cache
     * @param t1 the first type
     * @param t2 the second type
     * @return a type to which the two types can be unified if it exists
     * @throws UnificationError if the two types cannot be unified
     */
    public abstract Type unify(Cache<Type> cache, Type t1, Type t2) throws UnificationError;

    protected void registerUnaryOp(Type.Simple type, Capability.UnaryOp op) {
        getMap(op.opname, unaryMap).put(type, op);
    }

    public void registerAutoOp(Type.Simple type, Capability.AutoOp op) {
        getMap(op.opname, autoMap).put(type, op);
    }

    public void registerBinOp(Type.Simple type, Capability.BinOp op) {
        getMap(op.opname, binopMap).put(type, op);
    }

    protected <T> Map<Type, T> getMap(String key, Map<String, Map<Type, T>> mapmap) {
        Map<Type, T> map = mapmap.get(key);
        if (map == null) {
            map = Ovid.newMap();
            mapmap.put(key, map);
        }
        return map;
    }

    /**
     * The <code>UnificationError</code> class represents an exception that is thrown
     * while attempting to unify types that cannot be unified according to the rules
     * of the language.
     */
    public static class UnificationError extends Exception {

        public final Type type1;
        public final Type type2;

        public UnificationError(String msg, Type t1, Type t2) {
            super("cannot unify types "+ StringUtil.quote(t1)+" and "+StringUtil.quote(t2)+": "+msg);
            type1 = t1;
            type2 = t2;
        }
    }
}
