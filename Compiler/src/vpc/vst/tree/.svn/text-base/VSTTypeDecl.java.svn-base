/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tree;

import cck.parser.AbstractToken;
import vpc.core.types.TypeEnv;
import vpc.util.Ovid;

import java.util.List;

public abstract class VSTTypeDecl implements VSTNode {
    public AbstractToken token;
    public List<VSTFieldDecl> fields;
    public List<VSTMethodDecl> methods;
    public VSTConstructorDecl declaredConstr;
    public VSTConstructorDecl errConstr;
    public TypeEnv typeEnv;

    protected VSTTypeDecl(VSTModule m, AbstractToken tok, TypeEnv te) {
        token = tok;
        fields = Ovid.newLinkedList();
        methods = Ovid.newLinkedList();
        typeEnv = te;
    }

    public void addFieldDecl(VSTFieldDecl f) {
        fields.add(f);
    }

    public AbstractToken getToken() {
        return token;
    }

    public String getName() {
        return token.toString();
    }

    public void addMethodDecl(VSTMethodDecl m) {
        methods.add(m);
    }

    public void addConstructor(VSTConstructorDecl m) {
        if (declaredConstr != null && errConstr == null) errConstr = m;
        else declaredConstr = m;

    }
}
