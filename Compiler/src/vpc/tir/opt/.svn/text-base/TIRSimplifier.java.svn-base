/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Oct 5, 2006
 */
package vpc.tir.opt;

import vpc.core.Program;
import vpc.core.Value;
import vpc.core.base.*;
import vpc.core.csr.*;
import vpc.core.decl.Method;
import vpc.core.virgil.VirgilClass;
import vpc.core.virgil.VirgilComponent;
import vpc.sched.Stage;
import vpc.tir.*;
import static vpc.tir.TIRUtil.*;
import vpc.tir.expr.Operator;
import vpc.util.Ovid;
import vpc.hil.Device;

import java.io.IOException;
import java.util.*;

/**
 * The <code>TIRSimplifier</code> class simplifies complicated blocks of code,
 * flattening unnecessary nesting and lifting out subexpressions that can have
 * side-effects into a canonical order. This avoids ambiguous evaluation
 * order problems in generating C code, which was previously handled with
 * complex lifting built into the code generator.
 *
 * @author Ben L. Titzer
 */
public class TIRSimplifier extends Stage {

    protected static final Set<Class> simple;

    public static final boolean LIFT_THROUGH = true;

    static {
        simple = Ovid.newSet();

        // operators for comparing values have no side effects
        simple.add(Value.Equal.class);
        simple.add(Value.NotEqual.class);
        // operators for PrimInt32 have no side effects
        simple.add(PrimInt32.ADD.class);
        simple.add(PrimInt32.SUB.class);
        simple.add(PrimInt32.MUL.class);
        simple.add(PrimInt32.NEG.class);
        simple.add(PrimInt32.EQU.class);
        simple.add(PrimInt32.NEQU.class);
        simple.add(PrimInt32.GR.class);
        simple.add(PrimInt32.LT.class);
        simple.add(PrimInt32.GREQ.class);
        simple.add(PrimInt32.LTEQ.class);

        // operators for PrimChar have no side effects
        simple.add(PrimChar.EQU.class);
        simple.add(PrimChar.NEQU.class);
        simple.add(PrimChar.GR.class);
        simple.add(PrimChar.LT.class);
        simple.add(PrimChar.GREQ.class);
        simple.add(PrimChar.LTEQ.class);

        simple.add(PrimBool.NOT.class);

        // raw operators do not have side effects (except setbit)
        simple.add(PrimRaw.AND.class);
        simple.add(PrimRaw.OR.class);
        simple.add(PrimRaw.XOR.class);
        simple.add(PrimRaw.SHL.class);
        simple.add(PrimRaw.SHR.class);
        simple.add(PrimRaw.GetBit.class);
        simple.add(PrimRaw.Complement.class);

        // primitive conversions do not have side effects
        simple.add(PrimConversion.AdjustRaw.class);
        simple.add(PrimConversion.Char_Int32.class);
        simple.add(PrimConversion.Char_Raw.class);
        simple.add(PrimConversion.Int32_Char.class);
        simple.add(PrimConversion.Int32_Raw.class);
        simple.add(PrimConversion.Raw_Char.class);
        simple.add(PrimConversion.Raw_Int32.class);

        // the getmethod operators don't directly cause side effects
        simple.add(VirgilClass.GetMethod.class);
        simple.add(VirgilComponent.GetMethod.class);

        // the pointer, array, and struct read operators are simple
        simple.add(CSRArray.GetElement.class);
        simple.add(CSRPointer.GetPtr.class);
        simple.add(CSRStruct.GetRefField.class);
        simple.add(CSRStruct.GetValueField.class);
        simple.add(CSRData.GetGlobal.class);

        simple.add(Device.InstrUse.class);
    }

    public void visitProgram(Program p) throws IOException {
        for ( Method m : p.closure.methods ) {
            visitMethod(m);
        }
    }

    public void visitMethod(Method m) {
        TIRRep r = getRep(m);
        Transformer transformer = new Transformer(r);
        TIRExpr nbody = transformer.simplifyBlock(false, r.getBody());
        r.setBody(nbody);
        m.addMethodRep(TIRRep.REP_NAME, r);
    }

    protected static class Transformer extends TIRExprVisitor<TIRExpr, Transformer.Context> {

        protected Map<TIRBlock, TIRBlock> blockMap = Ovid.newMap(Ovid.WEAK);

        public class Context {
            final boolean expression;
            final TIRBlock block;
            final Method.Temporary temp;
            public Context(boolean b, TIRBlock bl) {
                expression = b;
                block = bl;
                temp = null;
            }
            public Context(boolean b, Method.Temporary t, TIRBlock bl) {
                expression = b;
                block = bl;
                temp = t;
            }
            public TIRExpr lift(TIRExpr e) {
                if ( expression )
                    return TIRUtil.lift(method, temp, e, block);
                else {
                    block.addExpr(e);
                    return null;
                }
            }

            public TIRExpr simple(TIRExpr e) {
                if ( expression ) return e;
                else return null;
            }
        }

        protected final TIRRep method;

        Transformer(TIRRep r) {
            method = r;
        }

        public TIRExpr visit(TIRExpr e, Context c) {
            throw TIRUtil.fail("unknown TIR expression kind in simplifier", e);
        }

        public TIRExpr visit(TIROperator e, Context c) {
            TIRExpr ne = copy(e, $_OP(e.operator, simplifyExprs(e.operands, c)));
            if ( isSimple(ne) ) {
                // no side effects, return this expression in its simple form
                return c.simple(ne);
            }
            // lift the operator to the top level
            return c.lift(ne);
        }

        public TIRExpr visit(TIRConst e, Context c) {
            // simple, terminal
            return c.simple(e); // Value : simple, terminal
        }

        public TIRExpr visit(TIRConst.Symbol e, Context c) {
            // lift a string constant
            return c.lift(dup(e));
        }

        public TIRExpr visit(TIRLocal.Get e, Context c) {
            // simple, terminal
            return c.simple(e); // Local : simple, terminal
        }

        public TIRExpr visit(TIRCall e, Context c) {
            TIRExpr nf = simplifyExpr(e.func, c);
            TIRExpr[] na = simplifyExprs(e.arguments, c);
            return c.lift(dup(e, nf, na));
        }

        public TIRExpr visit(TIRLocal.Set e, Context c) {
            TIRExpr ne = simplifyVar(e.temp, e.value, c);
            if ( ne instanceof TIRLocal.Get ) {
                // suppress self copies (v = v) here.
                TIRLocal.Get g = (TIRLocal.Get)ne;
                if ( g.temp == e.temp ) return c.lift(ne);
            }
            return c.lift(dup(e, ne));
        }

        public TIRExpr visit(TIRReturn e, Context c) {
            assert !c.expression;
            return dup(e, simplifyExpr(e.value, c));
        }

        public TIRExpr visit(TIRThrow e, Context c) {
            return dup(e);
        }

        public TIRExpr visit(TIRIfExpr e, Context c) {
            TIRExpr nc = simplifyExpr(e.condition, c);
            if ( isValue(nc) ) {
                // perform constant folding
                Value cv = valOf(nc);
                if ( Value.compareValues(cv, PrimBool.TRUE) ) {
                    return simplifyBranch(e.true_target, c);
                }
                if ( Value.compareValues(cv, PrimBool.FALSE) ) {
                    return simplifyBranch(e.false_target, c);
                }
            }
            if ( c.expression ) {
                // if this if is an expression, lift to the top level and introduce a temp
                Method.Temporary temp = method.newTemporary(e.getType());
                TIRExpr nt = simplifyBlock(false, new TIRLocal.Set(temp, e.true_target));
                TIRExpr nf = simplifyBlock(false, new TIRLocal.Set(temp, e.false_target));
                c.block.addExpr(dup(e, nc, nt, nf));
                // the overall result is the value of the temporary
                return new TIRLocal.Get(temp);
            } else {
                // otherwise, simplify this if expression as a statement
                TIRExpr nt = simplifyBlock(c.expression, e.true_target);
                TIRExpr nf = simplifyBlock(c.expression, e.false_target);
                return c.lift(dup(e, nc, nt, nf));
            }
        }

        public TIRExpr visit(TIRSwitch e, Context c) {
            TIRExpr nv = simplifyExpr(e.expr, c);
            TIRSwitch.Case[] nc = new TIRSwitch.Case[e.cases.length];
            for ( int cntr = 0; cntr < nc.length; cntr++ ) {
                TIRSwitch.Case oc = e.cases[cntr];
                nc[cntr] = new TIRSwitch.Case(oc.value, simplifyBlock(false, oc.body));
            }
            TIRSwitch.Case ndef = null;
            if ( e.defcase != null)
                ndef = new TIRSwitch.Case(e.defcase.value, simplifyBlock(false, e.defcase.body));
            return c.lift(dup(e, nv, nc, ndef));
        }

        public TIRExpr visit(TIRBlock e, Context c) {
            if ( c.expression ) {
                // rebuild the block as an expression
                assert e.isStraight(); // expression blocks cannot have control flow
                return visitBlock(true, e.list, c.block);
            } else if ( e.isStraight() ) {
                // a straight block can be collapsed up
                visitBlock(false, e.list, c.block);
                return null;
            } else {
                // the block needs to be completely rebuilt
                TIRBlock nb = new TIRBlock(e.label);
                blockMap.put(e, nb);
                return visitBlock(false, e.list, nb);
            }
        }

        protected TIRExpr visitBlock(boolean expr, List<TIRExpr> l, TIRBlock b) {
            int size = l.size();
            for ( int cntr = 0; cntr < size; cntr++ ) {
                TIRExpr se = l.get(cntr);
                if ( expr && cntr == size - 1 ) {
                    return simplifyExpr(se, new Context(true, b));
                } else {
                    TIRExpr ne = se.accept(this, new Context(false, b));
                    if ( ne != null ) b.addExpr(ne);
                }
            }
            return b;
        }

        public TIRExpr visit(TIRBlock.Break e, Context c) {
            assert !c.expression;
            TIRBlock nb = blockMap.get(e.block);
            if ( nb != null ) return copy(e, new TIRBlock.Break(nb));
            return e;
        }

        public TIRExpr visit(TIRBlock.Continue e, Context c) {
            assert !c.expression;
            TIRBlock nb = blockMap.get(e.block);
            if ( nb != null ) return copy(e, new TIRBlock.Continue(nb));
            return e;
        }

        protected TIRExpr[] simplifyExprs(TIRExpr[] e, Context c) {
            TIRExpr[] ne = new TIRExpr[e.length];
            for ( int cntr = 0; cntr < e.length; cntr++ ) {
                ne[cntr] = simplifyExpr(e[cntr], c);
            }
            return ne;
        }

        private TIRExpr simplifyVar(Method.Temporary var, TIRExpr e, Context c) {
            TIRExpr n = e.accept(this, new Context(true, var, c.block));
            return copy(e, n);
        }

        protected TIRExpr simplifyExpr(TIRExpr e, Context c) {
            TIRExpr n = e.accept(this, new Context(true, c.block));
            return copy(e, n);
        }

        protected TIRExpr simplifyBranch(TIRExpr e, Context c) {
            TIRExpr n = e.accept(this, new Context(c.expression, c.block));
            if ( n == null ) return n;
            return copy(e, n);
        }

        protected TIRExpr simplifyBlock(boolean expr, TIRExpr e) {
            TIRBlock b = new TIRBlock(method.newLabel("K"));
            Context c = new Context(expr, b);
            TIRExpr ne = e.accept(this, c);
            if ( ne != null ) b.addExpr(ne);
            if ( b.isEmpty() ) return $NOOP(e.getSourcePoint());
            if ( b.isSingleton() ) return b.getFirst();
            return copy(e, b);
        }

    }

    protected static boolean isSimple(Operator op) {
        return simple.contains(op.getClass());
    }

    protected static boolean isSimple(TIRExpr e) {
        return e instanceof TIROperator && isSimple(((TIROperator)e).operator);
    }

    public static TIRExpr $_OP(Operator op, TIRExpr... list) {
        if ( isSimple(op) ) {
            try {
                Value[] vals = TIRUtil.reduce(list);
                if ( vals != null )
                    return $VAL(op.apply(null, vals));
            } catch ( Throwable t) {
                // do nothing and return basic operator in case of exception
            }
        }
        return $OP(op, list);
    }
}
