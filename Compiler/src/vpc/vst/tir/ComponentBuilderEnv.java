/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 26, 2006
 */

package vpc.vst.tir;

import cck.util.Util;
import vpc.core.decl.Field;
import vpc.core.virgil.VirgilComponent;
import vpc.tir.TIRExpr;
import vpc.tir.TIROperator;
import vpc.tir.expr.Operator;
import vpc.vst.tree.VSTConstructorDecl;
import vpc.vst.tree.VSTSuperClause;

/**
 * @author Ben L. Titzer
 */
public class ComponentBuilderEnv extends BuilderEnv {

    ComponentBuilderEnv(TIRBuilder tirBuilder, VirgilComponent d) {
        super(tirBuilder, d);
    }

    TIRExpr newThisRef() {
        throw Util.failure("\"this\" not defined for components");
    }

    TIROperator getField(Field field) {
        Operator op = new VirgilComponent.GetField(field);
        return new TIROperator(op);
    }

    VSTSuperClause getSuperClause(VSTConstructorDecl cd) {
        return null;
    }

}
