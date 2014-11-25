/**
 * Copyright (c) 2004-2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.vst.parser.v2;

import cck.parser.SimpleCharStream;

/**
 * The <code>FileMarkingTokenManager</code> is a subclass of the TokenManager for the Atmel parser that marks
 * each token that is seen with the name of the file that it came from. This is useful in unifying multiple
 * grammars that each have their own definition of Token, since an AbstractToken can be used by any part of
 * the compiler.
 *
 * @author Ben L. Titzer
 */
public class FileMarkingTokenManager extends VirgilParserTokenManager {

    protected String filename;

    public FileMarkingTokenManager(SimpleCharStream s, String fname) {
        super(s);
        filename = fname;
    }

    protected Token jjFillToken() {
        Token t = super.jjFillToken();
        t.file = filename;
        return t;
    }

}
