/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 9, 2007
 */

package vpc.core.base;

import vpc.core.types.Type;
import vpc.util.Nested;

/**
 * The <code>Callable</code> interface models types and operations that can
 * be invoked on some input values and produce an output value. For example,
 * in Virgil, delegates have argument and return types. In CSR
 * (C source representation), function pointers are callable.
 *
 * @author Ben L. Titzer
 */
public interface Callable extends Nested<Type> {

    public Type getResultType();

    public Type getParameterType();
}
