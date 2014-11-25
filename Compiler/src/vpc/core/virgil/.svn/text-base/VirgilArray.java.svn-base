/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Mar 24, 2006
 */
package vpc.core.virgil;

import cck.parser.AbstractToken;
import vpc.core.*;
import vpc.core.base.*;
import vpc.core.decl.Field;
import vpc.core.types.*;
import vpc.tir.expr.Operator;
import vpc.util.Cache;

import java.util.Arrays;

/**
 * The <code>VirgilArray</code> class represents the array concept throughout the compiler.
 * An array concept includes operations such as reading / writing to array elements
 * (which may generate a
 * <code>NullCheckException</code> or
 * <code>BoundsCheckException</code>)
 * and getting the length
 * (which may generate a
 * <code>NullCheckException</code> exception).
 *
 * @author Ben L. Titzer
 */
public class VirgilArray {

    public static final ITypeCon TYPECON = new ITypeCon();
    public static final Type.Simple DEFAULT_INDEX_TYPE = PrimInt32.TYPE;

    /**
     * The <code>VirgilArray.IType</code> class represents an array type.
     */
    public static class IType extends Type implements Heap.Blueprint, Callable {
        protected final Type elemType;

        public IType(Type et) {
            super(buildArrayName(et));
            elemType = et;
        }

        public Type getElemType() {
            return elemType;
        }

        public boolean equals(Object o) {
            return o == this || o instanceof IType && elemType.equals(((IType)o).elemType);
        }

        public Type[] elements() {
            return new Type[] {DEFAULT_INDEX_TYPE, elemType };
        }

        public Type rebuild(Type[] ne) {
            assert ne.length == 2;
            assert ne[0] == DEFAULT_INDEX_TYPE;
            return new IType(ne[1]);
        }

        public Type getResultType() {
            return elemType;
        }

        public Type getParameterType() {
            return DEFAULT_INDEX_TYPE;
        }

        public Type getCellType(Heap.Record rec, int cell) {
            return elemType;
        }

        public String getCellName(Heap.Record rec, int cell) {
            return String.valueOf(cell);
        }

        public Field getCellDecl(Heap.Record rec, int cell) {
            return null;
        }
    }

    public static class ITypeCon extends TypeCon {
        public ITypeCon() {
            super("Array", 1);
        }
        public IType newType(Cache<Type> cache, Type... t) {
            assert t.length == 1;
            return cache.getCached(new IType(t[0]));
        }
        public String render(TypeRef... p) {
            assert p.length == 1;
            return buildArrayName(p[0]);
        }
    }

    public static class GetLength extends Operator.Op1 {
        public final IType arrayType;
        public GetLength(IType t) {
            super(t, PrimInt32.TYPE);
            arrayType = t;
        }

        public Value apply1(Value v1) throws Reference.NullCheckException {
            Heap.Record array = (Heap.Record) v1;
            if (array == null) throw new Reference.NullCheckException();
            return PrimInt32.toValue(array.getSize());
        }
    }

    public static class GetElement extends Operator.Op2 implements Operator.Location {
        public final IType arrayType;

        public GetElement(IType t) {
            super(t, PrimInt32.TYPE, t.elemType);
            arrayType = t;
        }

        public Value apply2(Value v1, Value v2) throws Operator.Exception {
            Heap.Record array = (Heap.Record) v1;
            int index = checkIndex(array, v2);
            return array.getValue(index);
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
            super(t, PrimInt32.TYPE, t.elemType, t.elemType);
            arrayType = t;
        }

        public Value apply3(Value v1, Value v2, Value v3) throws Operator.Exception {
            Heap.Record array = (Heap.Record) v1;
            int index = checkIndex(array, v2);
            array.setValue(index, v3);
            return v3;
        }
    }

    public static class Alloc extends Operator {
        public final TypeFormula<IType> arrayType;
        public final int dimensions;

        public Alloc(IType t, int d) {
            super(t);
            arrayType = TypeFormula.newFormula(t);
            dimensions = d;
        }

        public IType[] getOperandTypes() {
            IType[] t = new IType[dimensions];
            Arrays.fill(t, PrimInt32.TYPE);
            return t;
        }

        public Value apply(Program.DynamicEnvironment env, Value... v) throws Operator.Exception {
            assert v.length == dimensions;
            return newArray(env.getHeap(), arrayType.instantiate(env), v, 0);
        }

        protected Value newArray(Heap heap, IType at, Value[] dims, int wdim) throws Operator.Exception {
            Type et = at.getElemType();
            int len = PrimInt32.fromValue(dims[wdim]);
            if (len < 0) throw new Operator.Exception("ArrayAllocationException");
            Heap.Record rec = allocArray(heap, at, len);
            if (wdim == dims.length - 1) {
                // inner most dimension = element type
                for (int cntr = 0; cntr < len; cntr++)
                    rec.setValue(cntr, Value.BOTTOM);
            } else {
                // not the inner most dimension; recursively make inner arrays
                for (int cntr = 0; cntr < len; cntr++) {
                    Value v = newArray(heap, (VirgilArray.IType)et, dims, wdim + 1);
                    rec.setValue(cntr, v);
                }
            }
            return rec;
        }
    }

    public static class Init extends Operator {
        public final IType arrayType;
        public final int length;

        public Init(IType t, int vl) {
            super(t);
            arrayType = t;
            length = vl;
        }

        public Type[] getOperandTypes() {
            Type[] t = new IType[1 + length];
            Arrays.fill(t, arrayType.getElemType());
            t[0] = arrayType;
            return t;
        }

        public Value apply(Program.DynamicEnvironment env, Value... v) {
            assert v.length == length + 1;
            Heap.Record rec = (Heap.Record) v[0];
            for ( int cntr = 1; cntr < v.length; cntr++ ) {
                rec.setValue(cntr - 1, v[cntr]);
            }
            return v[0];
        }
    }


    public static class BoundsCheckException extends Operator.Exception {
        public final Heap.Record array;
        public final int index;

        public BoundsCheckException(Heap.Record rec, int indx) {
            super(boundsCheckMessage(rec, indx));
            array = rec;
            index = indx;
        }
    }

    private static String boundsCheckMessage(Heap.Record rec, int index) {
        if (index < 0) return index + " < 0";
        else return index + " >= " + rec.getSize();
    }

    protected static int checkIndex(Heap.Record array, Value ind) throws Reference.NullCheckException, BoundsCheckException {
        if (array == null) throw new Reference.NullCheckException();
        int index = PrimInt32.fromValue(ind);
        if (index < 0 || index >= array.getSize()) throw new BoundsCheckException(array, index);
        return index;
    }

    public static String buildArrayName(TypeToken et) {
        StringBuffer buf = new StringBuffer();
        if (isFunction(et)) {
            buf.append('(');
            buf.append(et.toString());
            buf.append(')');
        } else {
            buf.append(et.toString());
        }
        buf.append("[]");
        return buf.toString();
    }

    private static boolean isFunction(TypeToken et) {
        return et.toString().startsWith("function");
    }

    public static IType getArrayType(Cache<Type> cache, Type elemType) {
        return TYPECON.newType(cache, elemType);
    }

    public static Heap.Layout getArrayLayout(Heap heap, IType at, int len) {
        String n = at.getElemType() + "[" + len + "]";
        Heap.Layout layout = heap.newLayout(n, at);
        AbstractToken tok = AbstractToken.newToken("elem", null);
        TypeRef tref = TypeRef.refOf(at.getElemType());
        Field ef = new Field(null, tok, tref);
        // fill up the layout with the elements
        for (int cntr = 0; cntr < len; cntr++) layout.addCell(ef);

        return layout;
    }

    public static Heap.Record allocArray(Heap h, IType at, int sz) {
        return h.allocRecord(at, sz, -1);
    }
}
