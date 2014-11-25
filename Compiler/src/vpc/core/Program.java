/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.core;

import cck.parser.AbstractToken;
import cck.parser.SourcePoint;
import cck.util.Util;
import vpc.core.csr.CSRProgram;
import vpc.core.decl.NameSpace;
import vpc.core.decl.TypeDecl;
import vpc.core.types.*;
import vpc.core.virgil.*;
import vpc.util.Cache;
import vpc.util.Ovid;

import java.io.File;
import java.util.List;

/**
 * The <code>Program</code> interface represents the compiler's highest level
 * view of the program. Instances of this interface will encapsulate the types,
 * classes, components, and methods that comprise an application and will serve
 * as a means by which the compiler can manage and track the compilation
 * process.
 *
 * @author Ben L. Titzer
 */
public class Program {

    public final String name;
    public final VirgilProgram virgil;
    public CSRProgram csr;
    protected final List<File> files;
    public final Heap heap;
    public vpc.hil.Device targetDevice;
    public ProgramDecl programDecl;
    public final NameSpace namespace;
    public final TypeEnv typeEnv;
    public final TypeSystem typeSystem;
    public final Cache<Type> typeCache;
    public Closure closure;
    public final Language language;

    public Program(String n, Language lang) {
        name = n;
        typeCache = new Cache<Type>();
        namespace = new NameSpace();
        typeEnv = new TypeEnv(null);
        language = lang;
        virgil = new VirgilProgram(this);
        lang.initializeTypeEnv(this, typeCache, typeEnv);
        typeSystem = lang.getTypeSystem(this);
        files = Ovid.newLinkedList();
        heap = new Heap(this);
        closure = new Closure(this);
    }

    public void addFile(File f) {
        files.add(f);
    }

    public Iterable<File> getFiles() {
        return files;
    }

    public TypeDecl getTypeDecl(String s) {
        TypeDecl d;
        if ((d = virgil.getClassDecl(s)) != null) return d;
        return virgil.getComponentDecl(s);
    }

    public void setDevice(vpc.hil.Device d) {
        if (d == null) return;
        targetDevice = d;
        namespace.addDecl("device", d);
    }

    public File getFirstFile() {
        return files.get(0);
    }

    public SourcePoint getDefaultSourcePoint() {
        return new SourcePoint(getFirstFile().getName(), 1, 1, 1);
    }

    public AbstractToken getDefaultToken(String str) {
        return AbstractToken.newToken(str, getDefaultSourcePoint());
    }

    public Heap.Layout getLayout(Heap.Record record) {
        Type type = record.getType();
        if (type instanceof VirgilClass.IType) {
            return closure.getLayout(((VirgilClass.IType)type).getDecl());
        }
        if (type instanceof VirgilComponent.IType) {
            return closure.getLayout(((VirgilComponent.IType)type).getDecl());
        }
        if (type instanceof VirgilArray.IType) {
            return VirgilArray.getArrayLayout(heap, (VirgilArray.IType)type, record.getSize());
        }
        throw Util.failure("cannot get layout for record " + record);
    }

    public interface DynamicEnvironment extends TypeFormula.TypeEnv {
        public Program getProgram();
        public Heap getHeap();
    }
}
