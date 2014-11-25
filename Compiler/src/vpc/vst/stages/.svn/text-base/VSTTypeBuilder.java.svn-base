/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.stages;

import cck.parser.AbstractToken;
import vpc.core.Program;
import vpc.core.base.*;
import vpc.core.decl.*;
import vpc.core.types.*;
import vpc.core.virgil.*;
import vpc.util.Cache;
import vpc.util.Ovid;
import vpc.vst.VSTErrorReporter;
import static vpc.vst.VSTUtil.getVisibleMember;
import static vpc.vst.VSTUtil.vstRepOf;
import vpc.vst.tree.*;

import java.util.List;
import java.util.Set;

/**
 * The inner class <code>VSTTypeBuilder</code> contains the portion
 * of the verifier that will build the global type system, including
 * the inheritance tree, and build several important properties of the
 * whole program. The whole program must be present for this analysis.
 *
 * @author Ben L. Titzer
 */
public class VSTTypeBuilder {
    private final Set<String> stack;
    private final Set<VirgilClass> built;
    private final Set<VirgilClass> checked;
    private VSTErrorReporter ERROR;
    private Program program;

    public VSTTypeBuilder(Program p, VSTErrorReporter error) {
        built = Ovid.newSet();
        stack = Ovid.newSet();
        checked = Ovid.newSet();
        ERROR = error;
        program = p;
    }

    public static List<TypeRef> toFuncTypeRefs(TypeRef rt, List<VSTParamDecl> d) {
        List<TypeRef> list = Ovid.newLinkedList();
        for (VSTParamDecl p : d) list.add(p.getTypeRef());
        list.add(rt);
        return list;
    }

    public void buildProgramTypes(Program p) {
        // bind component type constructors
        for (VirgilComponent cd : p.virgil.getDeclaredComponents()) bindTypeCon(cd);
        // bind class type constructors
        for (VirgilClass cd : p.virgil.getDeclaredClasses()) bindTypeCon(cd);

        // check for cyclic inheritance
        for (VirgilClass cd : p.virgil.getDeclaredClasses()) {
            // check the inheritance for cycles, other problems
            checkInheritance(cd);
        }

        // check class members and inheritance
        for (VirgilClass cd : p.virgil.getDeclaredClasses()) checkClass(cd);
        // check component members
        for (VirgilComponent cd : p.virgil.getDeclaredComponents()) checkComponent(cd);
    }

    void checkClass(VirgilClass pcd) {
        if (!checked.contains(pcd)) {
            VirgilClass ppcd = program.virgil.getParentClass(pcd);
            if (ppcd != null) checkClass(ppcd);

            new ClassChecker(pcd, ppcd).check();
            checked.add(pcd);
        }
    }

    void checkComponent(VirgilComponent compDecl) {
        if (vstRepOf(compDecl) == null) {
            // TODO: this means the component is a builtin. Handle this more gracefully.
            return;
        }

        // check the type for each field
        for (Field f : compDecl.getFields()) {
            checkFieldResolved(vstRepOf(f), program.typeEnv);
        }

        // check the types for each method and number the type parameters
        for (Method m : compDecl.getMethods()) {
            checkMethodResolved(vstRepOf(m));
            numberTypeParams(m);
        }

        // check the constructor
        Constructor c = compDecl.getConstructor();
        if (c != null) {
            VSTConstructorDecl cd = vstRepOf(c);
            checkConstructorResolved(compDecl, cd, program.typeEnv);
            if (cd != null && cd.supclause != null) ERROR.SuperClauseMustBeInClass(cd.supclause);
        } else {
            VSTComponentDecl ccd = vstRepOf(compDecl);
            newDefaultConstructor(ccd.getToken(), compDecl, ccd.typeEnv);
        }
    }

    public static Type resolveType(VSTTypeRef tref, Cache<Type> cache, TypeEnv env, VSTErrorReporter error) {
        Type type = null;
        if (tref != null) {
            try {
                type = tref.resolveType(cache, env);
            } catch (TypeRef.Unresolved unresolved) {
                error.UnresolvedType(unresolved.name, unresolved.point);
            } catch (TypeRef.ArityMismatch arity) {
                error.TypeParamArityMismatch(arity.name, arity.point, arity.expected, arity.found);
            }
        }
        return type;
    }

    class ClassChecker {

        final VirgilClass.IType classType;
        final VirgilClass classDecl;
        final VirgilClass parentDecl;
        VSTClassDecl vstClassDecl;

        ClassChecker(VirgilClass c, VirgilClass pc) {
            classDecl = c;
            parentDecl = pc;
            classType = classDecl.getParameterizedType(program.typeCache);
            vstClassDecl = vstRepOf(classDecl);
        }

        void check() {
            // check each field
            for (Field f : classDecl.getFields()) checkField(f);
            // check each method
            for (Method m : classDecl.getMethods()) checkMethod(m);
            // check the constructor
            checkConstructor(classDecl.getConstructor(), vstClassDecl.typeEnv);
            // compute the type parameter positions
            computeTypeParams();
        }

        void computeTypeParams() {
            List<Type> tf = Ovid.newLinkedList();
            List<VirgilClass.IType> chain = VirgilClass.getChain(classType);
            TypeParam[] typeParams = classDecl.getTypeParamDecls();
            int pos = 0;
            for ( VirgilClass.IType pt : chain ) {
                Type[] tpl = pt.getTypeParams();
                for (int i = 0; i < tpl.length; i++) {
                    tf.add(tpl[i]);
                    if (pt == classType) typeParams[i].type.index = pos;
                    pos++;
                }
            }
            classDecl.completeTypeEnv = tf.toArray(Type.NOTYPES);
        }

        void checkField(Field f) {
            VSTFieldDecl fd = vstRepOf(f);
            String name = fd.getName();
            checkFieldResolved(fd, vstClassDecl.typeEnv);
            VirgilClass.IType parentType = classType.getParentType();
            if (getVisibleMember(program.typeCache, parentType, name, classDecl) != null)
                ERROR.MemberDefinedInSuper(fd);
        }


        void checkMethod(Method meth) {
            checkMethodResolved(vstRepOf(meth));
            String name = meth.getName();
            VirgilClass.IType parentType = classType.getParentType();
            Member.Ref pm = getVisibleMember(program.typeCache, parentType, name, classDecl);
            if (pm != null) {
                if (pm.memberDecl instanceof Method) {
                    Method pmeth = (Method) pm.memberDecl;
                    overrideCheck(meth, pm);
                    if ( pmeth.family == null ) pmeth.family = new Method.Family(pmeth);
                    meth.family = pmeth.family;
                    meth.family.addMethod(pmeth, meth);
                } else {
                    ERROR.MemberDefinedInSuper(vstRepOf(meth));
                }
            }
            numberTypeParams(meth);
        }

        void checkConstructor(Constructor c, TypeEnv env) {
            if (c == null) {
                if (mustCallSuper()) ERROR.NoDefaultConstructor(vstClassDecl);
                else {
                    newDefaultConstructor(vstClassDecl.getToken(), classDecl, env);
                }
            } else {
                VSTConstructorDecl cd = vstRepOf(c);
                checkConstructorResolved(classDecl, cd, vstClassDecl.typeEnv);

                if (cd.supclause != null) {
                    if (parentDecl == null) ERROR.NoSuperClass(classDecl, cd.supclause);
                    cd.supclause.target = classType.getParentType().getConstructor(program.typeCache);
                } else if (mustCallSuper()) {
                    ERROR.NoDefaultConstructor(cd);
                }
            }
        }

        boolean mustCallSuper() {
            return parentDecl != null && parentDecl.hasParameterizedConstructor();
        }

        void overrideCheck(Method m, Member.Ref parentMethRef) {

            VSTMethodDecl md = vstRepOf(m);

            // check the type parameters
            TypeParam[] newParams = m.getTypeParams();
            TypeParam[] supParams = ((Method)parentMethRef.memberDecl).getTypeParams();
            if (newParams.length != supParams.length )
                ERROR.CannotOverrideTypeParams(md);

            // substitute in the new type parameters for the old
            Type newtype = TypeParam.substitute(program.typeCache, supParams, TypeParam.toTypes(newParams), parentMethRef.memberType);
            Function.IType parentMethType = (Function.IType)newtype;

            // check the return type
            if (!checkOverrideReturnType(m, parentMethType))
                ERROR.CannotOverrideReturnType(md);

            // check the argument arity
            Type[] pa = Tuple.getParameterTypes(parentMethType);
            if (pa.length != md.params.size()) ERROR.CannotOverrideArity(md);

            // check every parameter type
            int cntr = 0;
            for (VSTParamDecl pd : md.params) {
                Type ptype = pa[cntr];
                if (!checkOverrideParamType(pd, ptype))
                    ERROR.CannotOverrideParamType(md, pd);
                cntr++;
            }
        }

        private boolean checkOverrideParamType(VSTParamDecl pd, Type ptype) {
            // in Virgil3, the parameter types are allowed to be contra-variant for overrides.
            if (program.language instanceof V3Language)
                return program.typeSystem.isAssignable(pd.getType(), ptype);
            return ptype == pd.getType();
        }

        private boolean checkOverrideReturnType(Method m, Function.IType parentMethType) {
            // in Virgil3, the return type is allowed to be co-variant for overrides.
            if (program.language instanceof V3Language)
                return program.typeSystem.isAssignable(m.getReturnType().getType(), parentMethType.getResultType());
            return m.getReturnType().getType() == parentMethType.getResultType();
        }

    }

    void checkInheritance(VirgilClass classDecl) {
        if (built.contains(classDecl)) return;

        VSTClassDecl vstClassDecl = vstRepOf(classDecl);
        VirgilClass parentDecl = program.virgil.getParentClass(classDecl);
        AbstractToken parentToken = classDecl.getParent();

        if (parentToken != null) {
            // check that inheritance is a-cyclic
            cyclicCheck(classDecl, parentToken.image, vstClassDecl);
            // recursively check inheritance on the parent
            if ( parentDecl != null ) checkInheritance(parentDecl);
            // check that the parent type is fully resolved
            parentResolveCheck(parentDecl, vstClassDecl);
            stack.remove(classDecl.getName());
        }
        // build the new class type
        newClassType(classDecl);
    }

    private void newClassType(VirgilClass classDecl) {
        classDecl.setCanonicalType(classDecl.getParameterizedType(program.typeCache));
        built.add(classDecl);
    }

    private void cyclicCheck(VirgilClass classDecl, String parentName, VSTClassDecl vstClassDecl) {
        stack.add(classDecl.getName());
        if (stack.contains(parentName)) {
            // check for cyclic inheritance first
            ERROR.CyclicInheritance(vstClassDecl.parent);
        }
    }

    private void parentResolveCheck(VirgilClass parentDecl, VSTClassDecl vstClassDecl) {
        Type t = resolveType(vstClassDecl.parent, program.typeCache, vstClassDecl.typeEnv, ERROR);
        if (!V2TypeSystem.isClass(t)) {
            // check that the parent type is a class
            ERROR.ExpectedObjectType(vstClassDecl.parent);
        }
        if ( parentDecl == null ) {
            ERROR.UnresolvedType(vstClassDecl.parent);
        }
    }

    void bindTypeCon(VirgilComponent compDecl) {
        TypeCon typeCon = compDecl.getTypeCon();
        program.typeEnv.bindTypeCon(compDecl.getName(), typeCon);
    }

    void bindTypeCon(VirgilClass classDecl) {
        VSTClassDecl vstClassDecl = vstRepOf(classDecl);

        // build the type constructor for this class
        TypeCon typeCon = classDecl.buildTypeCon(vstClassDecl.parent);
        program.typeEnv.bindTypeCon(classDecl.getName(), typeCon);
        // check type parameters
        checkTypeParams(vstClassDecl.typeParams, vstClassDecl.typeEnv);
    }

    void checkResolved(VSTTypeRef tref, TypeEnv env) {
        resolveType(tref, program.typeCache, env, ERROR);
    }

    void checkFieldResolved(VSTFieldDecl fd, TypeEnv env) {
        checkResolved(fd.memberType, env);
    }

    void checkMethodResolved(VSTMethodDecl md) {
        checkTypeParams(md.typeParams, md.typeEnv);
        checkResolved(md.memberType, md.typeEnv);
    }

    void checkConstructorResolved(CompoundDecl decl, VSTConstructorDecl md, TypeEnv env) {
        checkResolved(md.memberType, env);
    }

    void checkTypeParams(List<TypeParam> params, TypeEnv env) {
        if ( params == null ) return;
        for ( TypeParam p : params ) {
            if ( env.locallyBound(p.getName()) )
                ERROR.TypeParamRedefined(p);
            env.bindTypeCon(p.getName(), p.getTypeCon());
        }
    }

    private void numberTypeParams(Method meth) {
        TypeParam[] typeParams = meth.getTypeParams();
        for ( int cntr = 0; cntr < typeParams.length; cntr++ ) {
            typeParams[cntr].type.index = cntr;
            typeParams[cntr].type.inMethod = true;
        }
    }

    private void newDefaultConstructor(AbstractToken token, CompoundDecl decl, TypeEnv env) {
        List<VSTTypeRef> list = Ovid.newLinkedList();
        VSTTypeRef voidRef = new VSTTypeRef(token, PrimVoid.TYPECON, VSTTypeRef.NOPARAMSLIST);
        list.add(voidRef);
        list.add(voidRef);
        VSTTypeRef tref = new VSTTypeRef(token, Function.TYPECON, list);
        decl.newConstructor(tref);
        checkResolved(tref, env);
    }

} // End of VSTTypeBuilder
