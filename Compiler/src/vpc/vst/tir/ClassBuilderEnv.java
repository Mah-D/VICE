/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 26, 2006
 */

package vpc.vst.tir;

import cck.parser.AbstractToken;
import vpc.core.decl.Field;
import vpc.core.virgil.VirgilClass;
import vpc.tir.*;
import vpc.tir.expr.Operator;
import vpc.util.Ovid;
import vpc.vst.VSTUtil;
import vpc.vst.tree.*;

import java.util.List;
import java.util.LinkedList;

/**
 * @author Ben L. Titzer
 */
public class ClassBuilderEnv extends BuilderEnv {
    private final VirgilClass classdecl;

    ClassBuilderEnv(TIRBuilder tirBuilder, VirgilClass d) {
        super(tirBuilder, d);
        classdecl = d;
    }

    TIRExpr newThisRef() {
        return new TIRLocal.Get(thisParam);
    }

    TIROperator getField(Field field) {
        Operator op = new VirgilClass.GetField((VirgilClass.IType)thisParam.getType(), field);
        return new TIROperator(op, newThisRef());
    }

    VSTSuperClause getSuperClause(VSTConstructorDecl cd) {
        if (cd != null) {
            if (cd.supclause != null) return cd.supclause;
        }
        VirgilClass.IType ptype = ((VirgilClass.IType)classdecl.getCanonicalType()).getParentType();
        if (ptype != null) {
            AbstractToken token = cd == null ? VSTUtil.vstRepOf(classdecl).token : cd.token;
            LinkedList<VSTExpr> list = Ovid.newLinkedList();
            VSTTupleExpr te = new VSTTupleExpr(classdecl.getDefaultToken("super"), list);
            VSTSuperClause supclause = new VSTSuperClause(token, te);
            supclause.target = ptype.getConstructor(this.tirBuilder.program.typeCache);
            return supclause;
        }
        return null;
    }

}
