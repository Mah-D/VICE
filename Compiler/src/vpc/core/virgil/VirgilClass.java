/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Mar 24, 2006
 */
package vpc.core.virgil;

import cck.parser.AbstractToken;
import vpc.core.*;
import vpc.core.base.PrimBool;
import vpc.core.base.Reference;
import vpc.core.decl.*;
import vpc.core.types.*;
import vpc.tir.expr.Operator;
import vpc.util.Cache;
import vpc.util.Ovid;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * The <code>VirgilClass</code> class encapsulates the concept of an object and object
 * type in the compiler. An instance of the <code>VirgilClass</code> class represents a
 * class within a program, including a reference to the super class and its declared
 * members.
 *
 * @author Ben L. Titzer
 */
public class VirgilClass extends CompoundDecl {
    public Field metaField;

    protected final AbstractToken parent;
    protected final TypeParam[] typeParams;
    protected SourceRep sourceRep;

    public Type[] completeTypeEnv;

    public VirgilClass(AbstractToken n, AbstractToken pn, TypeParam[] tpl) {
        super(n);
        parent = pn;
        typeParams = tpl;
    }

    public boolean hasParameterizedConstructor() {
        return constructor != null && constructor.getParameterTypes().length > 0;
    }

    public SourceRep getSourceRep() {
        return sourceRep;
    }

    public void setSourceRep(SourceRep c) {
        sourceRep = c;
    }

    public Method resolveMethod(String name, Closure c) {
        VirgilClass cd = this;
        while ( cd != null ) {
            Method m = cd.getLocalMethod(name);
            if ( m != null ) return m;
            cd = c.hierarchy.getParent(cd);
        }
        return null;
    }

    public AbstractToken getParent() {
        return parent;
    }

    public TypeParam[] getTypeParamDecls() {
        return typeParams;
    }

    public String toString() {
        return "class "+getName();
    }

    public static LinkedList<IType> getChain(IType ct) {
        LinkedList<IType> list = Ovid.newLinkedList();
        while ( ct != null ) {
            list.addFirst(ct);
            ct = ct.getParentType();
        }
        return list;
    }

    public IType getParameterizedType(Cache<Type> cache) {
        return buildType(cache, TypeParam.toTypes(typeParams));
    }

    public static VirgilClass declOf(Type t) {
        return ((IType)t).getDecl();
    }

    /**
     * The <code>VirgilClass.TypeQuery</code> class represents an operator on a
     * class reference that determines whether the receiver object is an instance of
     * the specified class--i.e. the "instanceof" operator. This operator returns
     * false if the specified object reference is null.
     */
    public static class TypeQuery extends Operator {
        protected TypeFormula<IType> targetType;
        public final IType source;
        public final IType target;
        public TypeQuery(VirgilClass.IType stype, VirgilClass.IType target) {
            super(target);
            source = stype;
            this.target = target;
            targetType = TypeFormula.newFormula(target);
        }

        public Type[] getOperandTypes() {
            return new Type[] { source };
        }

        public Value apply(Program.DynamicEnvironment env, Value... vals) throws TypeCheckException {
            assert vals.length == 1;
            Heap.Record record = (Heap.Record) vals[0];
            if (record == null) return PrimBool.toValue(false);
            IType other = (VirgilClass.IType)record.getType();
            return PrimBool.toValue(other.isSubtypeOf(targetType.instantiate(env)));
        }
    }

    /**
     * The <code>VirgilClass.TypeCast</code> class represents an operator on an
     * object reference that determines whether the receiver object is an instance of
     * the specified class and throws an exception if it is not--i.e. the
     * "dynamic typecast" operator. This operator returns a reference to the object
     * if the cast succeed and throws an exception if it fails.
     */
    public static class TypeCast extends Operator {
        protected TypeFormula<IType> targetType;
        public final IType source;
        public final IType target;
        public TypeCast(VirgilClass.IType stype, VirgilClass.IType target) {
            super(target);
            source = stype;
            this.target = target;
            targetType = TypeFormula.newFormula(target);
        }

        public Type[] getOperandTypes() {
            return new Type[] { source };
        }

        public Value apply(Program.DynamicEnvironment env, Value... vals) throws TypeCheckException {
            assert vals.length == 1;
            VirgilClass.IType ttype = targetType.instantiate(env);
            Heap.Record record = (Heap.Record) vals[0];
            if (record != null) {
                IType type = (VirgilClass.IType)record.getType();
                if (!type.isSubtypeOf(ttype))
                    throw new TypeCheckException(type, result);
            }
            return vals[0];
        }
    }

    public static class TypeCheckException extends Operator.Exception {
        public TypeCheckException(Type t, Type et) {
            super("type check exception: "+t+", expected "+et);
        }
    }

    public interface OpVisitor<R, E> extends Operator.Visitor<R, E> {
        public R visit(GetField o, E... e);
        public R visit(SetField o, E... e);
        public R visit(GetMethod o, E... e);
    }

    static <R, E> OpVisitor<R, E> cast(Operator.Visitor<R, E> v) {
        return (OpVisitor<R, E>) v;
    }

    /**
     * The <code>VirgilClass.GetField</code> class represents an operator on a
     * class reference that retrieves the value of a named field. If the reference
     * to the object is null, this operator will throw a <code>NullCheckException</code>.
     */
    public static class GetField extends Operator.Op1 implements Operator.Location {
        public final IType thisType;
        public final Field field;

        public GetField(VirgilClass.IType et, Field f) {
            super(et, f.getType());
            field = f;
            thisType = et;
        }

        public GetField(Member.Ref<Field> ref) {
            super(ref.containerType, ref.memberType);
            field = ref.memberDecl;
            thisType = (IType)ref.containerType;
        }

        public Value apply1(Value v1) throws Reference.NullCheckException {
            Heap.Record record = (Heap.Record) v1;
            if (record == null) throw new Reference.NullCheckException();
            return record.getValue(field.fieldIndex);
        }

        public <R, E> R accept(Operator.Visitor<R, E> v, E... e) {
            if ( v instanceof OpVisitor ) return cast(v).visit(this, e);
            else return super.accept(v, e);
        }

        public Operator getOperator() {
            return this;
        }

        public Operator setOperator() {
            return new SetField(thisType, field);
        }
    }

    /**
     * The <code>VirgilClass.SetField</code> class represents an operator that updates
     * the field of an object. If the reference to the object is null, this operator
     * will throw a <code>NullCheckException</code>.
     */
    public static class SetField extends Operator.Op2 {
        public final IType thisType;
        public final Field field;

        public SetField(VirgilClass.IType et, Field f) {
            super(et, f.getType(), f.getType());
            field = f;
            thisType = et;
        }

        public SetField(Member.Ref<Field> ref) {
            super(ref.containerType, ref.memberType, ref.memberType);
            field = ref.memberDecl;
            thisType = (IType)ref.containerType;
        }
        
        public Value apply2(Value v1, Value v2) throws Reference.NullCheckException {
            Heap.Record record = (Heap.Record) v1;
            if (record == null) throw new Reference.NullCheckException();
            record.setValue(field.fieldIndex, v2);
            return v2;
        }

        public <R, E> R accept(Operator.Visitor<R, E> v, E... e) {
            if ( v instanceof OpVisitor ) return cast(v).visit(this, e);
            else return super.accept(v, e);
        }
    }

    /**
     * The <code>VirgilClass.GetMethod</code> class represents an operator on a
     * class reference that creates a delegate for the named method. If the reference
     * to the object is null, this operator will throw a <code>NullCheckException</code>.
     * This operator implements virtual dispatch and static dispatch (like Java's invokespecial)
     * on the type of the receiver object.
     */
    public static class GetMethod extends Operator {
        public final boolean virtual;
        public final IType thisType;
        public final Method method;
        public final TypeFormula[] newTypeEnv;

        public GetMethod(IType tt, Method m, boolean v, TypeFormula[] tf) {
            super(m.getType());
            thisType = tt;
            method = m;
            virtual = v;
            newTypeEnv = tf;
        }

        public GetMethod(Member.Ref<Method> ref, boolean v, TypeFormula[] tf) {
            super(ref.memberType);
            thisType = (IType)ref.containerType;
            method = ref.memberDecl;
            virtual = v;
            newTypeEnv = tf;
        }

        public GetMethod(Member.Ref<Method> ref, boolean v, Type[] tf) {
            this(ref, v, TypeFormula.newFormula(tf));
        }

        public Type[] getOperandTypes() {
            return new Type[] { thisType };
        }

        public Value apply(Program.DynamicEnvironment env, Value... vals) throws Reference.NullCheckException, MethodNotFoundException {
            assert vals.length == 1;
            Heap.Record record = (Heap.Record) vals[0];
            if (record == null) throw new Reference.NullCheckException();
            Method meth = virtual ? resolveMethod(env, record) : method;
            return new VirgilDelegate.Val(record, meth, TypeFormula.instantiate(env, newTypeEnv));
        }

        public <R, E> R accept(Operator.Visitor<R, E> v, E... e) {
            if ( v instanceof OpVisitor ) return cast(v).visit(this, e);
            else return super.accept(v, e);
        }

        private Method resolveMethod(Program.DynamicEnvironment env, Heap.Record record) throws MethodNotFoundException {
            Type t = record.getType();
            Method m = null;
            if (t instanceof IType) {
                m = declOf(t).resolveMethod(method.getName(), env.getProgram().closure);
            }
            if (m == null) throw new MethodNotFoundException(method);
            return m;
        }

    }

    public static class Alloc extends Operator {
        public final TypeFormula<IType> allocType;

        public Alloc(VirgilClass.IType c) {
            super(c);
            allocType = TypeFormula.newFormula(c);
        }

        public Type[] getOperandTypes() {
            return Type.NOTYPES;
        }

        public Value apply(Program.DynamicEnvironment env, Value... vals) {
            assert vals.length == 0;
            Heap heap = env.getHeap();
            VirgilClass.IType ctype = allocType.instantiate(env);
            VirgilClass decl = ctype.getDecl();
            Heap.Layout l = heap.program.closure.getLayout(decl);
            return heap.allocRecord(ctype, l.size());
        }
    }

    public TypeCon buildTypeCon(TypeRef pTypeRef) {
        if ( typeCon == null ) {
            typeCon = new ITypeCon(pTypeRef);
        }
        return typeCon;
    }

    public IType buildType(Cache<Type> cache, Type[] tp) {
        return (IType)typeCon.newType(cache, tp);
    }

    public class ITypeCon extends TypeCon {

        protected final TypeRef parentRef;
        protected Cache<Type> typeCache;

        public ITypeCon(TypeRef pt) {
            super(getName(), typeParams.length);
            parentRef = pt;
        }

        public IType newType(Cache<Type> cache, Type... tp) {
            assert tp.length == arity;
            typeCache = cache;
            IType ntype = new IType(this, tp, null);
            IType otype = cache.getCached(ntype);
            if ( ntype == otype ) {
                ntype.parent = buildParentType(cache, tp);
            }
            return otype;
        }

        private IType buildParentType(Cache<Type> cache, Type[] tp) {
            if ( parentRef == null ) return null;
            Type ptype = parentRef.rebuildType(cache);
            return (IType)TypeParam.substitute(cache, typeParams, tp, ptype);
        }

        public String render(TypeRef... p) {
            assert p.length == arity;
            return TypeParam.buildParameterizedName(getName(), p);
        }
        public VirgilClass getDecl() {
            return VirgilClass.this;
        }
    }

    /**
     * The <code>VirgilClass.Type</code> class represents a Virgil object type within
     * the type system. It consists of a list of fields and a list of methods
     * that make up the class as well as its parent class.
     */
    public static class IType extends Type {

        protected final ITypeCon typeCon;
        protected final Type[] params;

        protected IType parent;
        protected Type[] typeEnv;

        public IType(ITypeCon tc, Type[] tp, IType p) {
            super(TypeParam.buildParameterizedName(tc.getDecl().getName(), tp));
            parent = p;
            params = tp;
            typeCon = tc;
        }

        public ITypeCon getTypeCon() {
            return typeCon;
        }

        public IType getParentType() {
            return parent;
        }

        public Type[] elements() {
            return params;
        }

        public Type[] getTypeParams() {
            return params;
        }

        public Type[] getTypeEnv(Cache<Type> cache) {
            if (typeEnv == null) {
                Type[] ctenv = typeCon.getDecl().completeTypeEnv;
                TypeParam[] typeParams = typeCon.getDecl().getTypeParamDecls();
                typeEnv = new Type[ctenv.length];
                for (int cntr = 0; cntr < ctenv.length; cntr++) {
                    typeEnv[cntr] = TypeParam.substitute(cache, typeParams, params, ctenv[cntr]);
                }
            }
            return typeEnv;
        }

        public Type rebuild(Type[] n) {
            return typeCon.newType(typeCon.typeCache, n);
        }

        public VirgilClass getDecl() {
            return typeCon.getDecl();
        }

        public boolean equals(Object o) {
            if ( o == this ) return true;
            if ( !(o instanceof IType) ) return false;
            IType ot = (IType)o;
            return ot.typeCon == typeCon && Arrays.equals(params, ot.params);
        }

        public boolean isSubtypeOf(Type c) {
            if ( !(c instanceof IType) ) return false;
            for (IType w = this; w != null; w = w.parent) {
                if (c == w) return true;
            }
            return false;
        }

        public Member.Ref resolveMember(Cache<Type> cache, String name) {
            return resolveMember0(cache, this, name);
        }

        private Member.Ref resolveMember0(Cache<Type> cache, IType staticType, String name) {
            VirgilClass decl = typeCon.getDecl();
            Member m = decl.getLocalMember(name);
            if ( m != null ) {
                Type mt = TypeParam.substitute(cache, decl.typeParams, params, m.getType());
                return new Member.Ref<Member>(staticType, m, mt);
            } else if ( parent != null ) {
                return parent.resolveMember0(cache, staticType, name);
            }
            return null;
        }

        public Member.Ref<Constructor> getConstructor(Cache<Type> cache) {
            VirgilClass decl = typeCon.getDecl();
            Constructor c = decl.getConstructor();
            if ( c != null ) {
                Type mt = TypeParam.substitute(cache, decl.typeParams, params, c.getType());
                return new Member.Ref<Constructor>(this, c, mt);
            }
            return null;
        }

        public IType getRootType() {
            IType p = this;
            while ( p.parent != null ) p = p.parent;
            return p;
        }
    }

    public interface SourceRep {
    }

    public static class MethodNotFoundException extends Operator.Exception {
        public MethodNotFoundException(Method m) {
            super("method not found: "+m.getFullName());
        }
    }

}
