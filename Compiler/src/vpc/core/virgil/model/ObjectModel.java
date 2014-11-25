/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Mar 29, 2007
 */
package vpc.core.virgil.model;

import cck.parser.SourcePoint;
import cck.util.*;
import vpc.core.*;
import vpc.core.base.*;
import vpc.core.csr.*;
import vpc.core.decl.*;
import vpc.core.types.Type;
import vpc.core.types.TypeRef;
import vpc.core.virgil.*;
import static vpc.core.virgil.V2TypeSystem.*;
import vpc.tir.*;
import static vpc.tir.TIRUtil.*;
import vpc.tir.expr.Operator;
import vpc.tir.opt.DepthFirstTransformer;
import vpc.tir.opt.TIRCallShape;
import vpc.util.*;
import vpc.hil.Device;

import java.util.LinkedList;
import java.util.List;

/**
 * The <code>ObjectModel</code> interface represents an implementation of a converter
 * for object operations such as accessing fields, comparing against null, accesses
 * arrays, type casts, etc.
 *
 * @author Ben L. Titzer
 */
public class ObjectModel {

    public static final CSRType.Simple INDEX_TYPE = CSRProgram.INT16;
    public static final String METAID_FIELD = "__id";
    public static final String META_FIELD = "__meta";

    public CSRProgram csr;
    public TIRRep rep;
    public DepthFirstTransformer<Object> tf;

    final Options options = new Options();

    final cck.util.Option.Bool SUPPRESS_BOUNDS = options.newOption("suppress-bounds-checks", false,
            "This option is intended for compiler performance testing only. When enabled, this " +
            "option suppresses the generation of explicit array bounds checks in the outputted code, " +
            "resulting in unsafe execution. This option should NOT be used by application " +
            "programmers or designers in order to get better performance.");
    final cck.util.Option.Bool COMPRESS = options.newOption("compress", false,
            "This option selects whether the object model will use reference compression.");
    final cck.util.Option.Bool AVRROM = options.newOption("avr-rom", false,
            "When compression is enabled, this option selects whether the object model will " +
            "place the compression tables in ROM and use the built-in AVR ROM methods to access the " +
            "compressed references.");
    final cck.util.Option.Bool COMPRESS_ARRAYS = options.newOption("compress-array-refs", false,
            "This option selects whether the object model will generate compress array " +
            "references as well as object references. Typically, the savings is small " +
            "unless the program uses many small arrays.");
    final cck.util.Option.Bool VERTICAL = options.newOption("vertical-model", false,
            "This option selects the vertical object model which offers better " +
            "performance under compression.");

    Program program;
    boolean horizontal;
    Compressor compressor;
    Value nullArrayVal;
    Value nullObjectVal;
    Value falseValue;
    CSRType delegateRefType;
    CSRPointer.Val nullMethodVal = new CSRPointer.Val(CSRProgram.VOIDPTR, null);
    CSRStruct.IType delegate;
    int uniquifier;
    Value nullPtrVal;

    public ObjectModel(Program p) {
        program = p;
        csr = p.csr;
    }

    public void processOptions(Options o) {
        options.process(o);
        horizontal = !VERTICAL.get();
    }

    public void processClosure(Closure cl) {
        initValues();
        compressor = new Compressor(program);

        // compute the instance sets and encoding types
        for (Heap.Record record : compressor.program.closure.getRecords()) {
            compressor.getRecordInfo(record);
            compressor.getTypeInfo(record.getType()).instances.add(record);
        }
        // compute the index for each object and the intervals for classes
        computeEncodings(cl);
        // create the meta object tables
        createMetaObjects(cl);
        // declare any necessary global variables
        for (Heap.Record r : compressor.records) declareGlobals(r);
        // instantiate the globals with values
        for (Heap.Record r : compressor.records) instantiateGlobals(r);
    }

    public TIRExpr convertNewObject(TIROperator o) {
        return throwExpr(o.getType(), Heap.AllocationException.class, o.getSourcePoint());
    }

    public TIRExpr convertNewArray(TIROperator o) {
        return throwExpr(o.getType(), Heap.AllocationException.class, o.getSourcePoint());
    }

    public TIRExpr convertArrayInit(TIROperator o) {
        return throwExpr(o.getType(), Heap.AllocationException.class, o.getSourcePoint());
    }

    public TIRExpr convertNullCheck(TIROperator o, Object env) {
        TIRExpr nobj = tf.transform(o.operands, env)[0];
        TIRExpr te = throwExpr(o.getType(), Reference.NullCheckException.class, o.getSourcePoint());
        return $IF($ISNULL(nobj), te, nobj);
    }

    public TIRExpr convertArraySetElement(TIROperator o, Object env) {
        Type atype = o.operands[0].getType();
        CSRStruct.IType cst = getArrayStruct(atype);
        CSRArray.IType car = getCSRArrayType((VirgilArray.IType) atype);
        TIRExpr arr = $DECOMPRESS(atype, cst, $OPERAND(o, env));
        TIRExpr indx = checkIndex(o, env, cst, arr);
        TIRExpr val = tf.transform(o.operands[2], env);
        return $OP(new CSRArray.SetElement(car), $GETARRAY(cst, arr), indx, val);
    }

    public TIRExpr convertArrayGetElement(TIROperator o, Object env) {
        Type atype = o.operands[0].getType();
        CSRStruct.IType cst = getArrayStruct(atype);
        CSRArray.IType car = getCSRArrayType((VirgilArray.IType)atype);
        TIRExpr arr = $DECOMPRESS(atype, cst, $OPERAND(o, env));
        TIRExpr indx = checkIndex(o, env, cst, arr);
        return $OP(new CSRArray.GetElement(car), $GETARRAY(cst, arr), indx);
    }

    public TIRExpr convertTupleGetElement(TIROperator o, Object env) {
        Type ttype = o.operands[0].getType();
        Tuple.GetElement tge = (Tuple.GetElement)o.operator;
        CSRStruct.IType cst = (CSRStruct.IType)csr.encodeType(ttype);
        return $OP(new CSRStruct.GetValueField(cst, cst.fields.get(tge.position)), $OPERAND(o, env));
    }

    public TIRExpr convertArrayGetLength(TIROperator o, Object env) {
        Type atype = o.operands[0].getType();
        CSRStruct.IType cst = getArrayStruct(atype);
        TIRExpr arr = $DECOMPRESS(atype, cst, $OPERAND(o, env));
        return $GETREF(cst, "length", arr);
    }

    public TIRExpr convertComponentGetMethod(TIROperator o, VirgilComponent.GetMethod gm) {
        return $DELEG($VAL(nullObjectVal), $CSRFUNC(gm.method));
    }

    public TIRExpr convertComponentSetField(TIROperator o, VirgilComponent.SetField gf, Object env) {
        return $OP(new CSRData.SetGlobal(infoOf(gf.field).table), $OPERAND(o, env));
    }

    public TIRExpr convertComponentGetField(TIROperator o, VirgilComponent.GetField gf) {
        return $OP(new CSRData.GetGlobal(infoOf(gf.field).table));
    }

    public TIRExpr convertClassGetMethod(TIROperator o, VirgilClass.GetMethod gm, Object env) {
        if ( TIRCallShape.metaDispatch(gm) ) {
            // e.m  =>  {e, e->meta->m}
            TIRExpr ne = $OPERAND(o, env);
            return $DELEG(ne, $GETMETAMETH(gm.thisType, ne, gm.method));
        } else {
            // e.m  =>  {e, #E_m}
            TIRExpr ne = $OPERAND(o, env);
            return $DELEG(ne, $CSRFUNC(gm.method));
        }
    }

    public TIRExpr convertClassSetField(TIROperator o, VirgilClass.SetField op, Object env) {
        if (horizontal) return h_convertClassSetField(o, op, env);
        else return v_convertClassSetField(o, op, env);
    }

    public TIRExpr convertClassGetField(TIROperator o, VirgilClass.GetField op, Object env) {
        if (horizontal) return h_convertClassGetField(o, op, env);
        else return v_convertClassGetField(o, op, env);
    }

    public TIRExpr convertTypeQuery(TIROperator o, VirgilClass.TypeQuery tq, Object env) {
        if (horizontal) return h_convertTypeQuery(o, tq, env);
        else return v_convertTypeQuery(o, tq, env);
    }

    public TIRExpr convertTypeCast(TIROperator o, VirgilClass.TypeCast tc, Object env) {
        if (horizontal) return h_convertTypeCast(o, tc, env);
        else return v_convertTypeCast(o, tc, env);
    }

    public TIRExpr convertCall(TIRCall c, Object env) {
        TIRExpr ref;
        TIRExpr func;
        TIRCallShape.CallShape shape = TIRCallShape.match(program, c);
        CSRFunction.IType ftype = getCVirtualType(c.func.getType());
        switch ( shape.kind ) {
            case TIRCallShape.DIRECT:
                // direct dispatch
                TIRCallShape.Direct direct = (TIRCallShape.Direct)shape;
                if ( direct.method == null ) return throwNull(c);
                ref = tf.transform(direct.thisExpr, env);
                func = $CSRFUNC(direct.method);
                break;
            case TIRCallShape.VIRTUAL:
                // virtual dispatch
                TIRCallShape.Virtual virtual = (TIRCallShape.Virtual)shape;
                if ( virtual.method == null ) return throwNull(c);
                ref = tf.transform(virtual.thisExpr, env);
                func = $GETMETAMETH(virtual.thisType, ref, virtual.method);
                break;
            case TIRCallShape.DEVICE:
                TIRCallShape.Instr i = (TIRCallShape.Instr)shape;
                return $OP(new Device.InstrInvoke(i.instr));
            default: {
                // dynamic dispatch
                TIRExpr deleg = tf.transform(c.func, env);
                Method.Temporary<Type> temp = rep.newTemporary(ftype);
                CSRStruct.IType cst = getDelegateCSRStruct();

                ref = $GETVAL(cst, "ref", deleg);
                func = $IF($ISNULL($SET(temp, $GETVAL(cst, "method", deleg))),
                        throwNull(c),
                        $GET(temp));
            }
        }
        func.setType(ftype);
        return $CALL(false, func, ArrayUtil.prepend(ref, tf.transform(c.arguments, env)));
    }

    public CSRType encodeClassType(VirgilClass.IType t) {
        Compressor.TypeInfo info = compressor.getTypeInfo(t);
        Compressor.TypeInfo root = compressor.getTypeInfo(t.getRootType());
        if (horizontal) {
            if (root.encoding != null) return root.encoding;
            CSRStruct.IType struct = info.struct;
            if ( struct == null ) struct = getClassStruct(t);
            return CSRType.newPointer(csr, struct);
        } else {
            if ( info.encoding == null ) info.encoding = root.encoding;
            return info.encoding;
        }
    }

    public CSRType encodeComponentType(VirgilComponent.IType t) {
        if ( COMPRESS.get() || !horizontal ) return INDEX_TYPE;
        return CSRProgram.VOIDPTR;
    }

    public CSRType encodeArrayType(VirgilArray.IType t) {
        if ( COMPRESS_ARRAYS.get() ) {
            CSRType etype = compressor.getTypeInfo(t).encoding;
            return etype == null ? CSRBitField.getBitType(1) : etype;
        } else {
            return CSRType.newPointer(csr, getArrayStruct(t));
        }
    }

    public CSRType encodeDelegateType(Function.IType t) {
        return getDelegateCSRStruct();
    }

    public Value encodeObjectValue(Value v, VirgilClass.IType t) {
        if ( v == Value.BOTTOM ) return nullObjectVal;
        Heap.Record r = (Heap.Record) v;
        if (horizontal && !COMPRESS.get()) {
            return new CSRPointer.Val(csr.encodeType(r.getType()), globalOf(r));
        } else {
            return adjustIndex(compressor.getRecordInfo(r));
        }
    }

    public Value encodeComponentValue(Value v, VirgilComponent.IType t) {
        return nullObjectVal;
    }

    public Value encodeArrayValue(Value v, VirgilArray.IType t) {
        if ( v == Value.BOTTOM ) return nullArrayVal;
        Heap.Record r = (Heap.Record) v;
        if (!COMPRESS_ARRAYS.get()) {
            return new CSRPointer.Val(csr.encodeType(r.getType()), globalOf(r));
        } else {
            return adjustIndex(compressor.getRecordInfo(r));
        }
    }

    public Value encodeDelegateValue(Value v, Function.IType t) {
        CSRStruct.IType st = getDelegateCSRStruct();
        if ( v == Value.BOTTOM ) {
            CSRStruct.Val stv = new CSRStruct.Val(st, null);
            stv.values = new Value[] {nullObjectVal, nullMethodVal};
            return stv;
        } else {
            if (v instanceof VirgilDelegate.Val) {
                VirgilDelegate.Val d = VirgilDelegate.fromValue(v);
                CSRStruct.Val stv = new CSRStruct.Val(st, null);
                stv.values = new Value[] {
                        csr.encodeValue(d.record, d.record.getType()),
                        new CSRFunction.Val(d.method, getCVirtualType(t)) };
                return stv;
            } else if ( v instanceof Function.Val) {
                Function.Val d = (Function.Val)v;
                return new CSRFunction.Val(d.method, getCVirtualType(t));
            }
        }
        throw Util.failure("cannot encode delegate: "+v);
    }

    String getElemName(CSRType et) {
        if (et == CSRProgram.INT32) return "int";
        if (et == CSRProgram.INT16) return "short";
        if (et == CSRProgram.CHAR) return "char";
        if (et == CSRProgram.BOOL) return "char";
        if (et == CSRProgram.VOIDPTR || et instanceof CSRPointer.IType ) return "ptr";
        if (et instanceof CSRStruct.IType ) return ((CSRStruct.IType)et).shortName;
        if (et == CSRProgram.UINT8) return "raw8";
        if (et == CSRProgram.UINT16) return "raw16";
        if (et == CSRProgram.UINT32) return "raw32";
        if (et == CSRProgram.UINT64) return "raw64";
        if (et instanceof CSRBitField) {
            CSRBitField bf = (CSRBitField)et;
            if ( bf.length <= 8 ) return "raw8";
            if ( bf.length <= 16 ) return "raw16";
            if ( bf.length <= 32 ) return "raw32";
            return "raw64";
        }
        throw Util.failure("unknown array element type: "+et);
    }

    CSRArray.IType getCSRArrayType(VirgilArray.IType at) {
        CSRType et = csr.encodeType(at.getElemType());
        if ( et instanceof CSRPointer.IType ) et = CSRProgram.VOIDPTR;
        return CSRType.newArray(csr, et);
    }

    TIRExpr checkIndex(TIROperator o, Object env, CSRStruct.IType csr, TIRExpr arr) {
        TIRExpr indx = tf.transform(o.operands[1], env);
        if (SUPPRESS_BOUNDS.get()) {
            return indx;
        } else {
            TIRExpr check = $AND($GREQ(indx, $VAL(0)), $LT(indx, $GETREF(csr, "length", arr)));
            return $IF(check, indx, throwExpr(indx.getType(), VirgilArray.BoundsCheckException.class, o.getSourcePoint()));
        }
    }

    TIRExpr throwExpr(Type t, Class<? extends Operator.Exception> except, SourcePoint pt) {
        TIRThrow threxpr = new TIRThrow(except, pt);
        threxpr.setType(t);
        return threxpr;
    }

    TIRExpr throwNull(TIRCall c) {
        return throwExpr(c.getType(), Reference.NullCheckException.class, c.func.getSourcePoint());
    }

    void instantiateComponent(Heap.Record r) {
        Heap.Layout layout = program.getLayout(r);
        for (int cntr = 0; cntr < r.getSize(); cntr++) {
            Value v = r.getValue(cntr);
            Type type = layout.getCellType(cntr);
            Field field = layout.getCellDecl(cntr);
            Value cv = csr.encodeValue(v, type);
            String fname = field.getUniqueName() +"_field";
            infoOf(field).table = csr.newGlobal(null, fname, csr.encodeType(type), cv);
        }
    }

    CSRStruct.IType getDelegateCSRStruct() {
        if (delegate == null) {
            delegate = buildDelegateCSRStruct();
        }
        return delegate;
    }

    CSRStruct.IType buildDelegateCSRStruct() {
        CSRStruct.IType str = csr.newStruct("delegate");
        str.addField("ref", delegateRefType);
        str.addField("method", CSRProgram.VOIDPTR);
        return str;
    }

    CSRFunction.IType getCVirtualType(Type t) {
        Function.IType ft = (Function.IType) t;
        CSRType rtype = csr.encodeType(ft.getResultType());
        LinkedList<CSRType> ptypes = Ovid.newLinkedList();
        ptypes.add(delegateRefType);
        for (Type tn : Tuple.getParameterTypes(ft)) {
            ptypes.add(csr.encodeType(tn));
        }
        return CSRType.newFuncPtr(csr, Tuple.toTuple(program.typeCache, ptypes), rtype);
    }

    CSRStruct.IType newStruct(Type t, String name) {
        return compressor.getTypeInfo(t).struct = csr.getCachedType(new CSRStruct.IType(name));
    }

    CSRStruct.IType buildArrayStruct(VirgilArray.IType c) {
        CSRArray.IType at = getCSRArrayType(c);
        CSRStruct.IType arrstruct = newStruct(c, getElemName(at.elemType) + "_array");
        if (arrstruct.fields.isEmpty()) {
            // only add these fields if the struct has not been filled out yet
            arrstruct.addField("length", CSRProgram.INT32);
            arrstruct.addField("values", at);
        }
        return arrstruct;
    }

    Value[] buildArrayValues(Heap.Record r, Type t, int sz) {
        VirgilArray.IType at = (VirgilArray.IType) t;
        Type et = at.getElemType();
        CSRArray.Val av = new CSRArray.Val(csr.getCachedType(new CSRArray.IType(csr.encodeType(et))), r);
        av.values = new Value[sz];
        for (int cntr = 0; cntr < sz; cntr++) {
            Value v = r.getValue(cntr);
            av.values[cntr] = csr.encodeValue(v, et);
        }
        return new Value[]{csr.encodeValue(PrimInt32.toValue(sz), PrimInt32.TYPE), av};
    }

    CSRStruct.IType getClassStruct(Type t) {
        assert horizontal;
        Compressor.TypeInfo info = compressor.getTypeInfo(t);
        if ( info.struct == null ) {
            // build a new struct for the class
            return buildClassStruct((VirgilClass.IType)t);
        }
        return info.struct;
    }

    CSRStruct.IType getArrayStruct(Type t) {
        Compressor.TypeInfo info = compressor.getTypeInfo(t);
        if ( info.struct == null ) {
            // build a new struct for the class
            return buildArrayStruct((VirgilArray.IType)t);
        }
        return info.struct;
    }

    Value[] buildBasicRecord(Heap.Record r, int sz) {
        Value[] nv = new Value[sz];
        Heap.Layout layout = program.getLayout(r);
        for ( int cntr = 0; cntr < sz; cntr++ ) {
            Value v = r.getValue(cntr);
            nv[cntr] = csr.encodeValue(v, layout.getCellType(cntr));
        }
        return nv;
    }

    int numberMetaObject(int min, VirgilClass cd, boolean root) {
        int max = min;
        List<VirgilClass> children = compressor.getChildren(cd);
        if (!root || !children.isEmpty()) {
            // only create meta objects for non-orphan classes.
            Compressor.TypeInfo info = infoOf(cd);
            max = createMetaTypeInfo(max, info);
            // recursively assign id's for all the children
            for (VirgilClass child : children) max = numberMetaObject(max, child, false);
            // remember the interval for this type and its children
            info.metaTypeInfo.indices = new Interval(min, max);
        }
        return max;
    }

    int createMetaTypeInfo(int max, Compressor.TypeInfo info) {
        assert info.metaTypeInfo == null;
        info.metaTypeInfo = new Compressor.TypeInfo(null);
        if (!info.instances.isEmpty()) {
            // if there are instances of this class, record the need for meta object
            info.metaRecordInfo = new Compressor.RecordInfo();
            info.metaRecordInfo.index = max++;
        }
        return max;
    }

    int numberObjects(int min, VirgilClass cd) {
        Compressor.TypeInfo info = infoOf(cd);
        // assign indices for instances
        int max = numberObjects(min, info);
        // recursively assign id's for all the children
        for (VirgilClass child : compressor.getChildren(cd)) {
            max = numberObjects(max, child);
        }
        // extend interval for this type and its children
        info.indices = new Interval(min, max);
        return max;
    }

    int numberObjects(int min, Compressor.TypeInfo info) {
        int max = min;
        for (Heap.Record r : info.instances) compressor.getRecordInfo(r).index = max++;
        info.indices = new Interval(min, max);
        return max;
    }

    void computeEncoding(Compressor.TypeInfo info) {
        if (horizontal) h_computeEncoding(info);
        else v_computeEncoding(info);
    }

    TIRExpr staticRangeCheck(TIRExpr ne, Interval inst) {
        int size = inst.max - inst.min;
        if ( size == 0 ) return $VAL(falseValue);
        if ( size == 1 ) return $EQ(INDEX_TYPE, ne, $VAL(inst.min + 1));
        if ( size == 2 ) return $OR($EQ(INDEX_TYPE, ne, $VAL(inst.min + 1)), $EQ(INDEX_TYPE, ne, $VAL(inst.min + 2)));
        // TODO: +1 adjustment for null should be factored out
        return $AND($GREQ(ne, $VAL(inst.min + 1)), $LT(ne, $VAL(inst.max + 1)));
    }

    void initValues() {
        if ( horizontal ) h_initValues();
        else v_initValues();
    }

    void createMetaObjects(Closure cl) {
        if (horizontal) h_createMetaObjects();
        else v_createMetaObjects(cl);
    }

    void instantiateGlobals(Heap.Record r) {
        Type type = r.getType();
        if (isClass(type)) instantiateObject(r, type);
        else if (isComponent(type)) instantiateComponent(r);
        else if (isArray(type)) instantiateArray(r);
        else throw Util.failure("unknown record type: "+r+" "+type);
    }

    void instantiateObject(Heap.Record r, Type type) {
        if (horizontal) {
            Compressor.RecordInfo ri = compressor.getRecordInfo(r);
            ri.global.value = new CSRStruct.Val(getClassStruct(type), r, buildObjectValues(r, r.getSize()));
            if ( COMPRESS.get() ) {
                // add the pointer to this object into the ram compression table.
                Compressor.TypeInfo ti = compressor.getTypeInfo(getRootType(type));
                if ( ti.ramtable != null ) {
                    CSRArray.Val av = (CSRArray.Val) ti.ramtable.value;
                    av.values[ri.index] = new CSRPointer.Val(CSRType.newPointer(csr, ri.global.type), ri.global);
                }
            }
        } else v_instantiateObject(r);
    }

    void instantiateArray(Heap.Record r) {
        Type type = r.getType();
        Value[] nv = buildArrayValues(r, type, r.getSize());
        globalOf(r).value = new CSRStruct.Val(getArrayStruct(type), r, nv);
    }

    TIRExpr $GETTABLE(Compressor.FieldInfo info, TIRExpr indx) {
        if (info.table != null) {
            TIROperator op = $OP(new CSRData.GetGlobal(info.table));
            indx = $ADJUST_INDEX(info, indx);
            return $OP(new CSRArray.GetElement((CSRArray.IType) info.table.type), op, indx);
        } else {
            // no objects exist with this field. return BOTTOM.
            return $BLOCK(indx, $VAL(csr.encodeValue(Value.BOTTOM, info.field.getType())));
        }
    }

    TIRExpr $SETTABLE(Compressor.FieldInfo info, TIRExpr indx, TIRExpr val) {
        if (info.table != null) {
            TIROperator op = $OP(new CSRData.GetGlobal(info.table));
            indx = $ADJUST_INDEX(info, indx);
            return $OP(new CSRArray.SetElement((CSRArray.IType) info.table.type), op, indx, val);
        } else {
            // no objects exist with this field. return the value written.
            return $BLOCK(indx, val);
        }
    }

    TIRExpr $ADJUST_INDEX(Compressor.FieldInfo info, TIRExpr indx) {
        int index = info.baseIndex + 1;
        if ( index != 0 )
            indx = $OP(new PrimInt32.SUB(), indx, $VAL(index));
        return indx;
    }

    void declareTable(String name, Compressor.TypeInfo ti) {
        if (COMPRESS.get()) {
            CSRArray.IType at = CSRType.newArray(csr, CSRProgram.VOIDPTR);
            CSRArray.Val av = new CSRArray.Val(at, null);
            av.values = new Value[ti.indices.max];
            CSRData.Global global = csr.newGlobal(null, name + "_table", at, av);
            if ( AVRROM.get() ) global.attributes.add("__progmem__");
            ti.ramtable = global;
        }
    }

    void declareGlobals(Heap.Record r) {
        Type type = r.getType();
        // declare a new global variable
        if (isArray(type) ) {
            compressor.getRecordInfo(r).global = csr.newGlobal(null, "obj_" + r.uid, getArrayStruct(type), null);
        } else if (horizontal && isClass(type)) {
            compressor.getRecordInfo(r).global = csr.newGlobal(null, "obj_" + r.uid, getClassStruct(type), null);
        }
    }

    CSRStruct.IType buildClassStruct(VirgilClass.IType ctype) {
        VirgilClass cd = ctype.getDecl();
        CSRStruct.IType cstruct = newStruct(ctype, "Vc_" + cd.getName());
        Heap.Layout layout = program.closure.getLayout((CompoundDecl) cd);
        if (cstruct.fields.isEmpty()) {
            Compressor.TypeInfo info = compressor.getTypeInfo(ctype);
            if (info.metaTypeInfo != null) {
                cstruct.addField(cd.metaField.getUniqueName(), (CSRType) cd.metaField.getType());
            }
            for (int cntr = 0; cntr < layout.size(); cntr++) {
                Field field = layout.getCellDecl(cntr);
                cstruct.addField(field.getUniqueName(), csr.encodeType(field.getType()));
            }
        }
        return cstruct;
    }

    CSRStruct.Val instantiateMetaClassStruct(Compressor.TypeInfo meta, VirgilClass cdecl) {
        Closure cl = program.closure;
        // create a list of the values in this meta class
        List<Value> vals = Ovid.newList();
        // add the TID field
        if ( !COMPRESS.get() ) {
            meta.struct.addField(METAID_FIELD, CSRProgram.INT8); // TODO: compute correct meta id type
            vals.add(PrimInt32.toValue(meta.indices.min + 1));
        }
        // add all meta fields
        for ( VirgilClass pdecl : cl.hierarchy.getChain(cdecl) ) {
            for ( Method m : pdecl.getMethods() ) {
                Method.Family fam = m.family;
                if (m.isRootOfFamily() && cl.methodFamilies.contains(fam)) {
                    // if this declaration introduced the family and is live
                    CSRFunction.IType vtype = getCVirtualType(m.getType());
                    fam.metaField = new Field(cdecl, cdecl.getDefaultToken(fam.rootMethod.getName()), TypeRef.refOf(vtype));
                    meta.struct.addField(fam.metaField.getUniqueName(), vtype);
                    Method impl = cdecl.resolveMethod(m.getName(), cl);
                    vals.add(csr.encodeValue(new CSRFunction.Val(impl, vtype), vtype));
                }
            }
        }
        return new CSRStruct.Val(meta.struct, null, vals.toArray(new Value[vals.size()]));
    }

    CSRArray.IType typeOfTable(CSRData.Global g) {
        return (CSRArray.IType)g.type;
    }

    boolean isCompressed(Type t) {
        return COMPRESS.get() && (!isArray(t) || COMPRESS_ARRAYS.get());
    }

    String uniquify(String name) {
        return name + uniquifier++;
    }

    Value[] buildObjectValues(Heap.Record r, int sz) {
        Compressor.TypeInfo info = compressor.getTypeInfo(r.getType());
        List<Value> vals = Ovid.newList();
        // add the meta value if necessary
        if ( info.metaRecordInfo != null) vals.add(getRecordValue(info.metaRecordInfo));
        Heap.Layout layout = program.getLayout(r);
        for ( int cntr = 0; cntr < sz; cntr++ ) {
            vals.add(csr.encodeValue(r.getValue(cntr), layout.getCellType(cntr)));
        }
        return vals.toArray(new Value[vals.size()]);
    }

    Value getRecordValue(Compressor.RecordInfo ri) {
        Value nv;
        if ( COMPRESS.get() ) {
            // TODO: factor out +1 adjustment for NULL
            nv = adjustIndex(ri);
        }
        else {
            nv = new CSRPointer.Val(CSRType.newPointer(csr, ri.global.type), ri.global);
        }
        return nv;
    }

    void instantiateVerticalMetaField(Closure cl, Method.Family fam, VirgilClass child, CSRType vtype, Value[] fvals, Compressor.FieldInfo fi) {
        Compressor.TypeInfo cinfo = infoOf(child);
        if (cinfo.metaRecordInfo != null) {
            // if the child class exists, update the value in the table
            Method impl = child.resolveMethod(fam.rootMethod.getName(), cl);
            fvals[cinfo.metaRecordInfo.index - fi.baseIndex] = new CSRFunction.Val(impl, vtype);
        }
    }

    void instantiateVerticalMetaIDs(Compressor.TypeInfo info, Compressor.FieldInfo fi, Value[] fvals) {
        for ( Heap.Record r : info.instances ) {
            Compressor.RecordInfo ri = compressor.getRecordInfo(r);
            fvals[ri.index - fi.baseIndex] = adjustIndex(info.metaRecordInfo);
        }
    }

    boolean buildVerticalFieldArray(Compressor.FieldInfo fi, CSRType ft, Compressor.TypeInfo info) {
        assert info != null;
        if (fi.table == null) {
            Field f = fi.field;
            int size = info.indices.max - info.indices.min;
            if (size > 0) {
                // if there are any instances that have this field
                fi.baseIndex = info.indices.min;
                CSRArray.IType at = csr.getCachedType(new CSRArray.IType(ft));
                CSRArray.Val av = new CSRArray.Val(at, null);
                av.values = new Value[size];
                fi.table = csr.newGlobal(null, f.getCompoundDecl().getName()+"_"+ f.getUniqueName() + "_table", at, av);
                return true;
            }
            return false;
        }
        return true;
    }

    void v_initValues() {
        falseValue = csr.encodeValue(PrimBool.FALSE, PrimBool.TYPE);

        nullObjectVal = PrimInt32.toValue(0);
        nullArrayVal = new CSRPointer.Val(CSRProgram.VOIDPTR, null);
        delegateRefType = INDEX_TYPE;
    }

    void v_createMetaObjects(Closure cl) {
        for (VirgilClass cd : cl.hierarchy.getRoots()) {
            Compressor.TypeInfo info = infoOf(cd);
            Compressor.TypeInfo meta = info.metaTypeInfo;
            if (meta != null) {
                // build the metaobject type and instance
                cd.metaField = new Field(cd, cd.getDefaultToken(META_FIELD), TypeRef.refOf(meta.encoding));
                Compressor.FieldInfo fi = infoOf(cd.metaField);
                // build and initialize the field table for meta IDs
                assert meta.encoding != null;
                if ( buildVerticalFieldArray(fi, meta.encoding, info) ) {
                    Value[] fvals = ((CSRArray.Val)fi.table.value).values;
                    instantiateVerticalMetaIDs(info, fi, fvals);
                    for ( VirgilClass child : cl.hierarchy.getDescendants(cd) ) {
                        instantiateVerticalMetaIDs(infoOf(child), fi, fvals);
                        child.metaField = cd.metaField;
                    }
                }
            }
        }
        for ( Method.Family fam : cl.methodFamilies ) {
            // build a vertical layout for every method family
            VirgilClass cd = (VirgilClass)fam.rootMethod.getCompoundDecl();
            Compressor.TypeInfo info = infoOf(cd);
            Compressor.TypeInfo meta = info.metaTypeInfo;
            CSRType vtype = getCVirtualType(fam.rootMethod.getType());
            fam.metaField = new Field(cd, cd.getDefaultToken(fam.rootMethod.getName()), TypeRef.refOf(vtype));
            Compressor.FieldInfo fi = infoOf(fam.metaField);
            // build and initialize the field table
            if (buildVerticalFieldArray(fi, vtype, meta) ) {
                Value[] fvals = ((CSRArray.Val)fi.table.value).values;
                instantiateVerticalMetaField(cl, fam, cd, vtype, fvals, fi);
                for ( VirgilClass child : cl.hierarchy.getDescendants(cd) ) {
                    // fill in the entries for each descendant class
                    instantiateVerticalMetaField(cl, fam, child, vtype, fvals, fi);
                }
            }
        }
    }

    void v_computeEncoding(Compressor.TypeInfo info) {
        if ( info == null ) return;
        if ( COMPRESS.get() ) {
            info.encoding = compressor.getBitType(info);
        } else {
            info.encoding = INDEX_TYPE;
        }
    }

    void v_instantiateObject(Heap.Record r) {
        int size = r.getSize();
        int index = compressor.getRecordInfo(r).index;
        Heap.Layout layout = program.getLayout(r);
        for (int cntr = 0; cntr < size; cntr++) {
            Type ftype = layout.getCellType(cntr);
            Field field = layout.getCellDecl(cntr);
            Compressor.FieldInfo fi = infoOf(field);
            CompoundDecl decl = fi.field.getCompoundDecl();
            Compressor.TypeInfo ti = compressor.getTypeInfo(decl);
            CSRType tableType = csr.encodeType(ftype);
            buildVerticalFieldArray(fi, tableType, ti);
            if (fi.table != null) {
                // if the field table exists
                Value[] fv = ((CSRArray.Val) fi.table.value).values;
                fv[index - fi.baseIndex] = csr.encodeValue(r.getValue(cntr), ftype);
            }
        }
    }

    TIRExpr v_convertClassSetField(TIROperator o, VirgilClass.SetField op, Object env) {
        TIRExpr ne = $OPERAND(o, env);
        TIRExpr val = tf.transform(o.operands[1], env);
        return $SETTABLE(infoOf(op.field), ne, val);
    }

    TIRExpr v_convertClassGetField(TIROperator o, VirgilClass.GetField op, Object env) {
        TIRExpr ne = $OPERAND(o, env);
        return $GETTABLE(infoOf(op.field), ne);
    }

    TIRExpr v_convertTypeQuery(TIROperator o, VirgilClass.TypeQuery tq, Object env) {
        VirgilClass.IType ttype = tq.target;
        TIRExpr e = o.operands[0];
        TIRExpr ne = tf.transform(e, env);
        Compressor.TypeInfo info = compressor.getTypeInfo(ttype);
        if (info.metaTypeInfo == null) {
            // orphan:  e <: T  =>  e != null
            return $NEQ(INDEX_TYPE, ne, $VAL(nullObjectVal));
        }
        // nonleaf:  e <: T  =>  e >= min && e < max;
        return staticRangeCheck(ne, info.indices);
    }

    TIRExpr v_convertTypeCast(TIROperator o, VirgilClass.TypeCast tc, Object env) {
        VirgilClass.IType ttype = tc.target;
        TIRExpr e = o.operands[0];
        TIRExpr ne = tf.transform(e, env);
        Compressor.TypeInfo info = compressor.getTypeInfo(ttype);
        if (info.metaTypeInfo == null) {
            // orphan:  e :: T  =>  e
            return ne;
        }
        // nonleaf:  e :: T  =>  if ( e == null || e >= min && e < max ) e; else $except
        TIRExpr ok = $OR($EQ(INDEX_TYPE, ne, $VAL(nullObjectVal)), staticRangeCheck(ne, compressor.getTypeInfo(ttype).indices));
        TIRExpr check = $IF(ok, ne, throwExpr(ne.getType(), VirgilClass.TypeCheckException.class, o.getSourcePoint()));
        check.setType(ttype);
        return check;
    }

    Value v_encodeArrayValue(Value v, VirgilArray.IType t) {
        if (v == Value.BOTTOM) return nullArrayVal;
        Heap.Record r = (Heap.Record) v;
        return new CSRPointer.Val(csr.encodeType(r.getType()), globalOf(r));
    }

    void h_initValues() {
        nullPtrVal = new CSRPointer.Val(CSRProgram.VOIDPTR, null);
        Value nullIndxVal = PrimInt32.toValue(0);
        falseValue = csr.encodeValue(PrimBool.FALSE, PrimBool.TYPE);

        if ( COMPRESS.get() ) {
            if ( AVRROM.get() )
                program.csr.includes.add("avr/pgmspace.h");
            nullObjectVal = nullIndxVal;
            if ( COMPRESS_ARRAYS.get() ) nullArrayVal = nullIndxVal;
            else nullArrayVal = nullPtrVal;
            delegateRefType = CSRProgram.UINT16;
        } else {
            nullObjectVal = nullArrayVal = nullPtrVal;
            delegateRefType = CSRProgram.VOIDPTR;
        }
    }

    void h_computeEncoding(Compressor.TypeInfo info) {
        if ( info != null && COMPRESS.get() ) {
            info.encoding = compressor.getBitType(info);
            if ( info.ramtable == null ) declareTable(uniquify("reftable"), info);
        }
    }

    void h_createMetaObjects() {
        List<VirgilClass> mlist = Ovid.newList();
        // create all the meta class structs first.
        for ( VirgilClass cd : program.closure.classes ) {
            Compressor.TypeInfo info = infoOf(cd);
            Compressor.TypeInfo meta = info.metaTypeInfo;
            if (meta != null) {
                mlist.add(cd); // remember the class for the next loop
                Compressor.TypeInfo root = infoOf(program.closure.getRootClass(cd));
                // build the metaobject type and instance
                String metaname = "Vm_" + VirgilClass.declOf(info.type).getName() + "_meta";
                meta.struct = csr.getCachedType(new CSRStruct.IType(metaname));
                CSRType csrType;
                if ( root.metaTypeInfo.encoding != null ) csrType = root.metaTypeInfo.encoding;
                else csrType = CSRType.newPointer(csr, meta.struct);
                cd.metaField = new Field(cd, cd.getDefaultToken(META_FIELD), TypeRef.refOf(csrType));
            }
        }
        // now create instances and populate the live meta objects.
        for ( VirgilClass cd : mlist ) {
            Compressor.TypeInfo info = infoOf(cd);
            Compressor.TypeInfo root = infoOf(program.closure.getRootClass(cd));
            // build the metaobject type and instance
            CSRStruct.Val val = instantiateMetaClassStruct(info.metaTypeInfo, cd);
            Compressor.RecordInfo ri = info.metaRecordInfo;
            if (ri != null) {
                // save the instance in a global variable
                String name = "Vm_" + cd.getName() + "_meta_val";
                ri.global = csr.newGlobal(null, name, val.type, val);
                if (root.metaTypeInfo.ramtable != null) {
                    CSRArray.Val av = (CSRArray.Val) root.metaTypeInfo.ramtable.value;
                    av.values[ri.index] = new CSRPointer.Val(CSRType.newPointer(csr, ri.global.type), ri.global);
                }
            }
        }
    }

    TIRExpr h_convertClassSetField(TIROperator o, VirgilClass.SetField op, Object env) {
        CSRStruct.IType cst = getClassStruct(op.thisType);
        TIRExpr ref = $DECOMPRESS(o.operands[0].getType(), cst, $OPERAND(o, env));
        return $SETREF(cst, op.field, ref, tf.transform(o.operands[1], env));
    }

    TIRExpr h_convertClassGetField(TIROperator o, VirgilClass.GetField op, Object env) {
        CSRStruct.IType cst = getClassStruct(op.thisType);
        TIRExpr ref = $DECOMPRESS(o.operands[0].getType(), cst, $OPERAND(o, env));
        return $GETREF(cst, op.field, ref);
    }

    TIRExpr h_convertTypeQuery(TIROperator o, VirgilClass.TypeQuery tq, Object env) {
        VirgilClass.IType ttype = tq.target;
        TIRExpr e = o.operands[0];
        TIRExpr ne = tf.transform(e, env);
        Compressor.TypeInfo info = compressor.getTypeInfo(ttype);
        if (info.metaTypeInfo == null) {
            // orphan:  e <: T  =>  e != null
            return $ISNOTNULL(ne);
        }
        // nonleaf:  e <: T  =>  e != null && rangeCheck(e->meta->tid)
        TIRExpr mid = h_$GETTID((VirgilClass.IType) e.getType(), ne);
        return $AND($ISNOTNULL(ne), staticRangeCheck(mid, info.metaTypeInfo.indices));
    }

    TIRExpr h_convertTypeCast(TIROperator o, VirgilClass.TypeCast tc, Object env) {
        VirgilClass.IType ttype = tc.target;
        TIRExpr e = o.operands[0];
        TIRExpr ne = tf.transform(e, env);
        Compressor.TypeInfo info = compressor.getTypeInfo(ttype);
        if (info.metaTypeInfo == null) {
            // orphan:  e :: T  =>  e
            return ne;
        }
        // nonleaf:  e :: T  =>  if ( e == null || rangeCheck(e->meta->tid) e; else $except;
        TIRExpr mid = h_$GETTID((VirgilClass.IType) e.getType(), ne);
        TIRExpr ok = $OR($ISNULL(ne), staticRangeCheck(mid, info.metaTypeInfo.indices));
        TIRExpr check = $IF(ok, ne, throwExpr(ne.getType(), VirgilClass.TypeCheckException.class, o.getSourcePoint()));
        check.setType(ttype);
        return check;
    }

    void computeEncodings(Closure cl) {
        for (VirgilClass cd : cl.hierarchy.getRoots()) {
            Compressor.TypeInfo info = infoOf(cd);
            numberObjects(0, cd);
            computeEncoding(info);
            numberMetaObject(0, cd, true);
            computeEncoding(info.metaTypeInfo);
        }
        if ( COMPRESS_ARRAYS.get() ) {
            for (Compressor.TypeInfo ti : compressor.typeInfo.values()) {
                if ( isArray(ti.type) ) {
                    numberObjects(0, ti);
                    computeEncoding(ti);
                }
            }
        }
    }

    PrimInt32.Val adjustIndex(Compressor.RecordInfo ri) {
        return PrimInt32.toValue(ri.index + 1);
    }

    Compressor.FieldInfo infoOf(Field field) {
        return compressor.getFieldInfo(field);
    }

    Compressor.TypeInfo infoOf(VirgilClass cd) {
        return compressor.getTypeInfo(cd);
    }

    CSRData.Global globalOf(Heap.Record r) {
        return compressor.getRecordInfo(r).global;
    }

    TIRExpr $OPERAND(TIROperator o, Object env) {
        return tf.transform(o.operands[0], env);
    }

    TIROperator $GETARRAY(CSRStruct.IType csr, TIRExpr a) {
        return $GETREF(csr, "values", a);
    }

    TIROperator $SETREF(CSRStruct.IType cst, Field fn, TIRExpr... no) {
        CSRPointer.IType ptr = CSRType.newPointer(csr, cst);
        return $OP(new CSRStruct.SetRefField(ptr, cst.getField(fn.getUniqueName())), no);
    }

    TIROperator $GETREF(CSRStruct.IType cst, Field field, TIRExpr... no) {
        CSRPointer.IType ptr = CSRType.newPointer(csr, cst);
        return $OP(new CSRStruct.GetRefField(ptr, cst.getField(field.getUniqueName())), no);
    }

    TIROperator $GETREF(CSRStruct.IType cst, String field, TIRExpr... no) {
        CSRStruct.IType.Field csrfield = cst.getField(field);
        return $OP(new CSRStruct.GetRefField(CSRType.newPointer(csr, cst), csrfield), no);
    }

    TIROperator $GETVAL(CSRStruct.IType cst, String field, TIRExpr... no) {
        return $OP(new CSRStruct.GetValueField(cst, cst.getField(field)), no);
    }

    TIRExpr $CSRFUNC(Method m) {
        return $VAL(new CSRFunction.Val(m, getCVirtualType(m.getType())));
    }

    TIROperator $DELEG(TIRExpr oref, TIRExpr mref) {
        return $OP(new CSRStruct.NewValue(getDelegateCSRStruct()), oref, mref);
    }

    TIRExpr $GETMETAMETH(VirgilClass.IType thisType, TIRExpr thisExpr, Method method) {
        if (horizontal) {
            Compressor.TypeInfo info = compressor.getTypeInfo(thisType);
            CSRStruct.IType mcsr = info.metaTypeInfo.struct;
            return $GETREF(mcsr, method.family.metaField, h_$GETMETA(thisType, thisExpr, mcsr));
        } else {
            TIRExpr tid = $GETTABLE(infoOf(thisType.getDecl().metaField), thisExpr);
            return $GETTABLE(infoOf(method.family.metaField), tid);
        }
    }

    TIRExpr h_$DECOMPRESS(boolean compressed, Compressor.TypeInfo info, CSRStruct.IType cst, TIRExpr ne) {
        if ( !compressed ) return ne;
        CSRPointer.IType csrptr = CSRType.newPointer(csr, cst);
        if (info.ramtable != null) {
            CSRArray.IType at = typeOfTable(info.ramtable);
            TIRExpr adjust = $OP(new PrimInt32.SUB(), ne, $VAL(1));
            TIRExpr access;
            if (AVRROM.get()) {
                // call the built-in method pgm_read_word with the computed address
                TIROperator addr = $OP(new PrimInt32.ADD(), $OP(new CSRData.GetGlobal(info.ramtable)), adjust);
                access = $OP(new CSRFunction.Extern(csrptr, "pgm_read_word"), addr);
            } else {
                // otherwise just access the table with an array element operation
                access = $OP(new CSRArray.GetElement(at), $OP(new CSRData.GetGlobal(info.ramtable)), adjust);
            }
            return $OP(new CSRType.Coerce(at.elemType, csrptr), access);
        } else {
            // TODO: just return ne
            // there is no ram table for this type; just return null
            return $BLOCK(ne, $VAL(new CSRPointer.Val(csrptr, null)));
        }
    }

    TIROperator h_$GETTID(VirgilClass.IType ctype, TIRExpr ne) {
        Compressor.TypeInfo info = compressor.getTypeInfo(ctype);
        CSRStruct.IType mcsr = info.metaTypeInfo.struct;
        if (COMPRESS.get()) {
            CSRStruct.IType cst = getClassStruct(ctype);
            return $GETREF(cst, META_FIELD, $DECOMPRESS(ctype, cst, ne));
        } else {
            return $GETREF(mcsr, METAID_FIELD, h_$GETMETA(ctype, ne, mcsr));
        }
    }

    TIRExpr h_$GETMETA(VirgilClass.IType ctype, TIRExpr ne, CSRStruct.IType mcsr) {
        Compressor.TypeInfo root = compressor.getTypeInfo(getRootType(ctype));
        CSRStruct.IType cst = getClassStruct(ctype);
        TIROperator mindex = $GETREF(cst, META_FIELD, $DECOMPRESS(ctype, cst, ne));
        return h_$DECOMPRESS(COMPRESS.get(), root.metaTypeInfo, mcsr, mindex);
    }

    TIRExpr $DECOMPRESS(Type type, CSRStruct.IType cst, TIRExpr ne) {
        if (horizontal) return h_$DECOMPRESS(isCompressed(type), compressor.getTypeInfo(getRootType(type)), cst, ne);
        else return ne;
    }
}
