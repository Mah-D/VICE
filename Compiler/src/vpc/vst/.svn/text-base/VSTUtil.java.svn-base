/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Jan 22, 2006
 */
package vpc.vst;

import cck.util.Util;
import vpc.core.decl.*;
import vpc.core.types.Type;
import vpc.core.virgil.VirgilClass;
import vpc.core.virgil.VirgilComponent;
import vpc.util.Cache;
import vpc.vst.tree.*;

/**
 * The <code>VSTUtil</code> class encapsulates a number of utility methods that are used
 * through the implementation of various VST analysis and transformation passes.
 *
 * @author Ben L. Titzer
 */
public class VSTUtil {
    public static final String REPRESENTATION_KEY = "vst";

    public static VSTClassDecl vstRepOf(VirgilClass pcd) {
        if (pcd == null) return null;
        return (VSTClassDecl) pcd.getSourceRep();
    }

    public static VSTComponentDecl vstRepOf(VirgilComponent pcd) {
        if (pcd == null) return null;
        return (VSTComponentDecl) pcd.getComponentRep();
    }

    public static VSTMethodDecl vstRepOf(Method m) {
        if (m == null) return null;
        return (VSTMethodDecl) m.getMethodRep(REPRESENTATION_KEY);
    }

    public static VSTFieldDecl vstRepOf(Field f) {
        if (f == null) return null;
        return (VSTFieldDecl) f.getFieldRep();
    }

    public static VSTMemberDecl vstRepOf(Member m) {
        if (m.isField()) return vstRepOf((Field) m);
        else return vstRepOf((Method) m);
    }

    public static VSTConstructorDecl vstRepOf(Constructor c) {
        if (c == null) return null;
        return (VSTConstructorDecl) c.getMethodRep(REPRESENTATION_KEY);
    }

    public static Type typeOf(VSTTypeRef r) {
        return r.getType();
    }

    public static Type typeOf(VSTExpr e) {
        return e.getType();
    }

    public static boolean isPrivate(Member m) {
        VSTMemberDecl md = vstRepOf(m);
        return md.getMode().isPrivate();
    }

    public static Member.Ref getVisibleMember(Cache<Type> cache, VirgilClass.IType ct, String name, CompoundDecl where) {
        if ( ct == null ) return null;
        Member.Ref rf = ct.resolveMember(cache, name);
        if ( rf != null ) {
            CompoundDecl cd = rf.memberDecl.getCompoundDecl();
            if ( rf.memberDecl.isPrivate() && where != cd ) return null;
        }
        return rf;
    }

}
