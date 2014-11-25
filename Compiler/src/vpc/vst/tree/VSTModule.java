/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.ProgramDecl;
import vpc.core.types.TypeEnv;
import vpc.util.Ovid;
import vpc.vst.visitor.VSTVisitor;

import java.util.List;

public class VSTModule implements VSTNode {

    public final String name;
    public List<VSTTypeDecl> decls;
    public ProgramDecl programDecl;
    public TypeEnv typeEnv;

    public VSTModule(String n, TypeEnv te) {
        name = n;
        decls = Ovid.newLinkedList();
        typeEnv = te;
    }

    public void addTypeDecl(VSTTypeDecl d) {
        decls.add(d);
    }

    public <E> void accept(VSTVisitor<E> v, E env) {
        v.visit(this, env);
    }

    public AbstractToken getToken() {
        return null;
    }
}
