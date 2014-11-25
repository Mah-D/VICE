/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Apr 12, 2007
 */

package vpc.core.csr;

import vpc.core.Value;

/**
 * @author Ben L. Titzer
*/
public class CSRBitField extends CSRType.Simple {
    public final int length;
    public static final CSRBitField[] TYPE_CACHE = new CSRBitField[65];

    static {
        for ( int cntr = 1; cntr < 65; cntr++ ) {
            TYPE_CACHE[cntr] = new CSRBitField(cntr);
        }
    }

    private CSRBitField(int len) {
        super(getBitFieldName(len));
        length = len;
    }

    public String fieldDecl(String varname) {
        return getBitFieldName(length)+" " + varname+" : "+length;
    }

    public String localDecl(String varname) {
        return getBitFieldName(length)+" "+varname;
    }

    public String renderValue(boolean init, Value value) {
        return CSRType.renderIntegral(value);
    }

    public static CSRBitField getBitType(int width) {
        return TYPE_CACHE[width];
    }

    public static String getBitFieldName(int w) {
        if ( w <= 8 ) return CSRProgram.UINT8.name;
        if ( w <= 16 ) return CSRProgram.UINT16.name;
        if ( w <= 32 ) return CSRProgram.UINT32.name;
        return CSRProgram.UINT64.name;
    }
}
