/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.util.Ovid;
import vpc.vst.visitor.VSTAccumulator;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

/**
 * "{ stmt1; stmt2;  ...; stmtN; }"
 * <p/>
 * The <code>VSTBlock</code> class represents a block of statements that exist
 * in the syntax of the original source program. It is currently not used
 * to represent lists of statements that occur, for example, in a for loop's
 * init or update portions.
 *
 * @author Ben L. Titzer
 */
public class VSTBlock extends VSTBaseStmt {

    public List<VSTStmt> stmts;
    public AbstractToken end;

    public VSTBlock(AbstractToken tok) {
        super(tok);
        stmts = Ovid.newLinkedList();
    }

    public void setLastToken(AbstractToken e) {
        end = e;
    }

    public void addStmt(VSTStmt s) {
        if (s == null) return;
        if (s instanceof VSTEmptyStmt) return;
        stmts.add(s);
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }

}
