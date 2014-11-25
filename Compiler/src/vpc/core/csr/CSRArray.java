/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 30, 2007
 */

package vpc.core.csr;

import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.Heap;
import vpc.core.Value;
import vpc.core.base.PrimInt32;
import vpc.core.base.Tuple;
import vpc.core.types.Type;
import vpc.tir.expr.Operator;

/**
 * The <code>CSRArray</code> class implements the concept of the C source representation
 * of an array type. This includes the array type and the operators associated with
 * C-level arrays.
 *
 * @author Ben L. Titzer
 */
public class CSRArray {

    /**
     * The <code>Array</code> class represents a C type corresponding to an array.
     * Thought C pointers and C arrays are nearly interchangeable, the syntax of
     * declaring an array differs slightly.
     */
    public static class IType extends CSRType {
        public final CSRType elemType;
        protected static final Value[] NO_VALUES = new Value[0];

        public IType(CSRType et) {
            super(et + "[]");
            elemType = et;
        }

        public String globalDecl(String varname) {
            if ( elemType instanceof CSRFunction.IType ) {
                CSRFunction.IType f = (CSRFunction.IType)elemType;
                return f.returnType + " (*" + varname + "[])(" + Tuple.toCommaString(f.paramType) + ")";
            }
            return elemType + " " + varname + "[]";
        }

        public String localDecl(String varname) {
            if ( elemType instanceof CSRFunction.IType ) {
                CSRFunction.IType f = (CSRFunction.IType)elemType;
                return f.returnType + " (**" + varname + ")(" + Tuple.toCommaString(f.paramType) + ")";
            }
            return elemType + " *" + varname;
        }

        public String fieldDecl(String varname) {
            return globalDecl(varname);
        }

        public String renderValue(boolean init, Value value) {
            Value[] vals = value == Value.BOTTOM ? NO_VALUES : ((Val)value).values;
            StringBuffer buf = new StringBuffer();
            buf.append("{ ");
            for ( int cntr = 0; cntr < vals.length; cntr++ ) {
                buf.append(elemType.renderValue(true, vals[cntr]));
                if ( cntr < vals.length - 1 ) buf.append(", ");
            }
            buf.append(" }");
            return buf.toString();
        }
        public Type rebuild(Type[] elements) {
            assert elements.length == 1;
            return new IType((CSRType)elements[0]);
        }

    }

    public static class GetElement extends Operator.Op2 implements Operator.Location {
        public final IType arrayType;

        public GetElement(IType t) {
            // TODO: do not use PrimInt32
            super(t, PrimInt32.TYPE, t.elemType);
            arrayType = t;
        }

        public Value apply2(Value v1, Value v2) throws Operator.Exception {
            throw Util.unimplemented();
        }

        public Operator getOperator() {
            return this;
        }

        public Operator setOperator() {
            return new SetElement(arrayType);
        }
    }

    public static class SetElement extends Operator.Op3 {
        public final IType arrayType;

        public SetElement(IType t) {
            // TODO: do not use PrimInt32
            super(t, PrimInt32.TYPE, t.elemType, t.elemType);
            arrayType = t;
        }

        public Value apply3(Value v1, Value v2, Value v3) throws Operator.Exception {
            throw Util.unimplemented();
        }
    }


    /**
     * The <code>CSRArray.Val</code> class represents a value (instance) of a C array,
     * including the values of all its elements.
     */
    public static class Val extends CSRData.CSRValue {
        public final IType type;
        public Heap.Record record;
        public Value[] values;

        public Val(IType t, Heap.Record r) {
            super(t);
            type = t;
            record = r;
        }

        public Type getType() {
            return type;
        }
    }
}
