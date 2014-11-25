/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 14, 2006
 */

package vpc.core.decl;

import vpc.util.Delegator;
import vpc.util.Ovid;

import java.util.Map;

/**
 * The <code>NameSpace</code> class represents a collection of declarations
 * indexed by a string name. Name spaces can be nested, so that if resolution
 * of a particular name fails in an inner scope, it can be resolved in outer
 * scopes.
 *
 * @author Ben L. Titzer
 */
public class NameSpace extends Delegator<Decl, String> {

    protected Map<String, Decl> decls;

    /**
     * The default constructor for the <code>NameSpace</code> class creates a new
     * name space with the specified parent name space.
     *
     * @param p the parent(s) of this name space
     */
    public NameSpace(NameSpace... p) {
        for (NameSpace ns : p) add(ns);
    }

    /**
     * The <code>addDecl()</code> method adds a declaration to this name space.
     * The declaration is then available in this name space as given by the name
     * specified.
     *
     * @param n the string name of the declaration
     * @param d the declaration to add to this name space
     */
    public void addDecl(String n, Decl d) {
        if (decls == null) decls = Ovid.newMap();
        decls.put(n, d);
    }

    /**
     * The <code>resolveDecl()</code> method resolves a declaration given its name
     * in this namespace and its parents.
     *
     * @param n the string name of the declaration
     * @return a reference to the declaration for the specified name if it exists in
     *         this name space or the parent namespace; null otherwise
     */
    public Decl resolveDecl(String n) {
        return invoke(n);
    }

    /**
     * The <code>invoke()</code> method overrides the delegation process. It will
     * first attempt to resolve the string in this namespace, and if it fails,
     * resort to delegation using the <code>Delegator</code> inherited functionality.
     * @param n the name to resolve
     * @return a reference to the declaration for the specified name if it exists in this
     * namesapce or any parent namespaces
     */
    public Decl invoke(String n) {
        Decl d = getDecl(n);
        if ( d == null ) return super.invoke(n);
        else return d;
    }

    /**
     * The <code>getDecl()</code> method resolves a declaration given its name
     * in this namespace. This method does not search the parent namespace.
     *
     * @param n the string name of the declaration
     * @return a reference to the declaration for the specified name if it exists in
     *         this name space; null otherwise
     */
    public Decl getDecl(String n) {
        if (decls == null) return null;
        return decls.get(n);
    }

    /**
     * The <code>getFirstParent()</code> method returns the parent name space of this
     * name space.
     *
     * @return a reference to the first namespace that is a parent of this
     */
    public NameSpace getFirstParent() {
        if (delegates.isEmpty()) return null;
        return (NameSpace)delegates.getFirst();
    }
}
