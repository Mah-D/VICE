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

public class VSTSwitchStmt extends VSTBaseStmt {

    public VSTExpr value;
    public List<VSTSwitchCase> cases;
    public VSTSwitchCase defcase;
    public VSTSwitchCase err_defcase;

    public VSTSwitchStmt(AbstractToken tok, VSTExpr v) {
        super(tok);
        value = v;
        cases = Ovid.newLinkedList();
    }

    public void addCase(VSTSwitchCase c) {
        cases.add(c);
    }

    public void addDefaultCase(VSTSwitchCase c) {
        if (defcase != null && err_defcase == null) err_defcase = c;
        else defcase = c;
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public <V> V accept(VSTAccumulator<V> a) {
        return a.visit(this);
    }
}
