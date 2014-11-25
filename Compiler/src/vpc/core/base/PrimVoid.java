/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Mar 27, 2006
 */
package vpc.core.base;

import vpc.core.Value;
import vpc.core.types.*;

/**
 * The <code>PrimVoid</code> concept represents the void type which is used to
 * give a type (and a value) to loop statements, switch statements, calls to
 * functions without a return type, etc.
 *
 * @author Ben L. Titzer
 */
public class PrimVoid {

    public static Type TYPE = new Type.Simple("void");
    public static TypeCon TYPECON = new TypeCon.Singleton(TYPE);
    public static Value VALUE = Value.BOTTOM;

    public static boolean isVoid(TypeToken t) {
        return "void".equals(t.toString());
    }
}
