/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: May 24, 2007
 */

package vpc.tir.stages;

import cck.parser.AbstractToken;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.*;
import vpc.core.base.Function;
import vpc.core.base.Reference;
import vpc.core.csr.CSRGen;
import vpc.core.decl.*;
import vpc.core.types.*;
import vpc.core.virgil.*;
import static vpc.core.virgil.V2TypeSystem.*;
import vpc.sched.Stage;
import vpc.tir.*;
import static vpc.tir.TIRUtil.$GET;
import static vpc.tir.TIRUtil.$SET;
import vpc.tir.expr.Operator;
import vpc.tir.opt.DepthFirstTransformer;
import vpc.util.*;

import java.util.*;

/**
 * The <code>Monomorphizer</code> class implements a compiler pass
 * that translates polymorphic code from the source level into a monomorphic
 * form that is suitable for code generation.
 *
 * @author Ben L. Titzer
 */
public class Monomorphizer extends Stage {

    protected static final boolean METHODS_ONDEMAND = true;

    protected static final HashMap<Type, Type> EMPTY_ENV = Ovid.newHashMap();
    protected static final SpecKey[] NOKEYS = new SpecKey[0];
    protected static final Type[] MONO = Type.NOTYPES;

    public void visitProgram(Program p) {
        // specialize the program
        new MonoProgram(p).specializeProgram();
    }

    protected static class SpecKey {
        final TypeCon decl;
        final SpecKey[] subkeys;

        protected SpecKey(TypeCon d, SpecKey[] bind) {
            decl = d;
            subkeys = bind;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof SpecKey)) return false;
            SpecKey sk = (SpecKey) o;
            return decl.equals(sk.decl) && Arrays.equals(subkeys, sk.subkeys);
        }

        public int hashCode() {
            return decl.hashCode();
        }

        public String toString() {
            return StringUtil.embed(decl.name, StringUtil.commalist(subkeys));
        }
    }

    protected static class MonoProgram {

        protected Program program;
        protected Closure closure;

        protected Map<Type, String> mangledTypes = Ovid.newMap(Ovid.WEAK);
        protected Queue<MethodSpec> queue = Ovid.newLinkedList();

        protected Map<Type, SpecKey> specKeys = Ovid.newMap();
        protected Map<SpecKey, ClassSpec> classSpecs = Ovid.newMap();
        protected Map<MethodSpec, MethodSpec> methodSpecs = Ovid.newMap();
        protected Map<VirgilComponent, CompSpec> componentSpecs = Ovid.newMap();

        MonoProgram(Program p) {
            program = p;
            closure = p.closure.copy();
            closure.classes = Ovid.newLinkedList();
            closure.components = Ovid.newLinkedList();
            closure.methods = Ovid.newSet();
            closure.methodFamilies = Ovid.newSet();
            closure.resetLayouts();
            closure.hierarchy = new Hierarchy<VirgilClass>();
        }

        void specializeProgram() {
            // get the monomorphic methods from the closure
            processEntrypoints();
            // monomorphize all the records
            for (Heap.Record rec : closure.getRecords()) {
                getMonoRecord(rec);
                for (int cntr = 0; cntr < rec.getSize(); cntr++) {
                    rec.setValue(cntr, getMonoValue(rec.getValue(cntr)));
                }
            }
            // crank through all instantiations
            CodeSpecializer specializer = new CodeSpecializer();
            for (MethodSpec spec = queue.poll(); spec != null; spec = queue.poll()) {
                specializer.specializeMethod(spec);
            }
            // destroy the old polymorphic methods to catch nasty bugs later in the compiler
            for ( MethodSpec spec : methodSpecs.values()) {
                spec.polyMethod.addMethodRep(TIRRep.REP_NAME, null);
                spec.polyRep.setBody(null);
            }
            // build the layout of the classes in the closure.
            HashSet<VirgilClass> done = new HashSet<VirgilClass>();
            for ( VirgilClass c : closure.classes ) computeLayout(c, done);

            program.closure = closure;
        }

        private Heap.Layout computeLayout(VirgilClass c, Set<VirgilClass> done) {
            Heap.Layout layout = closure.classLayouts.get(c);
            if (!done.contains(c)) {
                VirgilClass pcd = closure.hierarchy.getParent(c);
                if (pcd != null) {
                    // if there is a parent class, copy its layout into this one
                    computeLayout(pcd, done).copy(layout);
                }
                ObjectLayout.addFields(c, layout);
                done.add(c);
            }
            return layout;
        }

        private void processEntrypoints() {
            if (program.programDecl == null) {
                for (Method m : program.closure.methods) {
                    if (m.getTypeParams().length == 0) {
                        CompoundDecl decl = m.getCompoundDecl();
                        if (decl instanceof VirgilComponent) {
                            CompSpec spec = getCompSpec((VirgilComponent) decl);
                            queue.offer(getMethodSpec(spec, m, MONO));
                        }
                    }
                }
            } else {
                for (ProgramDecl.EntryPoint pt : program.programDecl.entryPoints) {
                    Method m = pt.method;
                    assert m.getTypeParams().length == 0;
                    CompoundDecl decl = m.getCompoundDecl();
                    MethodSpec mspec = getMethodSpec(getCompSpec((VirgilComponent) decl), m, MONO);
                    pt.method = mspec.getMonoMethod();
                }
            }
        }

        DeclSpec getDeclSpec(Type type) {
            if (type instanceof VirgilClass.IType) {
                return getClassSpec(type);
            } else if (type instanceof VirgilComponent.IType) {
                return getCompSpec(VirgilComponent.declOf(type));
            }
            return null;
        }

        private CompSpec getCompSpec(VirgilComponent c) {
            CompSpec spec = componentSpecs.get(c);
            if (spec == null) {
                spec = new CompSpec(c);
                componentSpecs.put(c, spec);
            }
            return spec;
        }

        MethodSpec getMethodSpec(DeclSpec cspec, Method m, Type[] tbind) {
            MethodSpec nspc = new MethodSpec(m, cspec, tbind);
            MethodSpec spec = methodSpecs.get(nspc);
            if (spec == null) {
                spec = nspc;
                methodSpecs.put(nspc, nspc);
            }
            return spec;
        }

        ClassSpec getClassSpec(Type type) {
            SpecKey key = keyOf(type);
            ClassSpec spec = classSpecs.get(key);
            if (spec == null) {
                spec = new ClassSpec(key, (VirgilClass.IType) type);
            }
            return spec;
        }

        protected Value getMonoValue(Value val) {
            if (val instanceof VirgilDelegate.Val) {
                VirgilDelegate.Val deleg = VirgilDelegate.fromValue(val);
                DeclSpec dspec = getDeclSpec(deleg.record.getType());
                Member.Ref<Method> ref = dspec.resolveMethod(deleg.method, deleg.typeEnv);
                val = new VirgilDelegate.Val(getMonoRecord(deleg.record), ref.memberDecl);
            }
            if (val instanceof Heap.Record) {
                val = getMonoRecord((Heap.Record)val);
            }
            return val;
        }

        private Heap.Record getMonoRecord(Heap.Record record) {
            Type monoType = record.type = $mono(EMPTY_ENV, record.type);
            if (monoType instanceof VirgilClass.IType || monoType instanceof VirgilComponent.IType) {
                getDeclSpec(monoType).getMonoLayout();
            } else if (monoType instanceof VirgilArray.IType) {
                VirgilArray.getArrayLayout(program.heap, (VirgilArray.IType) monoType, record.getSize());
            }
            return record;
        }

        protected SpecKey keyOf(Type t) {
            SpecKey k = specKeys.get(t);
            if (k == null) {
                if (t instanceof VirgilClass.IType)
                    k = keyOf(VirgilClass.declOf(t).getTypeCon(), t.elements());
                else if (t instanceof VirgilComponent.IType)
                    k = new SpecKey(VirgilComponent.declOf(t).getTypeCon(), NOKEYS);
                else if (t instanceof Function.IType) k = keyOf(Function.TYPECON, t.elements());
                else if (t instanceof VirgilArray.IType) k = keyOf(VirgilArray.TYPECON, t.elements());
                else k = new SpecKey(new TypeCon.Singleton(t), NOKEYS);
                specKeys.put(t, k);
            }
            return k;
        }

        protected SpecKey keyOf(TypeCon d, Type[] t) {
            SpecKey[] sk = new SpecKey[t.length];
            for (int cntr = 0; cntr < t.length; cntr++) sk[cntr] = keyOf(t[cntr]);
            return new SpecKey(d, sk);
        }

        protected static class VirtualSpec {
            protected final Method polyMethod;
            protected final DeclSpec declarer;
            protected VirtualSpec(Method m, DeclSpec d) {
                polyMethod = m;
                declarer = d;
            }
        }

        protected abstract class DeclSpec<CDecl extends CompoundDecl> {
            final CDecl polyDecl;
            final Type[] binding;
            final Map<Type, Type> typeEnvMap;
            final Map<Method.Family, Set<VirtualSpec>> methodFamilies = Ovid.newMap();

            protected Heap.Layout monoLayout;
            protected CDecl monoDecl;

            protected DeclSpec(CDecl poly, Type[] b) {
                polyDecl = poly;
                binding = b;
                typeEnvMap = buildMap();
            }

            protected CDecl getMonoDecl() {
                if (monoDecl == null) buildMonoDecl();
                return monoDecl;
            }

            public Type getMonoType() {
                return getMonoDecl().getCanonicalType();
            }

            protected Heap.Layout getMonoLayout() {
                if (monoDecl == null) buildMonoDecl();
                if (monoLayout == null) buildMonoLayout();
                return monoLayout;
            }

            protected Map<Type, Type> buildMap() {
                return EMPTY_ENV;
            }

            protected Set<VirtualSpec> getMethodFamilySet(Method method, DeclSpec dspec) {
                Set<VirtualSpec> mfs = methodFamilies.get(method.family);
                if ( mfs == null ) {
                    mfs = Ovid.newSet();
                    methodFamilies.put(method.family, mfs);
                }
                mfs.add(new VirtualSpec(method, dspec));
                return mfs;
            }

            abstract void buildMonoDecl();

            abstract void buildMonoLayout();

            abstract Member.Ref<Field> resolveField(Field f);

            abstract Member.Ref<Method> resolveMethod(Method m, Type[] bind);
        }

        protected class CompSpec extends DeclSpec<VirgilComponent> {

            CompSpec(VirgilComponent c) {
                super(c, MONO);
            }

            void buildMonoDecl() {
                monoDecl = new VirgilComponent(polyDecl.getToken());
                for (Field f : polyDecl.getFields()) {
                    Type nt = $mono(EMPTY_ENV, f.getType());
                    monoDecl.newField(false, f.getToken(), TypeRef.refOf(nt));
                }
                closure.components.add(monoDecl);
                componentSpecs.put(monoDecl, this);
                monoDecl.setRecord(polyDecl.getRecord());
            }

            void buildMonoLayout() {
                monoLayout = ObjectLayout.computeLayout(closure, monoDecl);
            }

            Member.Ref<Field> resolveField(Field f) {
                Field mono = monoDecl.getLocalField(f.getName());
                return new Member.Ref<Field>(getMonoType(), mono, mono.getType());
            }

            Member.Ref<Method> resolveMethod(Method m, Type[] bind) {
                Method mono = getMethodSpec(this, m, bind).getMonoMethod();
                return new Member.Ref<Method>(getMonoType(), mono, mono.getType());
            }
        }

        protected class ClassSpec extends DeclSpec<VirgilClass> {
            final SpecKey specKey;
            final VirgilClass.IType polyType;
            final ClassSpec parentSpec;

            AbstractToken monoToken;
            VirgilClass.IType monoClassType;

            ClassSpec(SpecKey key, VirgilClass.IType type) {
                super(type.getDecl(), type.getTypeEnv(program.typeCache));
                classSpecs.put(key, this);
                polyType = type;
                specKey = key;
                VirgilClass.IType ptype = polyType.getParentType();
                parentSpec = ptype == null ? null : getClassSpec(ptype);
            }

            protected Map<Type, Type> buildMap() {
                Map<Type, Type> map = Ovid.newMap();
                return fillTypeMap(polyDecl.completeTypeEnv, binding, map);
            }

            protected void buildMonoDecl() {
                // mangle the name to disambiguate instantiations
                monoToken = mangleDecl(polyDecl, polyType.getTypeParams());
                AbstractToken parentToken = null;
                TypeRef parentRef = null;

                if (parentSpec != null) {
                    // get a reference to the parent type
                    String pname = mangleType(polyType.getParentType());
                    parentToken = AbstractToken.newToken(pname, polyDecl.getParent().getSourcePoint());
                    Type t = parentSpec.getMonoType();
                    TypeCon tc = parentSpec.monoClassType.getTypeCon();
                    parentRef = TypeRef.refOf(tc, t);
                }

                if (monoDecl == null) {
                    // build the declaration
                    monoDecl = new VirgilClass(monoToken, parentToken, TypeParam.NOTYPEPARAMS);
                    monoDecl.completeTypeEnv = MONO;

                    // add this class to the closure and hierarchy
                    closure.classes.add(monoDecl);
                    if (parentSpec != null) closure.hierarchy.add(parentSpec.getMonoDecl(), monoDecl);
                    else closure.hierarchy.addRoot(monoDecl);

                    // build the type constructor and mono type, add to spec
                    TypeCon tc = monoDecl.buildTypeCon(parentRef);
                    monoClassType = (VirgilClass.IType) tc.newType(program.typeCache);
                    monoDecl.setCanonicalType(monoClassType);
                    specKeys.put(monoClassType, specKey);

                    // add declared fields.
                    for (Field f : this.polyDecl.getFields()) {
                        Type nt = $mono(this.typeEnvMap, f.getType());
                        monoDecl.newField(false, f.getToken(), TypeRef.refOf(nt));
                    }

                    // add all virtual methods into their method family sets.
                    for (Method m : polyDecl.getMethods()) {
                        if ( m.family != null ) {
                            // get the class spec that declared the method
                            ClassSpec declarer = getDeclarer(m.family.rootMethod.getCompoundDecl());
                            // add this method to the method family set
                            declarer.getMethodFamilySet(m, this);
                        }
                    }

                    monoLayout = ObjectLayout.newLayout(closure, monoDecl, closure.classLayouts);
                }
            }

            void buildMonoLayout() {
                // build the layout: do nothing.
            }

            Member.Ref<Field> resolveField(Field f) {
                getMonoDecl();
                return monoClassType.resolveMember(program.typeCache, f.getName());
            }

            Member.Ref<Method> resolveMethod(Method m, Type[] bind) {
                getMonoDecl();
                ClassSpec declarer = getDeclarer(m.getCompoundDecl());
                Method mono = getMethodSpec(declarer, m, bind).getMonoMethod();
                return new Member.Ref<Method>(monoClassType, mono, mono.getType());
            }

            private ClassSpec getDeclarer(CompoundDecl decl) {
                ClassSpec declarer = this;
                while (declarer.polyDecl != decl) {
                    if (declarer.parentSpec == null)
                        throw Util.failure("cannot resolve declarer "+ decl + " in " + polyDecl);
                    declarer = declarer.parentSpec;
                }
                return declarer;
            }
        }

        protected class MethodSpec implements TypeFormula.TypeEnv {
            final Method polyMethod;
            final TIRRep polyRep;
            final DeclSpec declSpec;
            final Type[] methodEnv;
            final Map<Type, Type> typeEnvMap;
            final Map<Method.Temporary, Method.Temporary> rename;

            Method monoMethod;
            TIRRep monoRep;

            MethodSpec(Method poly, DeclSpec dspec, Type[] menv) {
                polyMethod = poly;
                polyRep = TIRUtil.getRep(poly);
                rename = Ovid.newMap(Ovid.WEAK);
                declSpec = dspec;
                methodEnv = menv;
                typeEnvMap = buildMap();
            }

            Map<Type, Type> buildMap() {
                Map<Type, Type> map = Ovid.newMap();
                fillTypeMap(polyMethod.getTypeParams(), methodEnv, map);
                map.putAll(declSpec.typeEnvMap);
                return map;
            }

            Method getMonoMethod() {
                if (monoMethod == null) {
                    // mangle the name to disambiguate instantiations
                    AbstractToken name = mangleDecl(polyMethod, methodEnv);
                    TypeRef tref = TypeRef.refOf(Function.TYPECON, $mono(polyMethod.getType(), this));
                    // create a monomorphic version of the method
                    CompoundDecl monoDecl = declSpec.getMonoDecl();
                    if (polyMethod instanceof Constructor) {
                        monoMethod = monoDecl.newConstructor(tref);
                    } else {
                        monoMethod = monoDecl.newMethod(polyMethod.isPrivate(), name, tref, TypeParam.NOTYPEPARAMS);
                        if ( METHODS_ONDEMAND && polyMethod.family != null ) {
                            if ( polyMethod.isRootOfFamily() ) {
                                monoMethod.family = new Method.Family(monoMethod);
                                closure.methodFamilies.add(monoMethod.family);
                                Set<VirtualSpec> set = declSpec.getMethodFamilySet(polyMethod, declSpec);
                                for ( VirtualSpec p : set) {
                                    getMethodSpec(p.declarer, p.polyMethod, methodEnv).getMonoMethod();
                                }
                            } else {
                                Member.Ref<Method> rm = declSpec.resolveMethod(polyMethod.family.rootMethod, methodEnv);
                                monoMethod.family = rm.memberDecl.family;
                            }
                        }
                    }
                    closure.methods.add(monoMethod);
                    queue.offer(this);
                }
                return monoMethod;
            }

            public boolean equals(Object o) {
                if (o == this) return true;
                if (!(o instanceof MethodSpec)) return false;
                MethodSpec om = (MethodSpec) o;
                if (polyMethod != om.polyMethod) return false;
                if (declSpec != om.declSpec) return false;
                return Arrays.equals(methodEnv, om.methodEnv);
            }

            public int hashCode() {
                return polyMethod.getName().hashCode();
            }

            Method.Temporary getTemp(Method.Temporary t) {
                Method.Temporary nt = rename.get(t);
                if (nt == null) {
                    nt = monoRep.newVariable(t.getName(), $mono(t.getType(), this));
                    rename.put(t, nt);
                }
                return nt;
            }

            public Type getMethodTypeParam(TypeParam.IType tp) {
                return methodEnv[tp.index];
            }

            public Type getClassTypeParam(TypeParam.IType tp) {
                return declSpec.binding[tp.index];
            }

            public Cache<Type> getTypeCache() {
                return program.typeCache;
            }
        }

        protected class CodeSpecializer extends DepthFirstTransformer<MethodSpec> {

            public void specializeMethod(MethodSpec spec) {
                if (spec.monoRep == null) {
                    // only specialize the code if not already done
                    Method meth = spec.getMonoMethod();
                    TIRRep newRep = new TIRRep();
                    spec.monoRep = newRep;
                    meth.addMethodRep(TIRRep.REP_NAME, newRep);
                    for (Method.Temporary op : spec.polyRep.getParams()) {
                        Method.Temporary np = newRep.newParam(op.getName(), $mono(op.getType(), spec));
                        spec.rename.put(op, np);
                    }
                    spec.monoRep.setBody(spec.polyRep.getBody().accept(this, spec));
                }
            }

            public Type transform(Type t, MethodSpec env) {
                return $mono(t, env);
            }

            public TIRExpr visit(TIRLocal.Get e, MethodSpec env) {
                return label(e, $GET(env.getTemp(e.temp)), env);
            }

            public TIRExpr visit(TIRConst.Value e, MethodSpec env) {
                return label(e, new TIRConst.Value(getMonoValue(e.getValue())), env);
            }

            public TIRExpr visit(TIRLocal.Set e, MethodSpec env) {
                TIRExpr nv = e.value.accept(this, env);
                return label(e, $SET(env.getTemp(e.temp), nv), env);
            }

            public TIRExpr visit(TIROperator op, MethodSpec env) {
                Operator nop = op.operator;
                TIRExpr[] no = transform(op.operands, env);
                switch (getOpcode(op)) {
                    case VirgilOp.CLASS_GETFIELD: {
                        VirgilClass.GetField gf = (VirgilClass.GetField) op.operator;
                        DeclSpec spec = getDeclSpec($mono(gf.thisType, env));
                        Member.Ref<Field> ref = spec.resolveField(gf.field);
                        nop = new VirgilClass.GetField(ref);
                        break;
                    }
                    case VirgilOp.CLASS_SETFIELD: {
                        VirgilClass.SetField sf = (VirgilClass.SetField) op.operator;
                        DeclSpec spec = getDeclSpec($mono(sf.thisType, env));
                        Member.Ref<Field> ref = spec.resolveField(sf.field);
                        nop = new VirgilClass.SetField(ref);
                        break;
                    }
                    case VirgilOp.CLASS_GETMETHOD: {
                        VirgilClass.GetMethod gm = (VirgilClass.GetMethod) op.operator;
                        Type[] mbind = $mono(gm.newTypeEnv, env);
                        DeclSpec spec = getDeclSpec($mono(gm.thisType, env));
                        Member.Ref<Method> ref = spec.resolveMethod(gm.method, mbind);
                        nop = new VirgilClass.GetMethod(ref, gm.virtual, MONO);
                        break;
                    }
                    case VirgilOp.COMPONENT_GETFIELD: {
                        VirgilComponent.GetField gf = (VirgilComponent.GetField) op.operator;
                        DeclSpec spec = getCompSpec(gf.component);
                        Member.Ref<Field> ref = spec.resolveField(gf.field);
                        nop = new VirgilComponent.GetField(ref.memberDecl);
                        break;
                    }
                    case VirgilOp.COMPONENT_SETFIELD: {
                        VirgilComponent.SetField sf = (VirgilComponent.SetField) op.operator;
                        DeclSpec spec = getCompSpec(sf.component);
                        Member.Ref<Field> ref = spec.resolveField(sf.field);
                        nop = new VirgilComponent.SetField(ref.memberDecl);
                        break;
                    }
                    case VirgilOp.COMPONENT_GETMETHOD: {
                        VirgilComponent.GetMethod gm = (VirgilComponent.GetMethod) op.operator;
                        Type[] mbind = $mono(gm.newTypeEnv, env);
                        DeclSpec spec = getCompSpec(gm.component);
                        Member.Ref<Method> ref = spec.resolveMethod(gm.method, mbind);
                        nop = new VirgilComponent.GetMethod(ref, MONO);
                        break;
                    }
                    case VirgilOp.ARRAY_GETLENGTH: {
                        VirgilArray.GetLength gl = (VirgilArray.GetLength) op.operator;
                        nop = new VirgilArray.GetLength($mono(gl.arrayType, env));
                        break;
                    }
                    case VirgilOp.ARRAY_INIT: {
                        VirgilArray.Init ai = (VirgilArray.Init) op.operator;
                        nop = new VirgilArray.Init($mono(ai.arrayType, env), ai.length);
                        break;
                    }
                    case VirgilOp.NEW_ARRAY: {
                        VirgilArray.Alloc na = (VirgilArray.Alloc) op.operator;
                        nop = new VirgilArray.Alloc($mono(na.arrayType, env), na.dimensions);
                        break;
                    }
                    case VirgilOp.NEW_OBJECT: {
                        VirgilClass.Alloc ca = (VirgilClass.Alloc) op.operator;
                        VirgilClass.IType nct = $mono(ca.allocType, env);
                        getDeclSpec(nct).getMonoLayout();// ensure the layout is built.
                        nop = new VirgilClass.Alloc(nct);
                        break;
                    }
                    case VirgilOp.TYPE_QUERY: {
                        VirgilClass.TypeQuery tq = (VirgilClass.TypeQuery) op.operator;
                        nop = new VirgilClass.TypeQuery($mono(tq.source, env), $mono(tq.target, env));
                        break;
                    }
                    case VirgilOp.TYPE_CAST: {
                        VirgilClass.TypeCast tq = (VirgilClass.TypeCast) op.operator;
                        nop = new VirgilClass.TypeCast($mono(tq.source, env), $mono(tq.target, env));
                        break;
                    }
                    case VirgilOp.ARRAY_GETELEMENT: {
                        VirgilArray.GetElement ge = (VirgilArray.GetElement) op.operator;
                        nop = new VirgilArray.GetElement($mono(ge.arrayType, env));
                        break;
                    }
                    case VirgilOp.ARRAY_SETELEMENT: {
                        VirgilArray.SetElement se = (VirgilArray.SetElement) op.operator;
                        nop = new VirgilArray.SetElement($mono(se.arrayType, env));
                        break;
                    }
                    case VirgilOp.NULL_CHECK: {
                        Reference.NullCheck nc = (Reference.NullCheck) op.operator;
                        nop = new Reference.NullCheck($mono(nc.getResultType(), env));
                        break;
                    }
                }
                return label(op, new TIROperator(nop, no), env);
            }
        }

        private int getOpcode(TIROperator to) {
            Integer val = CSRGen.opMap.get(to.operator.getClass());
            if (val == null) return -1;
            return val;
        }

        <T extends Type> T $mono(TypeFormula<T> f, MethodSpec spec) {
            return $mono(f.instantiate(spec), spec);
        }

        <T extends Type> T $mono(T t, MethodSpec spec) {
            return (T) $mono(spec.typeEnvMap, t);
        }

        Type[] $mono(TypeFormula[] tarr, MethodSpec spec) {
            Type[] narr = new Type[tarr.length];
            for (int cntr = 0; cntr < tarr.length; cntr++) {
                narr[cntr] = $mono(tarr[cntr], spec);
            }
            return narr;
        }

        protected AbstractToken mangleDecl(BaseDecl decl, Type[] bind) {
            StringBuffer buf = new StringBuffer(decl.getName());
            for (Type t : bind) {
                buf.append("_");
                buf.append(mangleType(t));
            }
            return AbstractToken.newToken(buf.toString(), decl.getSourcePoint());
        }

        protected String mangleType(Type t) {
            String str = mangledTypes.get(t);
            if (str == null) {
                StringBuffer buf = new StringBuffer();
                mangleType(buf, t);
                mangledTypes.put(t, str = buf.toString());
            }
            return str;
        }

        public static void mangleType(StringBuffer buf, Type t) {
            if (isArray(t)) appendElems(buf, "_arr", t);
            else if (isFunction(t)) appendElems(buf, "_func", t);
            else if (isClass(t)) appendElems(buf, VirgilClass.declOf(t).getName(), t);
            else buf.append(t.toString());
        }

        public static void appendElems(StringBuffer buf, String tycon, Type t) {
            buf.append(tycon);
            Type[] elems = t.elements();
            if (elems.length > 0) {
                buf.append("_s1");
                for (Type pt : elems) {
                    buf.append("_");
                    mangleType(buf, pt);
                }
                buf.append("_e1");
            }
        }

        Type $mono(Map<Type, Type> map, Type type) {
            if (type == null) return null;
            type = $sub(type, map);
            Type[] oldElems = type.elements();
            Type[] newElems = oldElems;
            if (oldElems.length > 0) {
                newElems = new Type[oldElems.length];
                for (int cntr = 0; cntr < newElems.length; cntr++) {
                    newElems[cntr] = $mono(map, oldElems[cntr]);
                }
            }
            type = program.typeCache.getCached(type.rebuild(newElems));
            DeclSpec spec = getDeclSpec(type);
            if (spec != null) type = spec.getMonoType();
            return type;
        }

        private Type $sub(Type type, Map<Type, Type> map) {
            Type ntype = map.get(type);
            if (ntype == null) ntype = type;
            assert !(ntype instanceof TypeParam.IType);
            return ntype;
        }
    }

    static Map<Type, Type> fillTypeMap(Type[] cparams, Type[] tbind, Map<Type, Type> map) {
        for (int cntr = 0; cntr < cparams.length; cntr++) {
            map.put(cparams[cntr], tbind[cntr]);
        }
        return map;
    }

    static Map<Type, Type> fillTypeMap(TypeParam[] cparams, Type[] tbind, Map<Type, Type> map) {
        for (int cntr = 0; cntr < cparams.length; cntr++) {
            map.put(cparams[cntr].getType(), tbind[cntr]);
        }
        return map;
    }
}
