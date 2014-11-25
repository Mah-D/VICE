/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Nov 28, 2007
 */
package vpc.core.virgil;

import vpc.core.Language;
import vpc.core.Program;
import vpc.core.base.*;
import vpc.core.types.*;
import vpc.util.Cache;

/**
 * The <code>V2Language</code> definition.
 *
 * @author Ben L. Titzer
 */
public class V2Language implements Language {

    public TypeSystem getTypeSystem(Program program) {
        V2TypeSystem typeSystem = new V2TypeSystem();
        typeSystem.registerBinOp(PrimBool.TYPE, new Capability.BinOp("&&", new PrimBool.AND()));
        typeSystem.registerBinOp(PrimBool.TYPE, new Capability.BinOp("||", new PrimBool.OR()));
        return typeSystem;
    }

    public void initializeTypeEnv(Program program, Cache<Type> typeCache, TypeEnv env) {
        env.bindTypeCon("int", PrimInt32.TYPECON);
        env.bindTypeCon("char", PrimChar.TYPECON);
        env.bindTypeCon("boolean", PrimBool.TYPECON);
        env.bindTypeCon("bool", PrimBool.TYPECON);
        env.bindTypeCon("void", PrimVoid.TYPECON);
        env.bindTypeCon("null", Reference.NULL_TYPECON);
        // bind the raw types.
        for ( int cntr = 1; cntr <= 64; cntr++ ) {
            env.bindTypeCon(Integer.toString(cntr), new TypeCon.Singleton(PrimRaw.getType(cntr)));
        }
    }

    public String renderType(Type t) {
        return t.toString();
    }
    
    public String renderTypeCon(TypeCon tc, TypeRef... args) {
        return tc.render(args);
    }
}
