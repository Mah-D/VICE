/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Apr 18, 2006
 */
package vpc.tir.expr;

import vpc.util.Ovid;

import java.util.Map;

/**
 * The <code>Precedence</code> class stores the precedence number for nesting
 * infix binary operators. This precedence number is used to determine when
 * parentheses are required in order to enforce the correct order of operations
 * when generating a textual representation of an operator tree.
 *
 * @author Ben L. Titzer
 */
public class Precedence {

    protected static final Map<Class, Integer> map = Ovid.newMap();

    public static int PREC_UNKNOWN = -1;
    public static int PREC_TERM = 25;
    public static int PREC_DOT = 19;
    public static int PREC_ARROW = 19;
    public static int PREC_INDEX = 19;
    public static int PREC_APPLY = 17;
    public static int PREC_DEREF = 16;
    public static int PREC_PREFIX_INC = 16;
    public static int PREC_COMP = 15;
    public static int PREC_NOT = 15;
    public static int PREC_MINUS = 14;
    public static int PREC_PLUS = 14;
    public static int PREC_CAST = 13;
    public static int PREC_MULTIPLY = 12;
    public static int PREC_DIVIDE = 12;
    public static int PREC_MODULUS = 12;
    public static int PREC_ADD = 11;
    public static int PREC_SUBTRACT = 11;
    public static int PREC_SHIFT_LEFT = 10;
    public static int PREC_SHIFT_RIGHT = 10;
    public static int PREC_LESS_THAN = 9;
    public static int PREC_LESS_EQUAL = 9;
    public static int PREC_GREATER = 9;
    public static int PREC_GREATER_EQUAL = 9;
    public static int PREC_EQUAL = 8;
    public static int PREC_NOT_EQUAL = 8;
    public static int PREC_BITWISE_AND = 7;
    public static int PREC_BITWISE_XOR = 6;
    public static int PREC_BITWISE_OR = 5;
    public static int PREC_LOGICAL_AND = 4;
    public static int PREC_LOGICAL_OR = 3;
    public static int PREC_IF_EXPR = 2;
    public static int PREC_ASSIGN = 1;

    public static void register(Class c, int i) {
        map.put(c, i);
    }

    public static int get(Class c) {
        Integer i = map.get(c);
        if (i == null) return PREC_UNKNOWN;
        return i;
    }

}
