/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 8, 2006
 */

package vpc.core;

import cck.parser.AbstractToken;
import vpc.core.decl.BaseDecl;
import vpc.core.decl.Method;
import vpc.core.virgil.VirgilError;
import vpc.util.HashList;

/**
 * The <code>ProgramDecl</code> class represents a program declaration, which
 * includes a collection of the modules involved as well as entrypoints.
 *
 * @author Ben L. Titzer
 */
public class ProgramDecl extends BaseDecl {

    /**
     * The <code>EntryPoint</code> class represents a declaration of an entrypoibnt
     * into the program. The entrypoint declarations are used to connect interrupt
     * handlers and the main entrypoint of the program to declared methods of
     * components.
     */
    public static class EntryPoint extends BaseDecl {
        public final AbstractToken component;
        public final AbstractToken methodName;
        public Method method;

        public EntryPoint(AbstractToken n, AbstractToken c, AbstractToken m) {
            super(n);
            component = c;
            methodName = m;
        }
    }

    /**
     * The <code>ComponentRef</code> class represents a reference to a component
     * that is declared to be part of the program.
     */
    public static class ComponentRef extends BaseDecl {
        public ComponentRef(AbstractToken tok) {
            super(tok);
        }
    }

    public EntryPoint mainEntry;

    public final HashList<String, EntryPoint> entryPoints;
    public final HashList<String, ComponentRef> components;

    /**
     * The default constructor for the <code>ProgramDecl</code> class accepts
     * a token that represents the name of the program. The program name is used
     * to create output file(s) for the results of the compilation.
     *
     * @param tok the name of the program as a token
     */
    public ProgramDecl(AbstractToken tok) {
        super(tok);

        entryPoints = new HashList<String, EntryPoint>();
        components = new HashList<String, ComponentRef>();
    }

    public void addEntryPoint(EntryPoint p) {
        entryPoints.add(p.getName(), p);
    }

    public void addComponent(ComponentRef r) {
        components.add(r.getName(), r);
    }

    public static EntryPoint lookupEntryPoint(String epn, Program p) {
        ProgramDecl decl = p.programDecl;
        if (decl == null)
            throw VirgilError.MissingProgramDecl(p);
        EntryPoint entry = decl.entryPoints.get(epn);
        if (entry == null)
            throw VirgilError.CannotResolveEntrypoint.gen(decl.getSourcePoint(), epn);
        return entry;
    }

}
