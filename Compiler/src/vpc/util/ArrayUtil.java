/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 25, 2007
 */

package vpc.util;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * @author Ben L. Titzer
 */
public class ArrayUtil {

    public static <T> T[] append(T[] ol, T val) {
        T[] nl = newArray(ol, ol.length + 1);
        System.arraycopy(ol, 0, nl, 0, ol.length);
        nl[ol.length] = val;
        return nl;
    }

    public static <T> T[] prepend(T val, T[] ol) {
        T[] nl = newArray(ol, ol.length + 1);
        System.arraycopy(ol, 0, nl, 1, ol.length);
        nl[0] = val;
        return nl;
    }

    public static <T> T[] trunc(T[] ol, int nlen) {
        T[] nl = newArray(ol, nlen);
        System.arraycopy(ol, 0, nl, 0, nlen);
        return nl;
    }

    public static <T> T[] trunc1(T[] ol) {
        return trunc(ol, ol.length - 1);
    }

    public static <T>T[] newArray(T[] ol, int nlen) {
        Class et = ol.getClass().getComponentType();
        return (T[]) Array.newInstance(et, nlen);
    }

    public static <S, T> T[] map(S[] ol, Delegate<T, S> mapfun, Class<T> tt) {
        T[] nl = (T[]) Array.newInstance(tt, ol.length);
        for ( int cntr = 0; cntr < ol.length; cntr++ ) {
            nl[cntr] = mapfun.invoke(ol[cntr]);
        }
        return nl;
    }

    public static <T extends Nested<T>> T substitute(Class<T> tt, T[] from, T[] to, T expr) {
        return substitute(null, tt, from, to, expr);
    }

    public static <T extends Nested<T>> T substitute(Cache<T> cache, Class<T> tt, T[] from, T[] to, T expr) {
        Map<T, T> map = Ovid.newMap();
        for ( int cntr = 0; cntr < from.length; cntr++ ) {
            map.put(from[cntr], to[cntr]);
        }
        T result = substitute(cache, tt, map, expr);
        return result;
    }

    public static <T extends Nested<T>> T substitute(Class<T> tt, Map<T, T> map, T expr) {
        return substitute(null, tt, map, expr);
    }

    public static <T extends Nested<T>> T rebuild(Cache<T> cache, Class<T> tt, T expr) {
        Map<T, T> map = Ovid.emptyMap();
        return substitute(cache, tt, map, expr);
    }

    public static <T extends Nested<T>> T substitute(Cache<T> cache, Class<T> tt, Map<T, T> map, T expr) {
        T nexpr = map.get(expr);
        if (nexpr == null) {
            T[] oldElems = expr.elements();
            T[] newElems = oldElems;
            if (oldElems.length > 0) {
                newElems = (T[])Array.newInstance(tt, oldElems.length);
                for (int cntr = 0; cntr < newElems.length; cntr++) {
                    newElems[cntr] = substitute(cache, tt, map, oldElems[cntr]);
                }
            }
            nexpr = expr.rebuild(newElems);
            if (cache != null) nexpr = cache.getCached(nexpr);
        }
        return nexpr;
    }
}
