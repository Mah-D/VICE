/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;

import vpc.core.base.PrimVoid;
import vpc.util.Ovid;

import java.util.List;

/**
 * The <code>TIRBlock</code> class represents an ordered list of statements that comprise
 * a block of code in the program.
 *
 * @author Ben L. Titzer
 */
public class TIRBlock extends TIRExpr {

    public final String label;
    public final List<TIRExpr> list;

    protected final List<Continue> continueExprs;
    protected final List<Break> breakExprs;

    public TIRBlock(String l) {
        label = l;
        list = Ovid.newArrayList();
        continueExprs = Ovid.newLinkedList();
        breakExprs = Ovid.newLinkedList();
        setType(PrimVoid.TYPE); // empty block has type void
    }

    /**
     * The <code>addExpr</code> method is used to add an expression to the end of
     * this block. It simply allocates a node and puts it on the end of the list.
     *
     * @param e the expression to add to the block
     */
    public void addExpr(TIRExpr e) {
        list.add(e);
        setType(e.getType());
    }

    public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
        return v.visit(this, env);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("$BLOCK \"" + label + "\" {");
        for (TIRExpr e : list) {
            buf.append(e);
            buf.append(";\n");
        }
        buf.append("}\n");
        return buf.toString();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean isSingleton() {
        return list.size() == 1;
    }

    public boolean isLoop() {
        return !continueExprs.isEmpty();
    }

    public boolean isStraight() {
        return continueExprs.isEmpty() && breakExprs.isEmpty();
    }

    public TIRExpr getFirst() {
        if (list.isEmpty() ) return null;
        return list.get(0);
    }

    /**
     * The <code>Continue</code> class represents a continue statement that returns to the
     * beginning of a TIRBlock.
     */
    public static class Continue extends TIRExpr {
        public final TIRBlock block;
        public Continue(TIRBlock b) {
            block = b;
            block.continueExprs.add(this);
            setType(PrimVoid.TYPE);
        }

        public String toString() {
            return "continue "+block.label;
        }

        public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
            return v.visit(this, env);
        }
    }

    /**
     * The <code>Break</code> class represents a break statement that exits to the end
     * of a TIRBlock.
     */
    public static class Break extends TIRExpr {
        public final TIRBlock block;
        public Break(TIRBlock b) {
            block = b;
            block.breakExprs.add(this);
            setType(PrimVoid.TYPE);
        }

        public String toString() {
            return "break "+block.label;
        }

        public <R, E> R accept(TIRExprVisitor<R, E> v, E env) {
            return v.visit(this, env);
        }
    }
}
