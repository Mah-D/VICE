/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 7, 2006
 */

package vpc.core.decl;

import cck.parser.AbstractToken;
import cck.parser.SourcePoint;

/**
 * The <code>BaseDecl</code> class serves as a convenient base for many kinds of declarations
 * within the Virgil compiler, such as variables, fields, methods, types, devices, registers,
 * interrupts, etc.
 *
 * @author Ben L. Titzer
 */
public abstract class BaseDecl implements Decl {
    protected final String name;
    protected final SourcePoint point;

    protected BaseDecl(String n, SourcePoint p) {
        name = n;
        point = p;
    }

    protected BaseDecl(AbstractToken tok) {
        name = tok.image;
        point = tok.getSourcePoint();
    }

    public String getName() {
        return name;
    }

    public SourcePoint getSourcePoint() {
        return point;
    }

    public AbstractToken getToken() {
        return AbstractToken.newToken(name, point);
    }
}
