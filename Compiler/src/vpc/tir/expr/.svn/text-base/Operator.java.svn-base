/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Mar 20, 2006
 */

package vpc.tir.expr;

import cck.text.StringUtil;
import vpc.core.Program;
import vpc.core.Value;
import vpc.core.types.Type;

/**
 * The <code>Operator</code> class represents an operator on expressions within the
 * compiler. The operator may represent a simple arithmetic operator or a more complicated
 * operator such as an array element accessor, a field accessor, or a virtual method
 * resolution.
 *
 * @author Ben L. Titzer
 */
public abstract class Operator {

    protected final Type result;

    /**
     * The <code>Operator.Visitor</code> interface represents a visitor that can
     * visit various kinds of operators.
     */
    public interface Visitor<R, E> {
        public R visit(Operator o, E... e);
    }

    /**
     * The <code>Operator.Location</code> interface represents an operator that
     * yields a location. Every location, whether it be a local variable, an array
     * element, or even a bit inside another location, has operators to retrieve
     * the value and to set the value.
     */
    public interface Location {
        public Operator getOperator();
        public Operator setOperator();
    }

    /**
     * The <code>Exception</code> class represents an exception that may
     * occur when executing an operator on values. Examples include
     * <code>DivideByZeroException</code>, <code>BoundsCheckException</code>,
     * etc.
     */
    public static class Exception extends java.lang.Exception {
        public final String type;
        public final String msg;

        public Exception(String m) {
            type = StringUtil.getShortName(getClass());
            msg = m;
        }

        public Exception(String type, String msg) {
            this.type = type;
            this.msg = msg;
        }

    }

    /**
     * The constructor for the <code>Operator</code> class accepts the result type
     * of this operator and stores it in a protected final field.
     *
     * @param r the result type of this operator
     */
    protected Operator(Type r) {
        result = r;
    }

    /**
     * The <code>getResultType()</code> method returns the result type of this operator.
     *
     * @return the type of the result of this operator
     */
    public Type getResultType() {
        return result;
    }

    /**
     * The <code>getOperandTypes()</code> method returns the expected types of the operand types
     * to this operator.
     *
     * @return the expected operand types for this operator
     */
    public abstract Type[] getOperandTypes();

    /**
     * The <code>apply()</code> method applies this operator to the specified values.
     *
     * @param env
     * @param vals the values to apply this operator to
     * @return the value that represents the result of applying the operator to the values
     * @throws Operator.Exception if the evaluation of the operator resulted
     *                            in an exception
     */
    public abstract Value apply(Program.DynamicEnvironment env, Value... vals) throws Exception;

    /**
     * The <code>accept()</code> method implements one half of the visitor interface
     * for operators.
     * @param v the visitor to accept
     * @param expr a list of sub-expressions that are operands to this operator
     * @return the result of calling the most appropriate <code>visit</code> method
     * on the visitor
     */
    public <R, E> R accept(Visitor<R, E> v, E... expr) {
        return v.visit(this, expr);
    }

    /**
     * The <code>Op0</code> class represents a nullary operator; i.e. an operator that
     * accepts no arguments. This class exists solely to reduce the
     * verbosity of implementing classes. For example, its default implementation of
     * <code>apply(Value... v)</code> unpacks the variable-length arguments and calls
     * an abstract method for subclasses to implement.
     *
     * @author Ben L. Titzer
     */
    public abstract static class Op0 extends Operator {

        protected Op0(Type r) {
            super(r);
        }

        /**
         * The <code>getOperandTypes()</code> method returns the expected types of the operand types
         * to this operator.
         *
         * @return the expected operand types for this operator
         */
        public Type[] getOperandTypes() {
            return Type.NOTYPES;
        }

        /**
         * The <code>apply()</code> method applies this operator to the specified values.
         * This implementation accepts only two values as parameters and delegates to the
         * specialized <code>apply()</code> method which accepts two values.
         *
         * @param env
         * @param vals the values to apply this operator to
         * @return the value that results from evaluating this operator with the specified values
         */
        public Value apply(Program.DynamicEnvironment env, Value... vals) throws Exception {
            assert vals.length == 0;
            return apply0();
        }

        /**
         * The <code>apply()</code> method evaluates this operator.
         *
         * @return the result of applying this operator to the specified values
         */
        public abstract Value apply0() throws Exception;
    }

    /**
     * The <code>Op1</code> class represents a unary operator; i.e. an operator that
     * accepts one sub-expression as an arguments. This class exists solely to reduce the
     * verbosity of implementing classes. For example, its default implementation of
     * <code>apply(Value... v)</code> unpacks the variable-length arguments and calls
     * an abstract method for subclasses to implement.
     *
     * @author Ben L. Titzer
     */
    public abstract static class Op1 extends Operator {

        public final Type type1;

        protected Op1(Type t1, Type r) {
            super(r);
            type1 = t1;
        }

        /**
         * The <code>getOperandTypes()</code> method returns the expected types of the operand types
         * to this operator.
         *
         * @return the expected operand types for this operator
         */
        public Type[] getOperandTypes() {
            return new Type[] { type1 };
        }

        /**
         * The <code>apply()</code> method applies this operator to the specified values.
         * This implementation accepts only two values as parameters and delegates to the
         * specialized <code>apply()</code> method which accepts two values.
         *
         * @param env
         * @param vals the values to apply this operator to
         * @return the value that results from evaluating this operator with the specified values
         */
        public Value apply(Program.DynamicEnvironment env, Value... vals) throws Exception {
            assert vals.length == 1;
            return apply1(vals[0]);
        }

        /**
         * The <code>apply()</code> method evaluates this operator with the specified
         * value as arguments.
         *
         * @param v1 the value of the first operand
         * @return the result of applying this operator to the specified values
         */
        public abstract Value apply1(Value v1) throws Exception;
    }

    /**
     * The <code>Op2</code> class represents a binary operator; i.e. an operator that
     * accepts two sub-expressions as arguments. This class exists solely to reduce the
     * verbosity of implementing classes. For example, its default implementation of
     * <code>apply(Value... v)</code> unpacks the variable-length arguments and calls
     * an abstract method for subclasses to implement.
     *
     * @author Ben L. Titzer
     */
    public abstract static class Op2 extends Operator {

        public final Type type1;
        public final Type type2;

        protected Op2(Type t1, Type t2, Type r) {
            super(r);
            type1 = t1;
            type2 = t2;
        }

        /**
         * The <code>getOperandTypes()</code> method returns the expected types of the operand types
         * to this operator.
         *
         * @return the expected operand types for this operator
         */
        public Type[] getOperandTypes() {
            return new Type[]{type1, type2};
        }

        /**
         * The <code>apply()</code> method applies this operator to the specified values.
         * This implementation accepts only two values as parameters and delegates to the
         * specialized <code>apply()</code> method which accepts two values.
         *
         * @param env
         * @param vals the values to apply this operator to
         * @return the value that results from evaluating this operator with the specified values
         */
        public Value apply(Program.DynamicEnvironment env, Value... vals) throws Exception {
            assert vals.length == 2;
            return apply2(vals[0], vals[1]);
        }

        /**
         * The <code>apply()</code> method evaluates this binary operator with the specified
         * values as arguments.
         *
         * @param v1 the value of the first operand
         * @param v2 the value of the second operand
         * @return the result of applying this operator to the specified values
         */
        public abstract Value apply2(Value v1, Value v2) throws Exception;
    }

    /**
     * The <code>Op3</code> class represents a ternary operator; i.e. an operator that
     * accepts three sub-expressions as arguments. This class exists solely to reduce the
     * verbosity of implementing classes. For example, its default implementation of
     * <code>apply(Value... v)</code> unpacks the variable-length arguments and calls
     * an abstract method for subclasses to implement.
     *
     * @author Ben L. Titzer
     */
    public abstract static class Op3 extends Operator {

        public final Type type1;
        public final Type type2;
        public final Type type3;

        protected Op3(Type t1, Type t2, Type t3, Type r) {
            super(r);
            type1 = t1;
            type2 = t2;
            type3 = t3;
        }

        /**
         * The <code>getOperandTypes()</code> method returns the expected types of the operand types
         * to this operator.
         *
         * @return the expected operand types for this operator
         */
        public Type[] getOperandTypes() {
            return new Type[]{type1, type2, type3};
        }

        /**
         * The <code>apply()</code> method applies this operator to the specified values.
         * This implementation accepts only two values as parameters and delegates to the
         * specialized <code>apply()</code> method which accepts two values.
         *
         * @param env
         * @param vals the values to apply this operator to
         * @return the value that results from evaluating this operator with the specified values
         */
        public Value apply(Program.DynamicEnvironment env, Value... vals) throws Exception {
            assert vals.length == 3;
            return apply3(vals[0], vals[1], vals[2]);
        }

        /**
         * The <code>apply()</code> method evaluates this binary operator with the specified
         * values as arguments.
         *
         * @param v1 the value of the first operand
         * @param v2 the value of the second operand
         * @param v3 the value of the third operand
         * @return the result of applying this operator to the specified values
         */
        public abstract Value apply3(Value v1, Value v2, Value v3) throws Exception;
    }
}
