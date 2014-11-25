/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Apr 9, 2006
 */
package vpc.core.decl;

import vpc.core.base.Function;
import vpc.core.types.TypeParam;
import vpc.core.types.TypeRef;

/**
 * The <code>Constructor</code> class represents a constructor for a component
 * or a class. The constructor contains code to initialize the fields or
 * a class or a component.
 *
 * @author Ben L. Titzer
 */
public class Constructor extends Method {

    public Constructor(CompoundDecl decl, TypeRef tref) {
        super(decl, decl.getDefaultToken("<constructor>"), tref, TypeParam.NOTYPEPARAMS, false);
    }

    public String toString() {
        return Function.buildCallableName("constructor",
                argumentTypes,
                returnType);
    }
}
