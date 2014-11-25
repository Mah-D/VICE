/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Apr 12, 2007
 */

package vpc.core.virgil.model;

import cck.util.Arithmetic;
import vpc.core.Heap;
import vpc.core.Program;
import vpc.core.csr.*;
import vpc.core.decl.CompoundDecl;
import vpc.core.decl.Field;
import vpc.core.types.Type;
import vpc.core.virgil.VirgilClass;
import vpc.util.*;

import java.util.*;

/**
 * @author Ben L. Titzer
 */
public class Compressor {

    protected static class TypeInfo {
        final Type type;
        final Set<Heap.Record> instances;
        Interval indices;
        CSRStruct.IType struct;
        CSRType encoding;
        CSRData.Global ramtable;

        TypeInfo metaTypeInfo;
        RecordInfo metaRecordInfo;

        TypeInfo(Type t) {
            type = t;
            instances = Ovid.newSet();
            indices = new Interval(0, 0);
        }
    }

    protected static class RecordInfo {
        CSRData.Global global;
        int index;
    }

    protected static class FieldInfo {
        final Field field;
        CSRData.Global table;
        int baseIndex;

        FieldInfo(Field f) {
            field = f;
        }
    }

    protected final List<Heap.Record> records = Ovid.newList();
    protected final Map<Type, TypeInfo> typeInfo = Ovid.newMap();
    protected final Map<Heap.Record, RecordInfo> recordInfo = Ovid.newMap();
    protected final Map<Field, FieldInfo> fieldInfo = Ovid.newMap();
    protected final Program program;
    protected final Hierarchy<VirgilClass> hierarchy;
    
    public Compressor(Program p) {
        program = p;
        hierarchy = p.closure.hierarchy;
    }

    TypeInfo getTypeInfo(CompoundDecl d) {
        return getTypeInfo(d.getCanonicalType());
    }

    TypeInfo getTypeInfo(Type t) {
        TypeInfo ti = typeInfo.get(t);
        if (ti == null) {
            ti = new TypeInfo(t);
            typeInfo.put(t, ti);
        }
        return ti;
    }

    RecordInfo getRecordInfo(Heap.Record r) {
        RecordInfo ti = recordInfo.get(r);
        if (ti == null) {
            ti = new RecordInfo();
            recordInfo.put(r, ti);
            records.add(r);
        }
        return ti;
    }

    FieldInfo getFieldInfo(Field f) {
        FieldInfo fi = fieldInfo.get(f);
        if (fi == null) {
            fi = new FieldInfo(f);
            fieldInfo.put(f, fi);
        }
        return fi;
    }

    protected List<VirgilClass> getChildren(VirgilClass cd) {
        List<VirgilClass> list = hierarchy.getChildren(cd);
        return list == null ? (List<VirgilClass>)Collections.EMPTY_LIST : list;
    }

    CSRBitField getBitType(TypeInfo ti) {
        int count = ti.indices.max - ti.indices.min + 1;
        int log = count <= 1 ? 1 : Arithmetic.log(count);
        return CSRBitField.getBitType(log);
    }

}
