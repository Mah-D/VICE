/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.stages;

import cck.parser.AbstractToken;
import vpc.core.Program;
import vpc.core.ProgramDecl;
import vpc.core.decl.*;
import vpc.core.types.*;
import vpc.core.virgil.VirgilClass;
import vpc.core.virgil.VirgilComponent;
import vpc.util.Ovid;
import vpc.vst.VSTErrorReporter;
import vpc.vst.VSTUtil;
import static vpc.vst.VSTUtil.vstRepOf;
import vpc.vst.tree.*;
import vpc.vst.visitor.VSTDepthFirstVisitor;

import java.util.HashSet;
import java.util.List;

/**
 * The class <code>Verifier</code> contains all of the analysis necessary to
 * detect all program errors and report them. It currently consists of three
 * phases. The first phase (phase0) makes use of the
 * <code>AbstractInterpreter</code> class for structural checks such as
 * unreachable code and uninitialized variables. The second phase is a global
 * type resolution phase that will resolve all types in the whole program
 * and check for errors such as cyclic inheritance and overriding errors.
 * The third phase encompasses type checking.
 *
 * @author Ben L. Titzer
 */
public class Verifier {

    public static void buildAndVerifyModule(Program p, VSTModule m) {
        new Verifier(p, m).phase0();
    }

    protected final VSTErrorReporter ERROR;
    protected final Program program;
    protected final VSTModule module;


    public Verifier(Program p, VSTModule m) {
        ERROR = new VSTErrorReporter();
        program = p;
        module = m;
        ProgramDecl pd = module.programDecl;
        if (pd != null) program.programDecl = pd;
    }

    /**
     * P H A S E   0   (module local)
     * ----------------------------------------------------------------
     * <p/>
     * - check lvalue correctness
     * - check return correctness for methods
     * - break and continue correctness (inside loops)
     * - detect unreachable code
     * - resolve all uses of local variables
     * - check variable initialization
     * - check for duplicate names (parameters, fields, etc)
     * - build class, component, struct summaries (e.g. Program.ClassDecl)
     * <p/>
     * Optimization opportunities
     * ----------------------------------------------------------------
     * <p/>
     * - use custom data structure for init maps (instead of HashSet)
     * - inline super (DepthFirstVisitor) traversal code
     * - optimize scopes
     */
    public void phase0() {

        module.accept(new Phase0(), null);
    }

    /**
     * The <code>P0_Accumulator</code> class is supplied to the AbstractInterpreter
     * to manage the state of the method during abstract interpretation. This
     * accumulator checks for variable initialization and lvalue correctness.
     *
     * @author Ben L. Titzer
     */
    private class P0_Accumulator implements AbstractInterpreter.Accumulator {

        private P0_ExprVisitor visitor = new P0_ExprVisitor();
        private VSTTypeDecl decl;

        P0_Accumulator(VSTTypeDecl d) {
            decl = d;
        }

        VSTTypeDecl getCurrentTypeDecl() {
            return decl;
        }

        private class P0_State implements AbstractInterpreter.State {

            private NameSpace namespace;
            private HashSet<Decl> initMap;

            P0_State() {
                initMap = Ovid.newHashSet();
                namespace = new NameSpace();
            }

            P0_State(NameSpace s, HashSet<Decl> i) {
                initMap = i;
                namespace = s;
            }

            public AbstractInterpreter.State split() {
                return new P0_State(namespace, (HashSet<Decl>) initMap.clone());
            }

            public AbstractInterpreter.State merge(AbstractInterpreter.State s) {
                if (s != null) {
                    P0_State ps = (P0_State) s;
                    initMap.retainAll(ps.initMap);
                }
                return this;
            }

        }

        private class P0_ExprVisitor extends VSTDepthFirstVisitor<Object> {
            P0_State state;

            public void visit(VSTAssign e, Object env) {
                e.value.accept(this, env);
                if (e.target instanceof VSTVarUse) {
                    Decl d = resolveVarUse((VSTVarUse) e.target);
                    if (d != null) state.initMap.add(d);
                }
                e.target.accept(this, env);
            }

            private Decl resolveVarUse(VSTVarUse u) {
                Decl decl;
                if (u.binding == null) {
                    decl = state.namespace.resolveDecl(u.getName());
                    if ( decl instanceof VSTVarDecl )
                        u.binding = new VSTBinding.Local((VSTVarDecl) decl);
                } else {
                    decl = ((VSTBinding.Local)u.binding).decl;
                }
                return decl;
            }

            public void visit(VSTCompoundAssign e, Object env) {
                e.target.accept(this, env);
                e.value.accept(this, env);
            }

            public void visit(VSTVarUse u, Object env) {
                Decl d = resolveVarUse(u);
                if (d != null) {
                    if (!state.initMap.contains(d)) ERROR.VariableNotInitialized(u);
                }
            }

            public void visit(VSTThisLiteral l, Object env) {
                if (!(decl instanceof VSTClassDecl)) ERROR.ThisMustBeInClass(l);
            }

            public void visit(VSTTernaryExpr e, Object env) {
                AbstractInterpreter.BranchStates bs = visitCond(e.cond, state);
                AbstractInterpreter.State ts = accumulate(e.trueExpr, bs.trueState);
                AbstractInterpreter.State fs = accumulate(e.falseExpr, bs.falseState);
                state = (P0_State) ts.merge(fs);
            }
        }

        public AbstractInterpreter.State initialState(VSTTypeDecl d, VSTMethodDecl m) {
            P0_State ps = new P0_State();
            addParams(ps, m.params);
            return ps;
        }

        public AbstractInterpreter.State initialState(VSTTypeDecl d, VSTConstructorDecl c) {
            P0_State ps = new P0_State();
            addParams(ps, c.params);
            return ps;
        }

        public AbstractInterpreter.State initialState(VSTTypeDecl d, VSTStmt s) {
            return new P0_State();
        }

        private void addParams(P0_State ps, List<VSTParamDecl> params) {
            for (VSTParamDecl p : params) {
                String name = p.getName();
                if (ps.namespace.getDecl(name) != null) ERROR.ParameterRedefined(p);
                ps.namespace.addDecl(name, p);
                ps.initMap.add(p);
            }
        }

        public AbstractInterpreter.State accumulate(VSTExpr e, AbstractInterpreter.State s) {
            visitor.state = (P0_State) s;
            e.accept(visitor, null);
            return s;
        }

        public AbstractInterpreter.State accumulate(List<VSTExpr> e, AbstractInterpreter.State s) {
            visitor.state = (P0_State) s;
            visitor.visitExprs(e, null);
            return s;
        }

        public AbstractInterpreter.State accumulate(VSTLocalVarDecl d, AbstractInterpreter.State s) {
            P0_State ps = (P0_State) s;
            String name = d.getName();
            if (ps.namespace.resolveDecl(name) != null) ERROR.VariableRedefined(d);

            if (d.init == null && d.typeRef == null) ERROR.LocalMustHaveTypeOrInit(d);

            if (d.init != null) {
                accumulate(d.init, ps);
                ps.initMap.add(d);
            }
            ps.namespace.addDecl(name, d);
            return s;
        }

        public AbstractInterpreter.BranchStates visitCond(VSTExpr e, AbstractInterpreter.State s) {
            // a null conditional (e.g. in a for loop) is considered always true
            if (e == null) return new AbstractInterpreter.BranchStates(s, null);

            // handle the ! conditional operator
            VSTUnaryOp u = asNot(e);
            if (u != null) return visitCond(u.expr, s).commute();

            // handle the binary conditional operators
            VSTBinOp b = asBinOp(e);
            if (b != null) {
                if (b.matches("and")) {
                    AbstractInterpreter.BranchStates bsl = visitCond(b.left, s);
                    AbstractInterpreter.BranchStates bsr = visitCond(b.right, bsl.trueState);
                    return new AbstractInterpreter.BranchStates(bsr.trueState, bsl.falseState.merge(bsr.falseState));
                } else if (b.matches("or")) {
                    AbstractInterpreter.BranchStates bsl = visitCond(b.left, s);
                    AbstractInterpreter.BranchStates bsr = visitCond(b.right, bsl.falseState);
                    return new AbstractInterpreter.BranchStates(bsl.trueState.merge(bsr.trueState), bsr.falseState);
                }
            }

            // default case is to simply accumulate the expression
            AbstractInterpreter.State ns = accumulate(e, s);
            return new AbstractInterpreter.BranchStates(ns, ns.split());
        }

        private VSTUnaryOp asNot(VSTExpr e) {
            if (e instanceof VSTUnaryOp) {
                VSTUnaryOp u = (VSTUnaryOp) e;
                if (u.token.image.equals("!")) return u;
            }
            return null;
        }

        private VSTBinOp asBinOp(VSTExpr e) {
            if (e instanceof VSTBinOp) return (VSTBinOp) e;
            return null;
        }

        public AbstractInterpreter.State enterScope(AbstractInterpreter.State s) {
            P0_State ps = (P0_State) s;
            ps.namespace = new NameSpace(ps.namespace);
            return s;
        }

        public AbstractInterpreter.State leaveScope(AbstractInterpreter.State s) {
            if (s == null) return s;
            P0_State ps = (P0_State) s;
            if (ps.namespace != null) ps.namespace = ps.namespace.getFirstParent();
            return s;
        }
    }


    private class Phase0 extends VSTDepthFirstVisitor<Object> {

        P0_Accumulator accumulator;
        CompoundDecl compound;
        AIERROR methodErrorReporter = new AIERROR();

        public void visit(VSTClassDecl d, Object env) {
            redefinedCheck(d);

            if (d.errConstr != null) ERROR.ConstructorRedefined(d.errConstr, d.declaredConstr);

            AbstractToken pn = d.parent != null ? d.parent.token : null;
            VirgilClass cd = program.virgil.newClass(d.token, pn, TypeParam.toTypeParamArray(d.typeParams));
            compound = cd;
            cd.setSourceRep(d);

            // visit members
            visitMembers(d, env);
        }

        private void redefinedCheck(VSTTypeDecl d) {
            String name = d.getName();
            // check to see if the default type environment defines this type
            TypeCon tc = program.typeEnv.resolveTypeCon(name);
            if ( tc != null) ERROR.BuiltinRedefined(d);
            // check for previous class declaration.
            VirgilClass cls = program.virgil.getClassDecl(name);
            if (cls != null) {
                VSTClassDecl cd = VSTUtil.vstRepOf(cls);
                ERROR.TypeRedefined(d, cd);
            }
            // check for previous component declaration.
            VirgilComponent cmp = program.virgil.getComponentDecl(name);
            if (cmp != null) {
                VSTComponentDecl cd = VSTUtil.vstRepOf(cmp);
                ERROR.TypeRedefined(d, cd);
            }
        }

        public void visit(VSTComponentDecl d, Object env) {
            redefinedCheck(d);

            if (d.errConstr != null) ERROR.ConstructorRedefined(d.errConstr, d.declaredConstr);

            VirgilComponent cd = program.virgil.newComponent(d.token);
            compound = cd;
            cd.setComponentRep(d);

            // visit members
            visitMembers(d, env);
        }

        private void visitMembers(VSTTypeDecl d, Object env) {
            accumulator = new P0_Accumulator(d);
            visitFieldDecls(d.fields, env);
            visitMethodDecls(d.methods, env);
            visitConstructor(d, env);
        }

        private void visitConstructor(VSTTypeDecl d, Object env) {
            visitIfNonNull(d.declaredConstr, env);
        }

        private Mode buildMode(VSTMemberDecl d) {
            int modeval = 0;
            for (VSTModifier m : d.modifiers) {
                AbstractToken tok = m.getToken();
                modeval = set(m, modeval, getModeFlag(tok, m));
            }
            Mode mode = new Mode(modeval);
            d.setMode(mode);
            return mode;
        }

        private int getModeFlag(AbstractToken tok, VSTModifier m) {
            int i = Mode.modeFlags.getFlagIndex(tok.image);
            if (i == -1) ERROR.InvalidModifier(m);
            else i = 1 << i;
            return i;
        }

        private int set(VSTModifier m, int mode, int m1) {
            if ((mode & m1) != 0) ERROR.RedundantModifier(m);
            return mode | m1;
        }

        public class AIERROR implements AbstractInterpreter.AbsIntErrorReporter {
            VSTBlock block;

            public void UnreachableCode(VSTNode n, AbstractInterpreter.State st) {
                ERROR.UnreachableCode(n);
            }

            public void MisplacedLoopControlStatement(VSTNode n, AbstractInterpreter.State st) {
                ERROR.StatementMustBeInLoop(n);
            }

            public void MissingReturn(VSTNode n, AbstractInterpreter.State st) {
                ERROR.MissingReturn(block);
            }

            public void NonVoidReturn(VSTNode n, AbstractInterpreter.State st) {
                ERROR.NonVoidReturn(n);
            }

            public void VoidReturn(VSTNode n, AbstractInterpreter.State st) {
                ERROR.VoidReturn(n);
            }

            public void NotAStatement(VSTNode n, AbstractInterpreter.State st) {
                ERROR.NotAStatement(n);
            }

            public void MultipleDefaultCase(VSTSwitchStmt s, AbstractInterpreter.State st) {
                ERROR.DefaultCaseRedefined(s.err_defcase, s.defcase);
            }
        }

        public void visit(VSTMethodDecl md, Object env) {
            String name = md.getName();

            // check for a previous declaration of this method.
            Member pm = compound.getLocalMember(name);
            if (pm != null) ERROR.MemberRedefined(md, VSTUtil.vstRepOf(pm));

            Mode mode = buildMode(md);
            Method m = compound.newMethod(mode.isPrivate(), md.token, md.memberType, TypeParam.toTypeParamArray(md.typeParams));
            m.addMethodRep(VSTUtil.REPRESENTATION_KEY, md);

            if (md.block != null) {
                methodErrorReporter.block = md.block;
                AbstractInterpreter interpreter = new AbstractInterpreter(accumulator, methodErrorReporter);
                interpreter.run(accumulator.getCurrentTypeDecl(), md);
            }
        }

        public void visit(VSTConstructorDecl cd, Object env) {
            for (VSTParamDecl param : cd.params) {
                VSTFieldDecl field = vstRepOf(compound.getLocalField(param.getName()));
                if (field != null) {
                    param.field = field;
                    if (param.type == null) param.type = field.memberType;
                } else if (param.type == null) {
                    ERROR.InvalidConstructorParam(param, "field or value \""+param.getName()+"\" not found");
                }
            }
            cd.rebuildMemberType();
            Constructor c = compound.newConstructor(cd.memberType);
            c.addMethodRep(VSTUtil.REPRESENTATION_KEY, cd);

            if (cd.block != null) {
                methodErrorReporter.block = cd.block;
                AbstractInterpreter interpreter = new AbstractInterpreter(accumulator, methodErrorReporter);
                interpreter.run(accumulator.getCurrentTypeDecl(), cd);
            }
        }

        public void visit(VSTFieldDecl fd, Object env) {
            String name = fd.getName();
            Mode m = buildMode(fd);

            // check for a previous declaration of this field.
            Member prev = compound.getLocalMember(name);
            if (prev != null) ERROR.MemberRedefined(fd, VSTUtil.vstRepOf(prev));
            if (fd.assign != null) {
                // execute the assign in the abstract interpreter (to check for some errors and resolve locals)
                AbstractInterpreter interpreter = new AbstractInterpreter(accumulator, methodErrorReporter);
                interpreter.execute(accumulator.getCurrentTypeDecl(), fd.assign);
            }
            Field f = compound.newField(m.isPrivate(), fd.token, fd.memberType);
            f.setFieldRep(fd);
        }
    }

}
