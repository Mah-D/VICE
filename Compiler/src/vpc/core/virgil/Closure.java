/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Dec 20, 2006
 */
package vpc.core.virgil;

import vpc.core.Heap;
import vpc.core.Program;
import vpc.core.decl.CompoundDecl;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.util.Hierarchy;
import vpc.util.Ovid;

import java.util.*;

/**
 * The <code>Closure</code> class definition. The <code>Closure</code> class
 * represents the complete set of classes, methods, layouts, metaobjects,
 * heap, etc for the program. The closure is used to compute information
 * for global analyses and for the final code generation step.
 *
 * @author Ben L. Titzer
 */
public class Closure {

    public final Program program;

    public int maxClassID;

    public Set<Method> methods = Ovid.newSet();

    public Set<Method.Family> methodFamilies = Ovid.newSet();

    public Hierarchy<VirgilClass> hierarchy = new Hierarchy<VirgilClass>();

    public Map<VirgilComponent, Heap.Layout> componentLayouts;

    public Map<VirgilClass, Heap.Layout> classLayouts;

    public List<VirgilClass> classes;

    public List<VirgilComponent> components;

    public Closure(Program p) {
        program = p;
        resetLayouts();
    }

    public Closure copy() {
        Closure nc = new Closure(program);
        nc.maxClassID = maxClassID;
        nc.methods = methods;
        nc.methodFamilies = methodFamilies;
        nc.hierarchy = hierarchy;
        nc.componentLayouts = componentLayouts;
        nc.classLayouts = classLayouts;
        nc.classes = classes;
        nc.components = components;
        return nc;
    }

    public Heap.Layout getLayout(CompoundDecl d) {
        if ( d instanceof VirgilComponent ) return getLayout((VirgilComponent)d);
        if ( d instanceof VirgilClass ) return getLayout((VirgilClass)d);
        return null;
    }

    public Heap.Layout getLayout(VirgilComponent c) {
        return componentLayouts.get(c);
    }

    public Heap.Layout getLayout(VirgilClass c) {
        return classLayouts.get(c);
    }

    public void resetLayouts() {
        componentLayouts = Ovid.newMap();
        classLayouts = Ovid.newMap();
    }

    public Iterable<Heap.Record> getRecords() {
        return program.heap.getRecords();
    }

    public Iterable<VirgilClass> getClasses() {
        return classes;
    }

    public Iterable<VirgilComponent> getComponents() {
        return components;
    }

    public VirgilClass getRootClass(VirgilClass c) {
        return hierarchy.getRoot(c);
    }

    public VirgilClass getRootClass(VirgilClass.IType c) {
        return hierarchy.getRoot(c.getDecl());
    }

    public VirgilClass getRootClass(Type c) {
        return getRootClass((VirgilClass.IType)c);
    }
}
