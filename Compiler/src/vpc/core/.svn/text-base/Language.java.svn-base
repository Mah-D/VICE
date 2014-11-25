/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Nov 28, 2007
 */
package vpc.core;

import vpc.core.types.*;
import vpc.util.Cache;

/**
 * The <code>Language</code> definition.
 *
 * @author Ben L. Titzer
 */
public interface Language {

    public TypeSystem getTypeSystem(Program program);

    public void initializeTypeEnv(Program program, Cache<Type> typeCache, TypeEnv env);

    public String renderType(Type t);

    public String renderTypeCon(TypeCon t, TypeRef... args);
}
