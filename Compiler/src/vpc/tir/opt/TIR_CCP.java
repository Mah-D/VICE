/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 2, 2006
 */

package vpc.tir.opt;

import vpc.core.Program;
import vpc.core.Value;
import vpc.core.base.PrimBool;
import vpc.core.decl.Method;
import vpc.sched.Stage;
import vpc.tir.*;
import static vpc.tir.TIRUtil.*;
import vpc.util.Ovid;

import java.io.IOException;
import java.util.*;

/**
 * The <code>TIRCCP</code> class implements conditional constant propagation
 * for TIR code.
 *
 * @author Ben L. Titzer
 */
public class TIR_CCP extends Stage {

    protected static final Approx FLOWEND = new Approx();
    protected static final Approx UNKNOWN = new Approx();

    static class Approx {
        final boolean isValue;
        final Value value;

        final boolean isTemp;
        final Method.Temporary temp;

        Approx() {
            isValue = false;
            value = null;
            isTemp = false;
            temp = null;
        }

        Approx(Value v) {
            value = v;
            isValue = true;
            isTemp = false;
            temp = null;
        }

        Approx(Method.Temporary t) {
            isTemp = true;
            temp = t;
            isValue = false;
            value = null;
        }

        boolean equals(Approx a) {
            if ( a == this ) return true;
            if ( isValue != a.isValue ) return false;
            if ( isTemp != a.isTemp ) return false;
            if ( !Value.compareValues(value, a.value)) return false;
            return temp == a.temp;
            }

        boolean definitelyEquals(Approx a) {
            if ( isValue && a.isValue && Value.compareValues(value, a.value)) return true;
            return isTemp && a.isTemp && temp == a.temp;
            }

        public String toString() {
            if ( isValue ) return "$="+value;
            else if ( isTemp ) return temp.getName();
            return "?";
        }
    }

    class BlockEnv {
        final TIRBlock block;
        final ConstEnv entry;
        ConstEnv exit;

        BlockEnv(TIRBlock b, ConstEnv e) {
            block = b;
            entry = e.branch();
        }

        void mergeReentry(ConstEnv e) {
            entry.merge(e);
        }

        void mergeExit(ConstEnv e) {
            if ( exit == null ) exit = e.branch();
            else exit.merge(e);
        }
    }


    class ConstEnv {
        boolean changed;
        HashMap<Method.Temporary, Approx> map;

        ConstEnv(HashMap<Method.Temporary, Approx> m) {
            if ( m == null ) map = Ovid.newHashMap();
            else map = m;
        }

        ConstEnv branch() {
            return new ConstEnv(cloneMap(map));
        }

        void merge(ConstEnv o) {
            for ( Map.Entry<Method.Temporary, Approx> entry : o.map.entrySet() ) {
                Method.Temporary t = entry.getKey();
                Approx a = entry.getValue();
                Approx o1 = map.get(t);
                if ( o1 != null ) {
                    // if the new value is not the same as the old, the env has changed
                    a = mergeTemp(t, o1, a);
                    if ( !o1.equals(a) )
                        changed = true;
                }
                map.put(t, a);
            }
        }

        void overwrite(ConstEnv o) {
            for ( Map.Entry<Method.Temporary, Approx> entry : o.map.entrySet() ) {
                Method.Temporary t = entry.getKey();
                Approx a = entry.getValue();
                write(t, a);
                map.put(t, a);
            }
        }

        void write(Method.Temporary t, Approx a) {
            if ( a != FLOWEND ) {
                Approx o = map.get(t);
                if ( o != null ) {
                    // if the new value is not the same as the old, the env has changed
                    if ( !o.equals(a) )
                        changed = true;
                }
                // overwrite the previous value
                map.put(t, a);
            }
        }

        Approx get(Method.Temporary t) {
            Approx a = map.get(t);
            if ( a == null ) {
                a = new Approx(t);
                map.put(t, a);
            }
            return a;
        }
    }

    public void visitProgram(Program p) throws IOException {
        for ( Method m : p.closure.methods ) {
            visitMethod(m);
        }
    }

    public void visitMethod(Method m) {
        TIRRep r = getRep(m);
        Analyzer analyzer = new Analyzer(r);
        analyzer.approx(r.getBody(), new ConstEnv(null));
        Transformer transformer = new Transformer(r, analyzer);
        r.setBody(transformer.simplifyBlock(false, r.getBody()));
    }

    protected class Analyzer extends TIRExprVisitor<Approx, ConstEnv> {

        protected final TIRRep method;
        protected final Map<TIRLocal.Get, Approx> localUses = Ovid.newMap();
        protected final Stack<BlockEnv> blocks = new Stack<BlockEnv>();

        Analyzer(TIRRep r) {
            method = r;
        }

        public Approx visit(TIRExpr e, ConstEnv env) {
            return UNKNOWN; // default is to return unknown
        }

        public Approx visit(TIROperator e, ConstEnv env) {
            Approx[] v = approx(e.operands, env);
            return UNKNOWN;
        }

        public Approx visit(TIRConst.Symbol e, ConstEnv env) {
            return UNKNOWN;
        }

        public Approx visit(TIRConst.Value e, ConstEnv env) {
            return new Approx(e.getValue());
        }

        public Approx visit(TIRLocal.Get e, ConstEnv env) {
            Approx a = env.get(e.temp);
            Approx ga = localUses.get(e);
            if ( ga != null ) a = merge2(ga, a);
            localUses.put(e, a);
            return a;
        }

        public Approx visit(TIRLocal.Set e, ConstEnv env) {
            Approx val = approx(e.value, env);
            env.write(e.temp, val);
            // update all aliases of this variable in the environment too
            for ( Method.Temporary t : env.map.keySet() ) {
                Approx a = env.get(t);
                if ( a.isTemp && a.temp == e.temp ) env.write(t, val);
            }
            return val;
        }

        public Approx visit(TIRCall e, ConstEnv env) {
            approx(e.func, env);
            approx(e.arguments, env);
            return UNKNOWN;
        }

        public Approx visit(TIRReturn e, ConstEnv env) {
            approx(e.value, env);
            return FLOWEND;
        }

        public Approx visit(TIRThrow e, ConstEnv env) {
            return FLOWEND;
        }

        public Approx visit(TIRIfExpr e, ConstEnv env) {
            Approx v = approx(e.condition, env);
            if ( v.isValue ) {
                if ( PrimBool.fromValue(v.value) ) return approx(e.true_target, env);
                else return approx(e.false_target, env);
            } else {
                ConstEnv te = env.branch();
                ConstEnv fe = env.branch();
                Approx ta = approx(e.true_target, te);
                Approx fa = approx(e.false_target, fe);
                if ( ta != FLOWEND ) env.merge(te);
                if ( fa != FLOWEND ) env.merge(fe);
                return mergeApprox(ta, fa);
            }
        }

        public Approx visit(TIRSwitch e, ConstEnv env) {
            approx(e.expr, env);
            ConstEnv entry = env.branch();
            for ( TIRSwitch.Case c : e.cases) {
                ConstEnv branch = entry.branch();
                Approx a = approx(c.body, branch);
                if ( a != FLOWEND ) env.merge(branch);
            }
            return UNKNOWN;
        }

        public Approx visit(TIRBlock e, ConstEnv env) {
            BlockEnv be = new BlockEnv(e, env);
            blocks.push(be);

            Approx a = UNKNOWN;
            do {
                // execute every statement in the block
                ConstEnv entry = be.entry.branch();
                be.entry.changed = false;
                for ( TIRExpr ie : e.list ) {
                    a = approx(ie, entry);
                }
                // merge exit, if not flow end
                if ( a != FLOWEND ) be.mergeExit(entry);
                // if the entry changed, do over
            } while ( be.entry.changed );

            // the outer environment is now the
            if ( be.exit != null ) env.overwrite(be.exit);
            blocks.pop();
            return a;
        }

        public Approx visit(TIRBlock.Break e, ConstEnv env) {
            findBlock(e.block).mergeExit(env);
            return FLOWEND;
        }

        public Approx visit(TIRBlock.Continue e, ConstEnv env) {
            findBlock(e.block).mergeReentry(env);
            return FLOWEND;
        }

        BlockEnv findBlock(TIRBlock b) {
            for ( BlockEnv be : blocks ) if ( be.block == b ) return be;
            throw TIRUtil.fail("could not find block in nesting", b);
        }

        public Approx approx(TIRExpr e, ConstEnv env) {
            return e.accept(this, env);
        }

        public Approx[] approx(TIRExpr[] e, ConstEnv env) {
            Approx[] r = new Approx[e.length];
            for ( int cntr = 0; cntr < e.length; cntr++ ) {
                r[cntr] = e[cntr].accept(this, env);
            }
            return r;
        }
    }

    protected Approx mergeApprox(Approx a, Approx b) {
        if ( a == FLOWEND ) {
            if ( b == FLOWEND ) return FLOWEND;
            return b;
        } else if ( b == FLOWEND ) {
            return a;
        }
        return merge2(a, b);
    }

    protected Approx merge2(Approx a, Approx b) {
        if (a.isValue && b.isValue && Value.compareValues(a.value, b.value)) {
            if (a.value == Value.BOTTOM) return new Approx(b.value);
            else return new Approx(a.value);
        } else if (a.isTemp && b.isTemp && a.temp == b.temp) {
            return new Approx(a.temp);
        } else {
            return UNKNOWN;
        }
    }

    protected Approx mergeTemp(Method.Temporary t, Approx a, Approx b) {
        if (a.isValue && b.isValue && Value.compareValues(a.value, b.value)) {
            if (a.value == Value.BOTTOM) return new Approx(b.value);
            else return new Approx(a.value);
        } else if (a.isTemp && b.isTemp && a.temp == b.temp) {
            return new Approx(a.temp);
        } else {
            return new Approx(t);
        }
    }

    private HashMap<Method.Temporary, Approx> cloneMap(HashMap<Method.Temporary, Approx> map) {
        return (HashMap<Method.Temporary, Approx>)map.clone();
    }

    protected class Transformer extends TIRSimplifier.Transformer {
        Analyzer analyzer;

        protected Transformer(TIRRep method, TIR_CCP.Analyzer a) {
            super(method);
            analyzer = a;
        }

        public TIRExpr visit(TIRLocal.Get g, Context c) {
            TIRExpr ne = g;
            Approx a = analyzer.localUses.get(g);
            if ( a != null ) {
                if ( a.isValue ) ne = copy(g, $VAL(a.value));
                else if ( a.isTemp ) ne = copy(g, TIRUtil.$GET(a.temp));
            }
            // simple, terminal
            return c.simple(ne);
        }
    }
}
