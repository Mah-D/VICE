/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 31, 2007
 */

package vpc.tir.opt;

import vpc.core.types.Type;
import vpc.tir.*;
import vpc.util.Ovid;

import java.util.List;
import java.util.Map;

/**
 * The <code>DepthFirstTransformer</code> visits the code of each method to extract the writes
 * to fields and use of methods.
 */
public class DepthFirstTransformer<E> extends TIRExprVisitor<TIRExpr, E> {

    protected Map<TIRBlock, TIRBlock> blockMap = Ovid.newMap(Ovid.WEAK);

    public TIRExpr transform(TIRExpr e, E env) {
        return e.accept(this, env);
    }

    public TIRExpr[] transform(TIRExpr[] oargs, E env) {
        TIRExpr[] nargs = new TIRExpr[oargs.length];
        for (int cntr = 0; cntr < nargs.length; cntr++)
            nargs[cntr] = transform(oargs[cntr], env);
        return nargs;
    }

    public Type transform(Type t, E env) {
        return t;
    }

    public TIRSwitch.Case transform(TIRSwitch.Case v, E env) {
        return new TIRSwitch.Case(v.value, transform(v.body, env));
    }

    public List<TIRExpr> transform(List<TIRExpr> ol, E env) {
        List<TIRExpr> nl = Ovid.newLinkedList();
        for (TIRExpr i : ol)
            nl.add(transform(i, env));
        return nl;
    }

    public TIRExpr visit(TIRExpr e, E env) {
        return e;
    }

    public TIRExpr visit(TIROperator o, E env) {
        return label(o, new TIROperator(o.operator, transform(o.operands, env)), env);
    }

    public TIRExpr visit(TIRCall c, E env) {
        return label(c, new TIRCall(c.delegate, transform(c.func, env), transform(c.arguments, env)), env);
    }

    public TIRExpr visit(TIRLocal.Set l, E env) {
        return label(l, new TIRLocal.Set(l.temp, transform(l.value, env)), env);
    }

    public TIRExpr visit(TIRLocal.Get l, E env) {
        return label(l, new TIRLocal.Get(l.temp), env);
    }

    public TIRExpr visit(TIRReturn r, E env) {
        return label(r, new TIRReturn(transform(r.value, env)), env);
    }

    public TIRExpr visit(TIRIfExpr e, E env) {
        return label(e, new TIRIfExpr(transform(e.condition, env), transform(e.true_target, env), transform(e.false_target, env)), env);
    }

    public TIRExpr visit(TIRSwitch e, E env) {
        TIRExpr expr = transform(e.expr, env);
        TIRSwitch.Case defcase = null;
        TIRSwitch.Case[] nc = new TIRSwitch.Case[e.cases.length];
        for (int i = 0; i < e.cases.length; i++) {
            nc[i] = transform(e.cases[i], env);
        }
        if (e.defcase != null) {
            defcase = transform(e.defcase, env);
        }
        return label(e, new TIRSwitch(expr, nc, defcase), env);
    }

    public TIRExpr visit(TIRBlock ob, E env) {
        TIRBlock nb = new TIRBlock(ob.label);
        blockMap.put(ob, nb);
        // transform the expressions and add them to the new block
        for ( TIRExpr ne : transform(ob.list, env)) {
            if ( ne != null ) nb.addExpr(ne);
        }
        return label(ob, nb, env);
    }

    public TIRExpr visit(TIRBlock.Break e, E env) {
        TIRBlock nb = blockMap.get(e.block);
        assert nb != null;
        return label(e, new TIRBlock.Break(nb), env);
    }

    public TIRExpr visit(TIRBlock.Continue e, E env) {
        TIRBlock nb = blockMap.get(e.block);
        assert nb != null;
        return label(e, new TIRBlock.Continue(nb), env);
    }

    protected TIRExpr label(TIRExpr o, TIRExpr n, E env) {
        n.setSourcePoint(o.getSourcePoint());
        if ( n.getType() == null ) {
            n.setType(transform(o.getType(), env));
        }
        return n;
    }
}
