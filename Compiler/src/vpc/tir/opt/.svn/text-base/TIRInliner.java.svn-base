/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: May 17, 2007
 */

package vpc.tir.opt;

import cck.util.Util;
import vpc.core.Program;
import vpc.core.base.PrimVoid;
import vpc.core.decl.Constructor;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.sched.Stage;
import vpc.tir.*;
import static vpc.tir.TIRUtil.*;
import vpc.util.ArrayUtil;
import vpc.util.Ovid;

import java.io.IOException;
import java.util.Map;

/**
 * The <code>TIRInliner</code> class implements the inlining optimization,
 * which expands the body of a called method into the calling method.
 *
 * @author Ben L. Titzer
 */
public class TIRInliner extends Stage {

    public interface Heuristic {
        public Method getInlineMethod(TIRCall c, TIRExpr f, TIRExpr[] args, InlineEnv env);
        public TIRExpr getThisRef(TIRCall c, TIRExpr f, InlineEnv env);
    }

    class InlineEnv {
        final InlineEnv parent;
        final Method method;
        final TIRRep rep;
        final int depth;
        Map<TIRBlock, TIRBlock> inlineBlockMap = Ovid.newMap(Ovid.WEAK);

        TIRBlock block;
        Method.Temporary rettemp;

        Map<Method.Temporary, Method.Temporary> rename;

        InlineEnv(InlineEnv p, Method b) {
            parent = p;
            method = b;
            rep = TIRUtil.getRep(method);
            depth = parent == null ? 0 : parent.depth + 1;
        }

        Method.Temporary getTemp(Method.Temporary t) {
            if ( parent == null ) return t;
            InlineEnv root = getRoot();
            Method.Temporary nt = root.rename.get(t);
            if ( nt == null ) {
                nt = root.rep.newVariable(getVariableName(t), t.getType());
                root.rename.put(t, nt);
            }
            return nt;
        }

        private String getVariableName(Method.Temporary t) {
            return mangleMethodName(method)+"_"+t.getName();
        }

        private InlineEnv getRoot() {
            InlineEnv r = this;
            while ( r.parent != null ) r = r.parent;
            if ( r.rename == null ) {
                r.rename = Ovid.newMap(Ovid.WEAK);
            }
            return r;
        }

        public String toString() {
            InlineEnv e = parent;
            StringBuffer buf = new StringBuffer(method.getFullName());
            while ( e != null ) {
                buf.insert(0, "->");
                buf.insert(0, e.method.getFullName());
                e = e.parent;
            }
            return buf.toString();
        }

        public boolean inlining() {
            return parent != null;
        }
    }


    public void visitProgram(Program p) throws IOException {
        Heuristic h = new ShapeHeuristic(p);
        Map<TIRRep, TIRExpr> map = Ovid.newMap();
        for ( Method m : p.closure.methods ) {
            TIRExpr nb = new Transformer(h, m).transform();
            map.put(TIRUtil.getRep(m), nb);
        }
        for ( Map.Entry<TIRRep, TIRExpr> e : map.entrySet() ) {
            e.getKey().setBody(e.getValue());
        }
    }

    protected class Transformer extends DepthFirstTransformer<InlineEnv> {

        protected final Heuristic heuristic;
        protected final Method method;
        protected final InlineEnv env;

        Transformer(Heuristic h, Method m) {
            heuristic = h;
            method = m;
            env = new InlineEnv(null, m);
        }

        public TIRExpr transform() {
            TIRExpr newBody = env.rep.getBody().accept(this, env);
            // TODO: don't commit the code changes until all methods have been processed
            //env.rep.setBody(newBody);
            return newBody;
        }

        public TIRExpr visit(TIRLocal.Get e, InlineEnv env) {
            return copy(e, $GET(env.getTemp(e.temp)));
        }

        public TIRExpr visit(TIRLocal.Set e, InlineEnv env) {
            return copy(e, $SET(env.getTemp(e.temp), e.value.accept(this, env)));
        }

        public TIRExpr visit(TIRCall call, InlineEnv env) {
            // transform the method and arguments
            TIRExpr meth = transform(call.func, env);
            TIRExpr[] args = transform(call.arguments, env);

            Method im = (null == heuristic) ? null : heuristic.getInlineMethod(call, meth, args, env);

            if ( im != null )  return inlineCall(im, call, meth, args, env);
            else return copy(call, $CALL(call.delegate, meth, args));
        }

        public TIRExpr visit(TIRReturn e, InlineEnv env) {
            TIRExpr nv = transform(e.value, env);
            if (env.inlining()) return inlineReturn(e, nv, env);
            else return copy(e, new TIRReturn(nv));
        }

        public TIRExpr visit(TIRBlock.Break e, InlineEnv env) {
            TIRBlock nb = env.inlineBlockMap.get(e.block);
            assert nb != null;
            return copy(e, new TIRBlock.Break(nb));
        }

        public TIRExpr visit(TIRBlock.Continue e, InlineEnv env) {
            TIRBlock nb = env.inlineBlockMap.get(e.block);
            assert nb != null;
            return copy(e, new TIRBlock.Continue(nb));
        }

        public TIRExpr visit(TIRBlock b, InlineEnv env) {
            TIRBlock nb = new TIRBlock(getInlineBlockLabel(b.label, env));
            env.inlineBlockMap.put(b, nb);
            // transform the expressions and add them to the new block
            for ( TIRExpr ne : transform(b.list, env)) {
                if ( ne != null ) nb.addExpr(ne);
            }
            return TIRUtil.copy(b, nb);
        }

        private TIRExpr inlineReturn(TIRReturn e, TIRExpr nv, InlineEnv env) {
            TIRBlock.Break brk = new TIRBlock.Break(env.block);
            if (env.rettemp == null) return copy(e, brk);
            else {
                brk.setType(env.rettemp.getType());
                return copy(e, $BLOCK($SET(env.rettemp, nv), brk));
            }
        }

        protected TIRExpr inlineCall(Method im, TIRCall call, TIRExpr meth, TIRExpr[] _args, InlineEnv env) {
            InlineEnv nenv = new InlineEnv(env, im);
            // build a new block for the body.
            nenv.block = new TIRBlock(getInlineMethodLabel(nenv));
            // assign the argument expressions to the parameters
            assignParams(call, meth, _args, nenv);
            Type retType = im.getReturnType().getType();
            if (retType == PrimVoid.TYPE) {
                // expand a void method call.
                nenv.block.addExpr(nenv.rep.getBody().accept(this, nenv));
                return copy(call, nenv.block);
            } else {
                // expand a non-void method call
                nenv.rettemp = nenv.getRoot().rep.newTemporary(retType);
                nenv.block.addExpr(nenv.rep.getBody().accept(this, nenv));
                return copy(call, $BLOCK(nenv.block, $GET(nenv.rettemp)));
            }
        }

        private void assignParams(TIRCall call, TIRExpr meth, TIRExpr[] args, InlineEnv nenv) {
            if (call.delegate) {
                // the "this" ref is bound in the delegate, need to expand it
                args = ArrayUtil.prepend(heuristic.getThisRef(call, meth, nenv.parent), args);
            }
            int cntr = 0;
            for ( Method.Temporary t : nenv.rep.getParams()) {
                nenv.block.addExpr($SET(nenv.getTemp(t), args[cntr]));
                cntr++;
            }
        }
    }

    private static String getInlineMethodLabel(InlineEnv env) {
        TIRRep meth = env.getRoot().rep;
        return meth.newLabel(mangleMethodName(env.method) +"_inline");
    }

    private static String getInlineBlockLabel(String lbl, InlineEnv env) {
        TIRRep meth = env.getRoot().rep;
        return meth.newLabel(mangleMethodName(env.method)+"_"+lbl+"_inline");
    }

    private static String mangleMethodName(Method im) {
        String mname = im instanceof Constructor ? "constructor" : im.getName();
        return im.getCompoundDecl().getName() + "_" + mname;
    }
    
    public static class ShapeHeuristic implements Heuristic {

        Program program;

        ShapeHeuristic(Program p) {
            program = p;
        }

        public Method getInlineMethod(TIRCall c, TIRExpr f, TIRExpr[] args, InlineEnv env) {
            TIRCallShape.CallShape cs = TIRCallShape.match(program, c);
            if ( cs.kind == TIRCallShape.DIRECT && env.parent == null ) {
                return ((TIRCallShape.Direct)cs).method;
            }
            return null;
        }

        public TIRExpr getThisRef(TIRCall c, TIRExpr f, InlineEnv env) {
            assert c.delegate;
            TIRCallShape.CallShape cs = TIRCallShape.match(program, c);
            if ( cs.kind == TIRCallShape.DIRECT && env.depth < 1) {
                return ((TIRCallShape.Direct)cs).thisExpr;
            }
            throw Util.failure("call shape mismatch");
        }
    }
}
