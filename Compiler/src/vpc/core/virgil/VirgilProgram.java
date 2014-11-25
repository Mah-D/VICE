/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Jun 18, 2006
 */
package vpc.core.virgil;

import cck.parser.AbstractToken;
import vpc.core.Program;
import vpc.core.decl.Field;
import vpc.core.decl.Method;
import vpc.core.types.TypeParam;
import vpc.util.HashList;
import vpc.util.Hierarchy;

/**
 * The <code>VirgilProgram</code> definition.
 *
 * @author Ben L. Titzer
 */
public class VirgilProgram {
    public final Program program;
    public final Hierarchy<VirgilClass> hierarchy;
    protected final HashList<String, VirgilClass> classes;
    protected final HashList<String, VirgilComponent> components;

    public VirgilProgram(Program p) {
        program = p;
        hierarchy = new Hierarchy<VirgilClass>();
        classes = new HashList<String, VirgilClass>();
        components = new HashList<String, VirgilComponent>();
    }

    public VirgilClass newClass(AbstractToken tn, AbstractToken pn, TypeParam[] tpl) {
        VirgilClass cr = new VirgilClass(tn, pn, tpl);
        classes.add(cr.getName(), cr);
        program.namespace.addDecl(cr.getName(), cr);
        return cr;
    }

    public VirgilComponent newComponent(AbstractToken tn) {
        VirgilComponent cr = new VirgilComponent(tn);
        components.add(cr.getName(), cr);
        program.namespace.addDecl(cr.getName(), cr);
        return cr;
    }

    public Iterable<VirgilClass> getDeclaredClasses() {
        return classes;
    }

    public Iterable<VirgilComponent> getDeclaredComponents() {
        return components;
    }

    public VirgilClass getClassDecl(String name) {
        return classes.get(name);
    }

    public VirgilComponent getComponentDecl(String name) {
        return components.get(name);
    }

    public VirgilClass getParentClass(VirgilClass c) {
        if ( c.parent == null ) {
            hierarchy.addRoot(c);
            return null;
        }
        VirgilClass parent = hierarchy.getParent(c);
        if (parent == null) {
            parent = classes.get(c.parent.image);
            if (parent != null) hierarchy.add(parent, c);
        }
        return parent;
    }

    public Method resolveMethod(VirgilClass c, String n) {
        while ( c != null ) {
            Method m = c.methods.get(n);
            if ( m != null ) return m;
            c = getParentClass(c);
        }
        return null;
    }

    public Field resolveField(VirgilClass c, String n) {
        while ( c != null ) {
            Field m = c.fields.get(n);
            if ( m != null ) return m;
            c = getParentClass(c);
        }
        return null;
    }

}
