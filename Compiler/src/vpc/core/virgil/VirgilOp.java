/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 8, 2007
 */

package vpc.core.virgil;

/**
 * This class collects together the constants for all of the operators related
 * to the Virgil programming language.
 *
 * @author Ben L. Titzer
 */
public class VirgilOp {

    public static final int RAW_CONCAT          = 50;
    public static final int RAW_GETBIT          = 51;
    public static final int RAW_SETBIT          = 52;

    public static final int TYPE_CAST           = 53;
    public static final int TYPE_QUERY          = 54;

    public static final int NULL_CHECK          = 55;

    public static final int CLASS_GETFIELD      = 56;
    public static final int CLASS_SETFIELD      = 57;
    public static final int CLASS_GETMETHOD     = 58;

    public static final int COMPONENT_GETFIELD  = 59;
    public static final int COMPONENT_SETFIELD  = 60;
    public static final int COMPONENT_GETMETHOD = 61;

    public static final int ARRAY_GETELEMENT    = 62;
    public static final int ARRAY_SETELEMENT    = 63;
    public static final int ARRAY_GETLENGTH     = 64;
    public static final int ARRAY_INIT          = 65;
    public static final int DEVICE_GETREGISTER  = 66;
    public static final int DEVICE_SETREGISTER  = 67;
    public static final int DEVICE_INSTRUSE     = 68;
    public static final int DEVICE_INSTRINVOKE  = 69;
    public static final int NEW_OBJECT          = 70;
    public static final int NEW_ARRAY           = 71;
    public static final int TUPLE_GETELEMENT    = 72;

}
