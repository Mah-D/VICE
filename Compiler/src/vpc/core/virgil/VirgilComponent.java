/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Apr 6, 2006
 */
package vpc.core.virgil;

import cck.parser.AbstractToken;
import vpc.core.*;
import vpc.core.decl.*;
import vpc.core.types.*;
import vpc.tir.expr.Operator;

/**
 * The <code>VirgilComponent</code> class represents a component declared
 * in a Virgil program.
 *
 * @author Ben L. Titzer
 */
public class VirgilComponent extends CompoundDecl {

    /**
     * The <code>Type</code> class represents the type of a component within
     * a Virgil program.
     */
    public class IType extends Type.Simple {

        public IType(String n) {
            super(n);
        }

        public VirgilComponent getDecl() {
            return VirgilComponent.this;
        }
    }

    public Type getCanonicalType() {
        if ( canonicalType == null ) {
            canonicalType = new IType(name);
        }
        return canonicalType;
    }

    public TypeCon getTypeCon() {
        if ( typeCon == null ) {
            typeCon = new TypeCon.Singleton(getCanonicalType());
        }
        return typeCon;
    }

    protected SourceRep sourceRep;
    protected Heap.Record record;

    public VirgilComponent(AbstractToken tn) {
        super(tn);
    }

    public SourceRep getComponentRep() {
        return sourceRep;
    }

    public void setComponentRep(SourceRep c) {
        sourceRep = c;
    }

    public void setRecord(Heap.Record r) {
        record = r;
    }

    public Heap.Record getRecord() {
        return record;
    }

    public String toString() {
        return "component "+getName();
    }

    public static VirgilComponent declOf(Type t) {
        return ((IType)t).getDecl();
    }

    public interface SourceRep {
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
     * The <code>VirgilComponent.GetField</code> class represents an operator that
     * retrieves the value of a named component field.
     */
    public static class GetField extends Operator.Op0 implements Operator.Location {
        public final VirgilComponent component;
        public final Field field;

        public GetField(Field f) {
            super(f.getType());
            component = (VirgilComponent)f.getCompoundDecl();
            field = f;
        }

        public Value apply0() throws InitCheckException {
            Heap.Record record = getRecord(component);
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
            return new SetField(field);
        }
    }

    /**
     * The <code>VirgilComponent.SetField</code> class represents an operator that updates
     * the field of a component.
     */
    public static class SetField extends Operator.Op1 {
        public final VirgilComponent component;
        public final Field field;

        public SetField(Field f) {
            super(f.getType(), f.getType());
            component = (VirgilComponent)f.getCompoundDecl();
            field = f;
        }

        public Value apply1(Value v1) throws InitCheckException {
            Heap.Record record = getRecord(component);
            record.setValue(field.fieldIndex, v1);
            return v1;
        }

        public <R, E> R accept(Operator.Visitor<R, E> v, E... e) {
            if ( v instanceof OpVisitor ) return cast(v).visit(this, e);
            else return super.accept(v, e);
        }
    }

    /**
     * The <code>VirgilComponent.GetMethod</code> class represents an operator on a
     * component that creates a delegate for the named component method.
     */
    public static class GetMethod extends Operator {
        public final VirgilComponent component;
        public final Method method;
        public final TypeFormula[] newTypeEnv;

        public GetMethod(Member.Ref<Method> ref, Type[] tf) {
            super(ref.memberType);
            component = (VirgilComponent)ref.memberDecl.getCompoundDecl();
            method = ref.memberDecl;
            newTypeEnv = TypeFormula.newFormula(tf);
        }

        public Type[] getOperandTypes() {
            return Type.NOTYPES;
        }

        public Value apply(Program.DynamicEnvironment env, Value... vals) throws InitCheckException {
            assert vals.length == 0;
            return new VirgilDelegate.Val(getRecord(component), method, TypeFormula.instantiate(env, newTypeEnv));
        }

        public <R, E> R accept(Operator.Visitor<R, E> v, E... e) {
            if ( v instanceof OpVisitor ) return cast(v).visit(this, e);
            else return super.accept(v, e);
        }
    }


    protected static Heap.Record getRecord(VirgilComponent component) throws InitCheckException {
        Heap.Record record = component.record;
        if (record == null) throw new InitCheckException(component);
        return record;
    }

    public static class InitCheckException extends Operator.Exception {
        public final VirgilComponent component;
        public InitCheckException(VirgilComponent c) {
            super("init check exception");
            component = c;
        }
    }
}
