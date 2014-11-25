/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.core.decl;

import cck.parser.AbstractToken;
import vpc.core.Program;
import vpc.core.types.TypeParam;
import vpc.core.types.TypeRef;
import vpc.util.HashList;

/**
 * The <code>CompoundDecl</code> class is an abstract class that represents
 * both class declarations and component declarations in the program. This class
 * contains members, which include fields, methods, and a constructor.
 *
 * @author Ben L. Titzer
 */
public abstract class CompoundDecl extends TypeDecl {
    public final HashList<String, Method> methods;
    public final HashList<String, Field> fields;
    protected Constructor constructor;

    protected CompoundDecl(AbstractToken tok) {
        super(tok);
        methods = new HashList<String, Method>();
        fields = new HashList<String, Field>();
    }

    public Method getLocalMethod(String name) {
        return methods.get(name);
    }

    public Field getLocalField(String name) {
        return fields.get(name);
    }

    public Member getLocalMember(String name) {
        Field f = getLocalField(name);
        if (f != null) return f;
        else return getLocalMethod(name);
    }

    public Member resolveMember(String name, Program p) {
        Field f = fields.get(name);
        if (f != null) return f;
        else return methods.get(name);
    }

    public Field newField(boolean priv, AbstractToken name, TypeRef tref) {
        Field f = new Field(this, name, this.name + "_" + name.image, tref, priv);
        fields.add(name.image, f);
        return f;
    }

    public Method newMethod(boolean priv, AbstractToken name, TypeRef tn, TypeParam[] tp) {
        Method m = new Method(this, name, tn, tp, priv);
        methods.add(name.image, m);
        return m;
    }

    public Constructor newConstructor(TypeRef tn) {
        return constructor = new Constructor(this, tn);
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public Iterable<Method> getMethods() {
        return methods;
    }

    public Iterable<Field> getFields() {
        return fields;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object o) {
        return this == o;
    }

    public AbstractToken getDefaultToken(String str) {
        return AbstractToken.newToken(str, point);
    }
}
