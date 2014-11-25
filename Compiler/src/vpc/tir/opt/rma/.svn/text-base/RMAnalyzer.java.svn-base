/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 28, 2006
 */

package vpc.tir.opt.rma;

import cck.text.Printer;
import vpc.core.*;
import vpc.core.base.Function;
import vpc.core.base.Reference;
import vpc.core.decl.*;
import vpc.core.types.Type;
import vpc.core.virgil.*;
import static vpc.core.virgil.V2TypeSystem.*;
import vpc.tir.*;
import vpc.tir.expr.Operator;
import vpc.tir.opt.DepthFirstOpVisitor;
import vpc.util.Ovid;

import java.util.*;

/**
 * The <code>RMAnalyzer</code> class implements Reachable Members Analysis (RMA),
 * which is similar to Rapid Type Analysis, but instead of reachable types,
 * this analysis additionally computes which members of which components and
 * classes are accessible.
 *
 * @author Ben L. Titzer
 */
public class RMAnalyzer implements RMResults {

    protected boolean debug;
    protected Analyzer analyzer;
    protected Printer printer;
    protected Program program;
    protected LinkedList<Unit> worklist;
    protected Map<Type, TypeInfo> typeInfo;
    protected Map<Method, MemberInfo<Method>> methodInfo;
    protected Map<Field, MemberInfo<Field>> fieldInfo;
    protected Set<Method> liveMethods;
    protected Set<Heap.Record> liveRecords;

    public class MemberInfo<M extends Member> {

        public final M member;
        public boolean read;
        public boolean written;
        public int numValues;
        public Value value;

        protected MemberInfo(M m) {
            member = m;
        }

        public void addValue(Value v) {
            if ( numValues == 0 ) {
                // no values seen yet.
                value = v;
                numValues++;
            } else if ( numValues == 1 ) {
                if (Value.compareValues(value, v)) {
                    // if values are equal, and the new one is not BOTTOM
                    if ( v != Value.BOTTOM ) value = v;
                } else {
                    // otherwise, there is more than one value
                    numValues++;
                }
            }
        }

        public boolean isReadOnly() {
            return !written;
        }

        public boolean isWriteOnly() {
            return !read;
        }

        public boolean isConstant() {
            return !written && numValues <= 1;
        }
    }

    /**
     * The <code>TypeInfo</code> class represents the information tracked for a
     * particular type, which includes its instantiated subtypes, live members,
     * live instances, and references to the new layout and meta layouts.
     */
    protected class TypeInfo {
        protected final TypeInfo parent;      // super type (if any)
        protected final Type type;            // this type
        protected final Set<Type> subtypes;   // instantiated subtypes
        protected final Set<Field> fields;    // fields read on this static type
        protected final Set<Method> methods;  // used methods
        protected Set<Heap.Record> instances; // live instances

        protected TypeInfo(Type t, TypeInfo p) {
            parent = p;
            type = t;
            subtypes = Ovid.newSet();
            fields = Ovid.newSet();
            methods = Ovid.newSet();
        }
    }

    /**
     * The <code>Unit</code> class represents a unit of work for the analysis.
     */
    protected abstract static class Unit {
        protected abstract boolean isDone();
        protected abstract void analyze();
    }

    /**
     * The <code>InstUnit</code> class represents a unit of work for a newly discovered
     * (reachable) record instance.
     * PENDING/DONE: if this record exists in the live record list of its type
     * ACTION: analyze each used field in the record and post values as necessary
     */
    protected class InstUnit extends Unit {
        private final Heap.Record record;

        protected InstUnit(Heap.Record r) {
            record = r;
        }

        protected boolean isDone() {
            if (record == null) return true;
            TypeInfo info = getTypeInfo(record.getType());
            post(new TypeUnit(info));
            return !liveRecords.add(record);
        }

        protected void analyze() {
            Type type = record.getType();
            TypeInfo info = getTypeInfo(type);
            info.instances.add(record);
            if (isArray(type)) {
                // if this an array, (conservatively) assume all members are reachable
                for (int cntr = 0; cntr < record.getSize(); cntr++)
                    postValue(record.getValue(cntr));
            } else {
                // record the values of fields of this record that are used
                for (Field f : info.fields) {
                    analyzeFieldOfRecord(record, getFieldInfo(f));
                }
            }
        }

        public String toString() {
            return "Record(" + record + ")";
        }
    }

    /**
     * The <code>MethodImpl</code> class represents a unit of work for a newly discovered
     * (reachable) method.
     * PENDING/DONE: if this method exists in the global <code>visitedMethods</code> list
     * ACTION: analyze the code of the method to discover member uses
     */
    protected class MethodImpl extends Unit {
        private final Method method;

        protected MethodImpl(Method m) {
            method = m;
        }

        protected boolean isDone() {
            return !liveMethods.add(method);
        }

        protected void analyze() {
            getMethodInfo(method).addValue(new Function.Val(method));
            TIRUtil.getRep(method).getBody().accept(analyzer, null);
        }

        public String toString() {
            return "Method(" + method.getFullName() + ")";
        }
    }

    /**
     * The <code>TypeUnit</code> class represents a unit of work for a newly discovered
     * (reachable) type.
     * PENDING/DONE: if the type info has a non-null list of record instances
     * ACTION: add this type to all supertypes' subtype lists; post parent's used members
     * on this type
     */
    protected class TypeUnit extends Unit {
        final TypeInfo typeInfo;

        TypeUnit(TypeInfo ti) {
            typeInfo = ti;
        }

        protected boolean isDone() {
            if (typeInfo.instances != null) return true;
            typeInfo.instances = Ovid.newSet();
            return false;
        }

        protected void analyze() {
            // add this to the live subtypes of each of the parent types
            for (TypeInfo info = typeInfo; info != null; info = info.parent) {
                info.subtypes.add(typeInfo.type);
                // for each member in each parent, post that member for this type
                for (Field f : info.fields)
                    post(new FieldRead(getTypeInfo(typeInfo.type), f));
                for (Method m : info.methods)
                    post(new MethodUse(getTypeInfo(typeInfo.type), m));
            }
        }

        public String toString() {
            return "Type(" + typeInfo.type + ")";
        }
    }

    /**
     * The <code>FieldRead</code> class represents a unit of work for a newly discovered
     * used member on a particular type.
     * PENDING/DONE: if the type info already contains the member in its members list
     * ACTION: for all records of this type, inspect this field; post this member for all
     * live subtypes of this type
     */
    protected class FieldRead extends Unit {
        final TypeInfo info;
        final MemberInfo<Field> field;

        FieldRead(TypeInfo ti, Field f) {
            info = ti;
            field = getFieldInfo(f);
        }

        protected boolean isDone() {
            field.read = true;
            return !info.fields.add(field.member);
        }

        protected void analyze() {
            if (info.instances != null) {
                for (Heap.Record r : info.instances) {
                    analyzeFieldOfRecord(r, field);
                }
            }
            // repost the field use to all live subtypes
            for (Type t : info.subtypes) {
                post(new FieldRead(getTypeInfo(t), field.member));
            }
        }

        public String toString() {
            return "FieldRead(" + info.type + "," + field.member.getName() + ")";
        }
    }

    /**
     * The <code>FieldWrite</code> class represents a unit of work for a newly discovered
     * used member on a particular type.
     * PENDING/DONE: if the type info already contains the member in its members list
     * ACTION: for all records of this type, inspect this field; post this member for all
     * live subtypes of this type
     */
    protected class FieldWrite extends Unit {
        final TypeInfo info;
        final MemberInfo<Field> field;

        FieldWrite(TypeInfo ti, Field f) {
            info = ti;
            field = getFieldInfo(f);
        }

        protected boolean isDone() {
            field.written = true;
            return true;
        }

        protected void analyze() {
            // do nothing.
        }

        public String toString() {
            return "FieldWrite(" + info.type + "," + field.member.getName() + ")";
        }
    }

    /**
     * The <code>MethodUse</code> class represents a unit of work for a newly discovered
     * used member on a particular type.
     * PENDING/DONE: if the type info already contains the member in its members list
     * ACTION: for all records of this type, inspect this field; post this member for all
     * live subtypes of this type
     */
    protected class MethodUse extends Unit {
        final TypeInfo info;
        final Method method;

        MethodUse(TypeInfo ti, Method m) {
            info = ti;
            method = m;
        }

        protected boolean isDone() {
            return !info.methods.add(method);
        }

        protected void analyze() {
            VirgilClass.IType ct = (VirgilClass.IType) info.type;
            Method impl = ct.getDecl().resolveMethod(method.getName(), program.closure);
            post(new MethodImpl(impl));
            for (Type t : info.subtypes) {
                post(new MethodUse(getTypeInfo(t), method));
            }
        }

        public String toString() {
            return "MethodUse(" + info.type + "," + method.getName() + ")";
        }
    }


    /**
     * The <code>analyzeProgram()</code> method begins the analysis by starting at the entrypoints
     * of the program and iteratively finding the reachable code and data.
     *
     * @param p the program to analyze
     */
    public void analyzeProgram(Program p) {
        analyzer = new Analyzer();
        ProgramDecl pd = p.programDecl;
        program = p;
        if (pd != null) { // only run the analysis if the entrypoints are known
            // build a cache of live (reachable) methods
            liveMethods = Ovid.newSet();
            liveRecords = Ovid.newSet();
            typeInfo = Ovid.newMap();
            fieldInfo = Ovid.newMap();
            methodInfo = Ovid.newMap();
            worklist = Ovid.newLinkedList();
            // get the entry points
            getEntryPoints(pd);
            // analyze all the units of work
            for (Unit u = worklist.poll(); u != null; u = worklist.poll()) {
                if (debug) printer.startblock(u.toString());
                u.analyze();
                if (debug) printer.endblock();
            }
        }
    }

    private void getEntryPoints(ProgramDecl pd) {
        for (ProgramDecl.EntryPoint ept : pd.entryPoints) {
            VirgilComponent d = (VirgilComponent)ept.method.getCompoundDecl();
            post(new InstUnit(d.getRecord()));
            post(new MethodImpl(ept.method));
        }
    }

    private void postValue(Value v) {
        if (v instanceof Heap.Record) {
            post(new InstUnit((Heap.Record)v));
        } else if (v instanceof VirgilDelegate.Val) {
            VirgilDelegate.Val d = VirgilDelegate.fromValue(v);
            post(new InstUnit(d.record));
            post(new MethodImpl(d.method));
        }
    }

    private void post(Unit u) {
        if (u.isDone()) {
            if (debug) printer.println("- " + u);
        } else {
            worklist.add(u);
            if (debug) printer.println("+ " + u);
        }
    }

    protected TypeInfo getTypeInfo(Type t) {
        TypeInfo u = typeInfo.get(t);
        if (u != null) return u;

        TypeInfo parent = null;
        if (isClass(t)) {
            // if it is an object type, get its parent info
            VirgilClass.IType pn = ((VirgilClass.IType) t).getParentType();
            if (pn != null) parent = getTypeInfo(pn);
        }
        if (isClass(t) || isArray(t) || isComponent(t)) {
            // if it is a member type
            u = new TypeInfo(t, parent);
            typeInfo.put(t, u);
            return u;
        }
        return null;
    }

    protected MemberInfo<Field> getFieldInfo(Field f) {
        MemberInfo<Field> u = fieldInfo.get(f);
        if (u != null) return u;
        u = new MemberInfo<Field>(f);
        fieldInfo.put(f, u);
        return u;
    }

    protected MemberInfo<Method> getMethodInfo(Method m) {
        if ( m.family != null ) m = m.family.rootMethod;
        MemberInfo<Method> u = methodInfo.get(m);
        if (u != null) return u;
        u = new MemberInfo<Method>(m);
        methodInfo.put(m, u);
        return u;
    }

    private void analyzeFieldOfRecord(Heap.Record r, MemberInfo<Field> finfo) {
        Value val = r.getValue(finfo.member.fieldIndex);
        finfo.addValue(val);
        postValue(val);
    }

    /**
     * The <code>RMAnalyzer.Operator</code> class implements a visitor that performs specific
     * actions on the operations in the program that correspond to accessing fields
     * and invoking methods.
     */
    protected class OpVisitor implements VirgilClass.OpVisitor<Void, TIRExpr>,
                                            VirgilComponent.OpVisitor<Void, TIRExpr> {

        public Void visit(VirgilClass.GetField f, TIRExpr... e) {
            post(new FieldRead(getTypeInfo(f.thisType), f.field));
            return null;
        }

        public Void visit(VirgilClass.SetField f, TIRExpr... e) {
            post(new FieldWrite(getTypeInfo(f.thisType), f.field));
            return null;
        }

        public Void visit(VirgilClass.GetMethod f, TIRExpr... e) {
            if ( f.virtual )
                post(new MethodUse(getTypeInfo(f.thisType), f.method));
            else
                post(new MethodImpl(f.method));
            return null;
        }

        public Void visit(VirgilComponent.GetField f, TIRExpr... e) {
            Type type = f.component.getCanonicalType();
            post(new InstUnit(f.component.getRecord()));
            post(new FieldRead(getTypeInfo(type), f.field));
            return null;
        }

        public Void visit(VirgilComponent.SetField f, TIRExpr... e) {
            Type type = f.component.getCanonicalType();
            post(new InstUnit(f.component.getRecord()));
            post(new FieldWrite(getTypeInfo(type), f.field));
            return null;
        }

        public Void visit(VirgilComponent.GetMethod f, TIRExpr... e) {
            post(new InstUnit(f.component.getRecord()));
            post(new MethodImpl(f.method));
            return null;
        }

        public Void visit(Operator o, TIRExpr... e) {
            // default behavior is to simply do nothing
            return null;
        }
    }

    protected class Analyzer extends DepthFirstOpVisitor<Void, Object> {
        protected Analyzer() {
            super(new OpVisitor());
        }

        public Void visit(TIRConst.Value c, Object env) {
            postValue(c.getValue());
            return null;
        }

        public Void visit(TIRConst.Symbol c, Object env) {
            // TODO: deal with symbols by posting the heap record.
            return null;
        }
    }

    public Program getProgram() {
        return program;
    }

    public Heap getHeap() {
        return program.heap;
    }

    public Set<Method> getLiveMethods() {
        return liveMethods;
    }

    public Set<Heap.Record> getLiveRecords() {
        return liveRecords;
    }

    public boolean isWriteOnly(Field sf) {
        return getFieldInfo(sf).isWriteOnly();
    }

    public Value getConstantMethodValue(Method method) {
        return getMethodInfo(method).value;
    }

    public boolean isConstantMethod(Method method) {
        return getMethodInfo(method).isConstant();
    }

    public Value getConstantFieldValue(Field field) {
        return getFieldInfo(field).value;
    }

    public boolean isConstantField(Field field) {
        return getFieldInfo(field).isConstant();
    }

    public Collection<Field> getFields(Type t) {
        return getTypeInfo(t).fields;
    }
}
