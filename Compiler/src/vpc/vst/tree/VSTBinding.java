/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Feb 27, 2007
 */
package vpc.vst.tree;

import vpc.core.base.PrimInt32;
import vpc.core.base.Tuple;
import vpc.core.decl.*;
import vpc.core.types.Type;
import vpc.core.types.TypeVar;
import vpc.core.virgil.VirgilComponent;
import vpc.hil.Device;

/**
 * The <code>VSTBinding</code> definition.
 *
 * @author Ben L. Titzer
 */
public abstract class VSTBinding {

    protected Type type;

    protected VSTBinding(Type t) {
        type = t;
    }

    protected VSTBinding() {
    }

    public abstract <T> T accept(Visitor<T> v);

    public interface Visitor<T> {
        public T visit(Local e);
        public T visit(ComponentField e);
        public T visit(ClassField e);
        public T visit(ComponentMethod e);
        public T visit(ClassMethod e);
        public T visit(ArrayLength e);
        public T visit(DeviceRegister e);
        public T visit(DeviceInstruction e);
        public T visit(TupleElement e);
    }

    public static class Local extends VSTBinding {
        public final VSTVarDecl decl;
        public Local(VSTVarDecl d) {
            decl = d;
        }
        public Type getType() {
            return type = decl.getType();
        }
        public <T> T accept(Visitor<T> v) {
            return v.visit(this);
        }
    }

    public abstract static class MethodUse extends VSTBinding {
        public TypeVar[] typeVars;
        public final Member.Ref<Method> ref;

        protected MethodUse(Member.Ref<Method> ref) {
            super(ref.memberType);
            this.ref = ref;
        }
        public void setVarType(Type t) {
            type = t;
        }
    }

    public abstract static class FieldUse extends VSTBinding {
        public final Member.Ref<Field> ref;

        protected FieldUse(Member.Ref<Field> ref) {
            super(ref.memberType);
            this.ref = ref;
        }
    }

    public static class ComponentField extends FieldUse {

        public ComponentField(VirgilComponent cd, Member.Ref<Field> ref) {
            super(ref);
        }
        public <T> T accept(Visitor<T> v) {
            return v.visit(this);
        }
    }

    public static class ClassField extends FieldUse {
        public final VSTExpr expr;

        public ClassField(VSTExpr e, Member.Ref<Field> ref) {
            super(ref);
            expr = e;
        }
        public <T> T accept(Visitor<T> v) {
            return v.visit(this);
        }
    }

    public static class ComponentMethod extends MethodUse {

        public ComponentMethod(VirgilComponent cd, Member.Ref<Method> ref) {
            super(ref);
        }
        public <T> T accept(Visitor<T> v) {
            return v.visit(this);
        }
    }

    public static class ClassMethod extends MethodUse {
        public final VSTExpr expr;

        public ClassMethod(VSTExpr e, Member.Ref<Method> ref) {
            super(ref);
            expr = e;
        }
        public <T> T accept(Visitor<T> v) {
            return v.visit(this);
        }
    }

    public static class ArrayLength extends VSTBinding {
        public final VSTExpr array;

        public ArrayLength(VSTExpr e) {
            super(PrimInt32.TYPE);
            array = e;
        }
        public <T> T accept(Visitor<T> v) {
            return v.visit(this);
        }
    }

    public static class DeviceRegister extends VSTBinding {
        public final Device.Register register;

        public DeviceRegister(vpc.hil.Device d, Device.Register r) {
            super(r.getType());
            register = r;
        }
        public <T> T accept(Visitor<T> v) {
            return v.visit(this);
        }
    }

    public static class DeviceInstruction extends VSTBinding {
        public final Device.Instruction instruction;

        public DeviceInstruction(vpc.hil.Device d, Type t, Device.Instruction r) {
            super(t);
            instruction = r;
        }
        public <T> T accept(Visitor<T> v) {
            return v.visit(this);
        }
    }

    public static class TupleElement extends VSTBinding {
        public final VSTExpr tupleExpr;
        public final Tuple.IType tupleType;
        public final int position;

        public TupleElement(VSTExpr expr, Tuple.IType ttype, int pos) {
            super(ttype.elements()[pos]);
            tupleExpr = expr;
            position = pos;
            tupleType = ttype;
        }
        public <T> T accept(Visitor<T> v) {
            return v.visit(this);
        }
    }

    public Type getType() {
        return type;
    }
}
