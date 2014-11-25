/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 30, 2007
 */

package vpc.core.csr;

import cck.util.Util;
import vpc.core.*;
import vpc.core.types.Type;
import vpc.tir.expr.Operator;
import vpc.util.Ovid;

import java.util.List;

/**
 * The <code>CSRStruct</code> class implements a representation of a C struct type
 * and related member access operators for use in the TIR representation.
 *
 * @author Ben L. Titzer
 */
public class CSRStruct {

    /**
     * The <code>Struct</code> class represents a C struct type. A struct can contain
     * members (fields only), each with a name and a type.
     */
    public static class IType extends CSRType {
        public List<Field> fields;
        public final String shortName;

        public static class Field {
            public final CSRType type;
            public final String name;

            public Field(String n, CSRType t) {
                type = t;
                name = n;
            }
        }

        public IType(String n) {
            super("struct " + n);
            shortName = n;
            fields = Ovid.newLinkedList();
        }

        public Field addField(String name, CSRType type) {
            Field field = new Field(name, type);
            fields.add(field);
            return field;
        }

        public Field getField(String name) {
            for ( Field f : fields ) {
                if ( f.name.equals(name) ) return f;
            }
            return null;
        }

        public boolean equals(Object o) {
            return o == this || o instanceof IType && ((IType)o).shortName.equals(shortName);
        }

        public String renderValue(boolean init, Value value) {
            Value[] vals = value == Value.BOTTOM ? new Value[fields.size()] : ((Val)value).values;
            StringBuffer buf = new StringBuffer();
            if ( !init ) {
                buf.append("((");
                buf.append(typename);
                buf.append(")");
            }
            buf.append("{ ");
            for ( int cntr = 0; cntr < vals.length; cntr++ ) {
                CSRType ftype = fields.get(cntr).type;
                buf.append(ftype.renderValue(true, vals[cntr]));
                if ( cntr < vals.length - 1 ) buf.append(", ");
            }
            buf.append(" }");
            if ( !init ) buf.append(")");
            return buf.toString();
        }
        public Type rebuild(Type[] elements) {
            return this;
        }
    }

    public static class GetValueField extends Operator.Op1 implements Operator.Location {
        public final IType structType;
        public final IType.Field field;

        public GetValueField(IType t, IType.Field f) {
            super(t, f.type);
            structType = t;
            field = f;
        }

        public Value apply1(Value v1) throws Operator.Exception {
            throw Util.unimplemented();
        }

        public Operator getOperator() {
            return this;
        }

        public Operator setOperator() {
            return new SetValueField(structType, field);
        }
    }

    public static class SetValueField extends Operator.Op2 {
        public final IType structType;
        public final IType.Field field;

        public SetValueField(IType t, IType.Field f) {
            super(t, f.type, f.type);
            field = f;
            structType = t;
        }

        public Value apply2(Value v1, Value v2) throws Operator.Exception {
            throw Util.unimplemented();
        }
    }

    public static class GetRefField extends Operator.Op1 implements Operator.Location {
        public final CSRPointer.IType ptrType;
        public final IType structType;
        public final IType.Field field;

        public GetRefField(CSRPointer.IType t, IType.Field f) {
            super(t, f.type);
            ptrType = t;
            structType = (IType)t.ptype;
            field = f;
        }

        public Value apply1(Value v1) throws Operator.Exception {
            throw Util.unimplemented();
        }

        public Operator getOperator() {
            return this;
        }

        public Operator setOperator() {
            return new SetRefField(ptrType, field);
        }
    }

    public static class SetRefField extends Operator.Op2 {
        public final CSRPointer.IType ptrType;
        public final IType structType;
        public final IType.Field field;

        public SetRefField(CSRPointer.IType t, IType.Field f) {
            super(t, f.type, f.type);
            ptrType = t;
            structType = (IType)t.ptype;
            field = f;
        }

        public Value apply2(Value v1, Value v2) throws Operator.Exception {
            throw Util.unimplemented();
        }
    }

    public static class NewValue extends Operator {
        public final IType structType;

        public NewValue(IType t) {
            super(t);
            structType = t;
        }

        public Type[] getOperandTypes() {
            CSRType[] t = new CSRType[structType.fields.size()];
            int cntr = 0;
            for ( IType.Field f : structType.fields ) {
                t[cntr++] = f.type;
            }
            return t;
        }

        public Value apply(Program.DynamicEnvironment env, Value... v) {
            throw Util.unimplemented();
        }
    }

    /**
     * The <code>CSRStruct.Val</code> class represents a value (instance) of a C struct,
     * including the values of all its fields.
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

        public Val(IType t, Heap.Record r, Value[] v) {
            super(t);
            type = t;
            record = r;
            values = v;
        }
    }
}
