/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: May 29, 2007
 */

package vpc.tir.stages;

import vpc.core.Program;
import vpc.core.ProgramDecl;
import vpc.core.decl.Constructor;
import vpc.core.decl.Method;
import vpc.core.virgil.*;
import vpc.sched.Stage;
import vpc.util.Ovid;

import java.io.IOException;

/**
 * @author Ben L. Titzer
 */
public class SimpleClosure extends Stage {

    public void visitProgram(Program p) throws IOException {
        Closure cl = p.closure;
        VirgilProgram vp = p.virgil;

        // resolve any declared entrypoints
        resolveEntryPoints(p);

        // compute the sets of classes, methods, and components
        cl.methods = Ovid.newSet();
        cl.classes = Ovid.newList();
        cl.components = Ovid.newList();

        // add all components and their methods
        for (VirgilComponent cd : vp.getDeclaredComponents()) {
            cl.components.add(cd);
            Constructor cons = cd.getConstructor();
            if ( cons != null ) cl.methods.add(cons);
            for ( Method m : cd.getMethods() ) cl.methods.add(m);
        }

        // add all classes, methods, and method families
        for (VirgilClass cd : vp.getDeclaredClasses()) {
            cl.classes.add(cd);
            Constructor cons = cd.getConstructor();
            if ( cons != null ) cl.methods.add(cons);
            for ( Method m : cd.getMethods() ) {
                cl.methods.add(m);
                if ( m.family != null ) cl.methodFamilies.add(m.family);
            }
        }

        // use the hierarchy of the declared program
        cl.hierarchy = vp.hierarchy;
    }

    public static void resolveEntryPoints(Program p) {
        if ( p.programDecl != null ) {
            // lookup the main entrypoint
            p.programDecl.mainEntry = ProgramDecl.lookupEntryPoint("main", p);
            // resolve all the declared entrypoints
            for ( ProgramDecl.EntryPoint ep : p.programDecl.entryPoints ) {
                // TODO: typecheck each entrypoint method against linkage decl
                VirgilComponent pcd = p.virgil.getComponentDecl(ep.component.image);
                if (pcd == null)
                    throw VirgilError.UnresolvedIdentifier.gen(ep.component.getSourcePoint(), ep.component.image);
                ep.method = pcd.getLocalMethod(ep.methodName.image);
                if (ep.method == null)
                    throw VirgilError.UnresolvedMember.gen(ep.methodName.getSourcePoint(), ep.methodName.image, pcd.getName());
            }
        }
    }

}
