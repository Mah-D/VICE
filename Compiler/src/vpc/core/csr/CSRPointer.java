/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 30, 2007
 */

package vpc.core.csr;

import cck.util.Util;
import vpc.core.Value;
import vpc.core.types.Type;
import vpc.tir.expr.Operator;

/**
 * @author Ben L. Titzer
 */
public class CSRPointer {

    /**
     * The <code>Pointer</code> class represents the C pointer type, which encapsulates
     * the idea of a pointer to a typed memory location.
     */
    public static class IType extends CSRType {
        public final CSRType ptype;

        public IType(CSRType pt) {
            super(pt + " *");
            ptype = pt;
        }

        public String renderValue(boolean init, Value value) {
            Val pval = (Val)value;
            if (pval == Value.BOTTOM || pval.item == null) return "(("+ this +")0)";
            if ( pval.getType() == this ) return "(&"+ pval.item.name+")";
            else return "((" + this + ")&"+ pval.item.name+")";
        }

        public Type rebuild(Type[] elements) {
            assert elements.length == 1;
            return new IType((CSRType)elements[0]);
        }
    }

    /**
     * The <code>VoidPtr</code> class represents a void (untyped) pointer to memory.
     * Such a pointer can be used to circumvent the (admittedly weak) C type system.
     */
    public static class VoidPtr extends CSRType {
        public VoidPtr() {
            super("void *");
        }

        public String renderValue(boolean init, Value value) {
            if ( value instanceof CSRFunction.Val ) {
                return CSRFunction.renderFuncVal(init, (CSRFunction.Val)value);                
            } else {
                Val pval = (Val)value;
                if (pval == Value.BOTTOM) return "((void *)0)";
                else if (pval.item == null) return "(("+ pval.getType() +")0)";
                return "(&"+ pval.item.name+")";
            }
        }
        public Type rebuild(Type[] elements) {
            return this;
        }
    }

    public static class GetPtr extends Operator.Op1 implements Operator.Location {
        public final IType ptrType;

        public GetPtr(CSRPointer.IType t) {
            super(t, t.ptype);
            ptrType = t;
        }

        public Value apply1(Value v1) throws Operator.Exception {
            throw Util.unimplemented();
        }

        public Operator getOperator() {
            return this;
        }

        public Operator setOperator() {
            return new SetPtr(ptrType);
        }
    }

    public static class SetPtr extends Operator.Op2 {
        public final IType ptrType;

        public SetPtr(CSRPointer.IType t) {
            super(t, t.ptype, t.ptype);
            ptrType = t;
        }

        public Value apply2(Value v1, Value v2) throws Operator.Exception {
            throw Util.unimplemented();
        }
    }

    /**
     * The <code>CSRPointer.Val</code> class represents a value that contains a reference to a
     * global variable, which could be a record, an array, or a scalar. The
     * record has a <code>metadata</code> field that stores the type of the record, but this
     * class also stores the dynamic type of the reference. A reference can be <code>null</code>,
     * in which case the dynamic type is <code>Type.NULL</code> and the record reference is
     * <code>null</code>.
     */
    public static class Val extends CSRData.CSRValue {
        public final CSRData.Global item;

        public Val(CSRType t, CSRData.Global i) {
            super(t);
            item = i;
        }

        public boolean equals(Object o) {
            if (o == this) return true; // degenerate aliasing case
            if (o == Value.BOTTOM ) return item == null; // null == bottom
            return o instanceof Val && ((Val) o).item == item;
        }

        public String toString() {
            return type.renderValue(false, this);
        }

    }
}
