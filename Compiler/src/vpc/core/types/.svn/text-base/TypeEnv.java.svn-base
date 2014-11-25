/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 9, 2006
 */

package vpc.core.types;

import cck.parser.AbstractToken;
import vpc.util.Ovid;

import java.util.List;
import java.util.Map;

/**
 * The <code>TypeEnv</code> class represents a type environment for a program,
 * module, file, type declaration, or method. A type environment contains local
 * type declarations and aliases as well as <code>TypeRef</code> instances that
 * represent the free (i.e. unbound or unknown) types within the environment.
 * <p/>
 * To complete a type environment, all of its unbound type references must be
 * resolved. The type environment is then suitable for use in typechecking.
 *
 * @author Ben L. Titzer
 */
public class TypeEnv {

    protected final TypeEnv parent;
    protected final Map<String, TypeCon> tycons;

    /**
     * The constructor for the <code>TypeEnv</code> class creates a new type environment.
     * It accepts as a parameter the parent, or enclosing, type environment.
     *
     * @param p the parent type environment for the newly created type environment
     */
    public TypeEnv(TypeEnv p) {
        tycons = Ovid.newMap();
        parent = p;
    }

    /**
     * The <code>bindTypeCon()</code> method binds the specified string name to the
     * specified type constructor only within this environment.
     *
     * @param str the name of the type
     * @param tc  the type constructor to bind in this environment
     */
    public void bindTypeCon(String str, TypeCon tc) {
        tycons.put(str, tc);
    }

    /**
     * The <code>resolveTypeCon()</code> method attempts to resolve the specified type constructor
     * in this type environment and recursively in parent environments.
     *
     * @param nm the type constructor to resolve
     * @return the type constructor that this type reference refers to, if it exists; null otherwise
     */
    public TypeCon resolveTypeCon(String nm) {
        TypeCon tc = tycons.get(nm);
        if ( tc == null && parent != null ) tc = parent.resolveTypeCon(nm);
        return tc;
    }

    public boolean locallyBound(String nm) {
        return tycons.containsKey(nm);
    }

    public TypeRef newTypeRef(AbstractToken tname) {
        return new TypeRef(tname, null, null);
    }

    public TypeRef newTypeRef(AbstractToken tname, List<? extends TypeRef> params) {
        return new TypeRef(tname, null, params);
    }

    public TypeRef newTypeRef(AbstractToken tname, TypeCon tc, List<? extends TypeRef> params) {
        return new TypeRef(tname, tc, params);
    }
}
