/* Generated By:JavaCC: Do not edit this line. Token.java Version 3.0 */
package vpc.vst.parser.v3;

import cck.parser.AbstractToken;

/**
 * Describes the input token stream.
 */

public class Token extends AbstractToken {

    /**
     * An integer that describes the kind of this token.  This numbering system is determined by JavaCCParser,
     * and a table of these numbers is stored in the file ...Constants.java.
     */
    public int kind;

    /**
     * A reference to the next regular (non-special) token from the input stream.  If this is the last token
     * from the input stream, or if the token manager has not read tokens beyond this one, this field is set to
     * null.  This is true only if this token is also a regular token.  Otherwise, see below for a description
     * of the contents of this field.
     */
    public Token next;

    public AbstractToken getNextToken() {
        return next;
    }

    /**
     * Returns a new Token object, by default. However, if you want, you can create and return subclass objects
     * based on the value of ofKind. Simply add the cases to the switch for all those special cases. For
     * example, if you have a subclass of Token called IDToken that you want to create if ofKind is ID, simlpy
     * add something like :
     * <p/>
     * case MyParserConstants.ID : return new IDToken();
     * <p/>
     * to the following switch statement. Then you can cast matchedToken variable to the appropriate type and
     * use it in your lexical actions.
     */
    public static Token newToken(int ofKind) {
        switch (ofKind) {
            default:
                return new Token();
        }
    }

}
