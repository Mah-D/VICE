/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Apr 9, 2006
 */
package vpc.core.decl;

import cck.parser.AbstractToken;
import vpc.core.types.TypeRef;

/**
 * The <code>Field</code> class represents a field declaration inside a class or component.
 * A field has a name as a string and a type.
 *
 * @author Ben L. Titzer
 */
public class Field extends Member {
    protected final String uniqueName;

    protected FieldRep fieldRep;
    public int fieldIndex;

    public Field(CompoundDecl decl, AbstractToken nameToken, TypeRef tref) {
        this(decl, nameToken, nameToken.image, tref, false);
    }

    public Field(CompoundDecl decl, AbstractToken nameToken, String un, TypeRef tref, boolean p) {
        super(decl, nameToken, tref, p);
        uniqueName = un;
    }

    public FieldRep getFieldRep() {
        return fieldRep;
    }

    public void setFieldRep(FieldRep rep) {
        fieldRep = rep;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public String getFullName() {
        return container.getName() + ":" + name;
    }

    public String toString() {
        StringBuffer b = new StringBuffer("field ");
        b.append(name);
        b.append(": ");
        b.append(typeRef.toString());
        return b.toString();
    }

    public interface FieldRep {
    }
}
