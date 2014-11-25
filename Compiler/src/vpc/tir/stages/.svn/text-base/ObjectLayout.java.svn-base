/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Mar 1, 2006
 */

package vpc.tir.stages;

import vpc.core.Heap;
import vpc.core.Program;
import vpc.core.decl.CompoundDecl;
import vpc.core.decl.Field;
import vpc.core.virgil.*;
import vpc.sched.Stage;

import java.io.IOException;
import java.util.Map;

/**
 * The <code>ObjectLayout</code> class computes the layout for each object and component
 * in the program.
 *
 * @author Ben L. Titzer
 */
public class ObjectLayout extends Stage {

    public void visitProgram(Program p) throws IOException {
        Closure cl = p.closure;
        // compute layout for each component
        for (VirgilComponent cd : cl.getComponents()) computeLayout(cl, cd);
        // compute layout for each class
        for (VirgilClass cd : cl.getClasses()) computeLayout(cl, cd);
    }

    public static Heap.Layout computeLayout(Closure cl, VirgilComponent cd) {
        Heap.Layout layout = cl.getLayout(cd);
        if (layout == null) {
            layout = newLayout(cl, cd, cl.componentLayouts);
            addFields(cd, layout);
        }
        return layout;
    }

    public static Heap.Layout computeLayout(Closure cl, VirgilClass cd) {
        Heap.Layout layout = cl.getLayout(cd);
        if (layout == null) {
            layout = newLayout(cl, cd, cl.classLayouts);
            VirgilClass pcd = cl.hierarchy.getParent(cd);
            if (pcd != null) {
                // if there is a parent class, copy its layout into this one
                Heap.Layout parent = computeLayout(cl, pcd);
                parent.copy(layout);
            }
            addFields(cd, layout);
        }
        return layout;
    }

    public static void addFields(CompoundDecl cd, Heap.Layout nl) {
        for (Field f : cd.getFields()) {
            f.fieldIndex = nl.addCell(f);
        }
    }

    public static <C extends CompoundDecl> Heap.Layout newLayout(Closure cl, C cd, Map<C, Heap.Layout> map) {
        Heap.Layout layout = cl.program.heap.newLayout(cd.getName(), cd.getCanonicalType());
        map.put(cd, layout);
        return layout;
    }

}
