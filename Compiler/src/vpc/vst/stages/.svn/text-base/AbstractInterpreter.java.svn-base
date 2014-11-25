/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.stages;

import vpc.core.base.PrimVoid;
import vpc.vst.tree.*;
import vpc.vst.visitor.VSTDepthFirstVisitor;

import java.util.List;

/**
 * The <code>AbstractInterpreter</code> class is an implementation of an
 * abstract interpreter for Virgil abstract syntax trees. It is meant as
 * a general purpose abstract interpreter for analysis, although it is
 * at the moment only used by the Verifier to detect unreachable code and
 * unintialized variables. The state changes of the abstract interpreter
 * and the actual state representation are factored out into two inner
 * interfaces that clients of the abstract interpreter implement for their
 * analysis. For example, the verifier's state tracks initialized variables
 * and the state change updates this state on assignments.
 *
 * @author Ben L. Titzer
 */
public class AbstractInterpreter {

    protected Context context;
    protected final AbsIntErrorReporter ERROR;

    /**
     * The <code>State</code> interface represents the accumulation of
     * abstraction execution of the program as it proceeds. State could
     * represent anything from a map of initialized variables to known
     * constants to types. States are split and merged across conditionals
     * by the abstract interpreter.
     */
    public interface State {
        public State merge(State s);

        public State split();
    }

    /**
     * The <code>BranchStates</code> class is used internally to collect
     * information about the method state after evaluation of a
     * conditional. This class helps in tracking information across
     * shortcutting of conditionals.
     */
    public static class BranchStates {
        public State trueState;
        public State falseState;

        public BranchStates(State t, State f) {
            trueState = t;
            falseState = f;
        }

        public BranchStates commute() {
            State temp = trueState;
            trueState = falseState;
            falseState = temp;
            return this;
        }
    }

    public interface AbsIntErrorReporter {
        public void UnreachableCode(VSTNode n, State st);

        public void MisplacedLoopControlStatement(VSTNode n, State st);

        public void MissingReturn(VSTNode n, State st);

        public void NonVoidReturn(VSTNode n, State st);

        public void VoidReturn(VSTNode n, State st);

        public void NotAStatement(VSTNode n, State st);

        public void MultipleDefaultCase(VSTSwitchStmt s, State st);
    }

    /**
     * The <code>Accumulator</code> interface represents the mediator
     * that manages state changes as the program is interpreted. For
     * example, for each expression encountered in the program, the
     * Accumulator computes the new state from the old state.
     */
    public interface Accumulator {
        public State initialState(VSTTypeDecl d, VSTMethodDecl m);

        public State initialState(VSTTypeDecl d, VSTStmt s);

        public State initialState(VSTTypeDecl d, VSTConstructorDecl c);

        public State accumulate(VSTExpr e, State s);

        public State accumulate(List<VSTExpr> e, State s);

        public State accumulate(VSTLocalVarDecl d, State s);

        public BranchStates visitCond(VSTExpr e, State s);

        public State enterScope(State s);

        public State leaveScope(State s);

    }

    private final Accumulator accumulator;

    /**
     * The <code>Context</code> class is used internally in the
     * AbstractInterpreter to track information about where the
     * interpreter is in the execution of the program. For example,
     * it records whether the interpreter is inside a loop, and the
     * states needed to merge back in at the reentry and exit of
     * the loop.
     */
    private class Context extends VSTDepthFirstVisitor {

        private State state;
        private boolean flowEnd;
        private boolean inLoop;
        private boolean isVoid;

        private State loopReentry;
        private State loopExit;

        private Context(State s, VSTMethodDecl m) {
            state = s;
            isVoid = PrimVoid.isVoid(m.returnType);
        }

        private Context(State s) {
            state = s;
            isVoid = true;
        }

        private Context(State s, Context prev) {
            state = s;
            loopReentry = prev.loopReentry;
            loopExit = prev.loopExit;
            inLoop = prev.inLoop;
            isVoid = prev.isVoid;
        }

        Context exec() {
            return new Context(state, this);
        }

        Context fork() {
            return new Context(state.split(), this);
        }

        private void accumulate(VSTExpr e) {
            if (e != null) state = accumulator.accumulate(e, state);
        }

        private void accumulate(List<VSTExpr> e) {
            if (e != null) state = accumulator.accumulate(e, state);
        }

        private void accumulate(List<VSTExpr> e, State s) {
            if (e != null) state = accumulator.accumulate(e, s);
        }

        private void mergeContextsIntoThis(Context c1, Context c2) {
            if (c1 == null) {
                state = c2.state;
                loopReentry = c2.loopReentry;
                loopExit = c2.loopExit;
                flowEnd = c2.flowEnd;
            } else {
                state = c1.state.merge(c2.state);
                loopReentry = mergeStates(c1.loopReentry, c2.loopReentry);
                loopExit = mergeStates(c1.loopExit, c2.loopExit);
                flowEnd = c1.flowEnd && c2.flowEnd;
            }
        }

        private void mergeContextsIntoFirst(Context c1, Context c2) {
            c1.state = mergeStates(c1.state, c2.state);
            c1.loopReentry = mergeStates(c1.loopReentry, c2.loopReentry);
            c1.loopExit = mergeStates(c1.loopExit, c2.loopExit);
            c1.flowEnd = c1.flowEnd && c2.flowEnd;
        }


        private void visitScopedStmt(VSTStmt s, Object env) {
            state = accumulator.enterScope(state);
            if (s != null) s.accept(this, env);
            state = accumulator.leaveScope(state);
        }

        public void visit(VSTSuperClause sc, Object env) {
            state = accumulator.accumulate(sc.args, state);
        }

        public void visit(VSTIfStmt s, Object env) {
            unreachableCheck(s);
            BranchStates bs = accumulator.visitCond(s.cond, state);
            Context tc = new Context(bs.trueState, this);
            Context fc = new Context(bs.falseState, this);
            tc.visitScopedStmt(s.trueStmt, env);
            fc.visitScopedStmt(s.falseStmt, env);
            mergeContextsIntoThis(tc, fc);
        }

        public void visit(VSTSwitchStmt s, Object env) {
            unreachableCheck(s);
            accumulate(s.value);
            Context tc = null; // merged version of true branches
            VSTSwitchCase defcase = s.defcase;

            for (VSTSwitchCase c : s.cases) {
                Context tmp = fork();
                tmp.accumulate(c.list, tmp.state);
                tmp.visitScopedStmt(c.stmt, env);
                if (tc == null) tc = tmp;
                else mergeContextsIntoFirst(tc, tmp);
            }
            Context fc = exec(); // false (default) branch
            if (defcase != null) fc.visitScopedStmt(defcase.stmt, env);

            if (s.err_defcase != null) ERROR.MultipleDefaultCase(s, state);

            mergeContextsIntoThis(tc, fc);
        }

        public void visit(VSTForStmt s, Object env) {
            unreachableCheck(s);
            accumulate(s.init);
            BranchStates bs = accumulator.visitCond(s.cond, state);

            Context bc = new Context(bs.trueState, this);
            bc.loopExit = bs.falseState;
            bc.loopReentry = null;
            bc.inLoop = true;

            // visit the body
            bc.visitScopedStmt(s.body, env);
            bc.mergeReentry();

            if (bc.loopReentry == null) {
                // if there is an update clause, it is unreachable
                if (s.update != null && !s.update.isEmpty()) ERROR.UnreachableCode(s.update.get(0), state);
            } else accumulate(s.update, bc.loopReentry);

            state = bc.loopExit;
            if (bc.loopExit == null) flowEnd = true;
        }

        public void visit(VSTWhileStmt s, Object env) {
            unreachableCheck(s);
            BranchStates bs = accumulator.visitCond(s.cond, state);

            Context bc = new Context(bs.trueState, this);
            bc.loopExit = bs.falseState;
            bc.loopReentry = null;
            bc.inLoop = true;

            // visit the body
            bc.visitScopedStmt(s.body, env);
            bc.mergeReentry();

            state = bc.loopExit;
        }

        public void visit(VSTDoWhileStmt s, Object env) {
            unreachableCheck(s);
            Context bc = fork();
            bc.loopExit = null;
            bc.loopReentry = null;
            bc.inLoop = true;

            bc.visitScopedStmt(s.body, env);
            bc.mergeReentry();

            BranchStates bs = accumulator.visitCond(s.cond, bc.loopReentry);
            state = bs.falseState.merge(bc.loopExit);
        }

        public void visit(VSTLocalVarDecl s, Object env) {
            unreachableCheck(s);
            state = accumulator.accumulate(s, state);
        }

        public void visit(VSTBreakStmt s, Object env) {
            unreachableCheck(s);
            loopCheck(s);
            if (loopExit == null) loopExit = state.split();
            else loopExit = loopExit.merge(state);
            flowEnd = true;
        }

        public void visit(VSTContinueStmt s, Object env) {
            unreachableCheck(s);
            loopCheck(s);
            if (loopReentry == null) loopReentry = state.split();
            else loopReentry = loopReentry.merge(state);
            flowEnd = true;
        }

        public void visit(VSTReturnStmt s, Object env) {
            unreachableCheck(s);
            if (s.expr != null) {
                if (isVoid) ERROR.NonVoidReturn(s.expr, state);
                accumulate(s.expr);
            } else if (!isVoid) {
                ERROR.VoidReturn(s, state);
            }

            flowEnd = true;
        }

        public void visit(VSTExprStmt s, Object env) {
            unreachableCheck(s);
            if (!s.expr.isStmt()) ERROR.NotAStatement(s.expr, state);
            accumulate(s.expr);
        }

        public void visit(VSTBlock b, Object env) {
            state = accumulator.enterScope(state);
            super.visit(b, env);
            state = accumulator.leaveScope(state);
        }

        void unreachableCheck(VSTNode n) {
            if (flowEnd) ERROR.UnreachableCode(n, state);
        }

        void loopCheck(VSTNode n) {
            if (!inLoop) ERROR.MisplacedLoopControlStatement(n, state);
        }

        void mergeReentry() {
            if (!flowEnd) loopReentry = state.merge(loopReentry);
        }
    }

    private static State mergeStates(State s1, State s2) {
        if (s1 == null) return s2;
        else return s1.merge(s2);
    }


    public AbstractInterpreter(Accumulator a, AbsIntErrorReporter er) {
        accumulator = a;
        ERROR = er;
    }

    public void run(VSTTypeDecl d, VSTMethodDecl m) {
        context = new Context(accumulator.initialState(d, m), m);
        m.accept(context, null);
        // end of method reached without flow end (return statement)?
        if (!PrimVoid.isVoid(m.returnType) && !context.flowEnd) ERROR.MissingReturn(null, context.state);
    }

    public void run(VSTTypeDecl d, VSTConstructorDecl c) {
        context = new Context(accumulator.initialState(d, c));
        c.accept(context, null);
    }

    public void execute(VSTTypeDecl d, VSTStmt s) {
        context = new Context(accumulator.initialState(d, s));
        s.accept(context, null);
    }
}
