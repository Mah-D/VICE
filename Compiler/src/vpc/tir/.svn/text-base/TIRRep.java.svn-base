/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.tir;

import vpc.core.decl.Method;
import vpc.core.types.Type;

/**
 * The <code>TIRRep</code> class represents the tree-based
 * code representation that is used by the early stages of the
 * compiler (after program verification).
 *
 * @author Ben L. Titzer
 */
public class TIRRep extends Method.BaseMethodRep<TIRExpr, Type> {

    public static final String REP_NAME = "tir";

    int numLabels;

    public String newLabel(String l) {
        return l + numLabels++;
    }

}
