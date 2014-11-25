/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.tir;

import cck.parser.SourcePoint;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.Program;
import vpc.core.decl.*;
import vpc.core.virgil.VirgilClass;
import vpc.core.virgil.VirgilComponent;
import vpc.tir.TIRRep;
import vpc.util.Ovid;
import vpc.vst.VSTUtil;
import vpc.vst.tree.*;

import java.util.List;

/**
 * The <code>TIRBuilder</code> class builds the IR representation from
 * the syntax tree. The principle differences between the VST and the
 * IR tree is simplification, resolution, and specialization. VSTs
 * represent the code in a form that is closer to its lexical structure
 * than its semantic level (e.g. assignments to locals and assignments
 * to fields are represented in the same way in the VST; in the IR
 * tree, a distinction is made).
 * <p/>
 * The <code>TIRBuilder</code> class makes use of some information
 * supplied by the verification phase, such as the type of expressions
 * and resolution of variable references.
 *
 * @author Ben L. Titzer
 */
public class TIRBuilder {

    protected static final List<VSTParamDecl> NOPARAMS = Ovid.newLinkedList();

    protected final Program program;

    protected TIRBuilder(Program p) {
        program = p;
    }

    public static void buildIRForProgram(Program p) {
        TIRBuilder irb = new TIRBuilder(p);
        irb.buildIR(p);
    }

    private void buildIR(Program p) {
        for (VirgilClass pcd : p.virgil.getDeclaredClasses())
            buildCompound(pcd, new ClassBuilderEnv(this, pcd));
        for (VirgilComponent pcd : p.virgil.getDeclaredComponents())
            buildCompound(pcd, new ComponentBuilderEnv(this, pcd));
    }

    public void buildCompound(CompoundDecl pcd, BuilderEnv mbuilder) {
        for (Method m : pcd.getMethods())
            buildMethod(m, mbuilder);
        buildConstructor(pcd, mbuilder);
    }

    public void buildMethod(Method pm, BuilderEnv mbuilder) {
        if (pm == null) return;
        VSTMethodDecl md = VSTUtil.vstRepOf(pm);
        if (md != null) {
            TIRRep rep = mbuilder.buildMethod(md);
            pm.addMethodRep(TIRRep.REP_NAME, rep);
        }
    }

    public void buildConstructor(CompoundDecl pcd, BuilderEnv mbuilder) {
        Constructor consDecl = pcd.getConstructor();
        VSTConstructorDecl consVST = (VSTConstructorDecl) consDecl.getMethodRep(VSTUtil.REPRESENTATION_KEY);
        TIRRep rep = mbuilder.buildConstructor(consVST, pcd.getFields());
        consDecl.addMethodRep(TIRRep.REP_NAME, rep);
    }

    public static Util.Error fail(String msg, VSTNode n) {
        String nstr = StringUtil.quote(n.getClass());
        SourcePoint sourcePoint = n.getToken().getSourcePoint();
        return Util.failure("TIRBuilder failure: " + msg + " on node " + nstr + " @ " + sourcePoint);
    }

}
