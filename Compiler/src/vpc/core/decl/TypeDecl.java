/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.core.decl;

import cck.parser.AbstractToken;
import vpc.core.types.Type;
import vpc.core.types.TypeCon;

/**
 * The <code>TypeDecl</code> class represents the declaration of a new type
 * by the program. The new type could be a component or a class.
 *
 * @author Ben L. Titzer
 */
public abstract class TypeDecl extends BaseDecl {

    protected TypeCon typeCon;
    protected Type canonicalType;

    protected TypeDecl(AbstractToken tok) {
        super(tok);
    }

    public TypeCon getTypeCon() {
        return typeCon;
    }

    public void setTypeCon(TypeCon tc) {
        typeCon = tc;
    }

    public Type getCanonicalType() {
        return canonicalType;
    }

    public void setCanonicalType(Type t) {
        canonicalType = t;
    }

}
