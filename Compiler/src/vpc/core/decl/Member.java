/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Apr 9, 2006
 */
package vpc.core.decl;

import cck.parser.AbstractToken;
import vpc.core.types.*;

/**
 * The <code>Member</code> class represents a member of a compound declaration, such
 * as a component or a class.
 *
 * @author Ben L. Titzer
 */
public abstract class Member extends BaseDecl implements Typeable {

    protected final boolean private_;
    protected final TypeRef typeRef;
    protected final CompoundDecl container;

    protected Member(CompoundDecl decl, AbstractToken nameToken, TypeRef tref, boolean p) {
        super(nameToken);
        container = decl;
        typeRef = tref;
        private_ = p;
    }

    public boolean isPrivate() {
        return private_;
    }

    public Type getType() {
        return typeRef.getType();
    }

    public boolean isField() {
        return this instanceof Field;
    }

    public CompoundDecl getCompoundDecl() {
        return container;
    }

    public static class Ref<M extends Member> {
        public final Type containerType;
        public final M memberDecl;
        public final Type memberType;
        public Ref(Type ct, M m, Type t) {
            containerType = ct;
            memberDecl = m;
            memberType = t;
        }
    }
}
