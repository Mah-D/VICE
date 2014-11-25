/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Dec 8, 2006
 */

package vpc.sched;

import cck.text.StringUtil;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Set;

/**
 * The <code>StageSignature</code> class represents the signature of a stage of compilation.
 * The signature describes the expected format of the code (its IR representation, such as "tir"),
 * any attributes that are required or disallowed, and any attributes of the code that are
 * added or removed by the compilation stage.
 *
 * @author Ben L. Titzer
 */
public class StageSignature {
    public final IRState input;
    public final IRState output;

    public StageSignature(IRState b, IRState a) {
        input = b;
        output = a;
    }

    public boolean canAccept(IRState other) {
        return input.isMetBy(other);
    }

    public IRState process(IRState input) {
        // create a new output IR state
        IRState n = new IRState(output.name);
        // add all the new attributes
        n.plusAttrs.addAll(output.plusAttrs);

        if ( n.name.equals(input.name) ) {
            // if it is the same IR, propagate all attributes
            n.plusAttrs.addAll(input.plusAttrs);
        } else {
            // otherwise, only propagate the sticky attributes
            for ( String a : input.plusAttrs ) {
                if ( a.charAt(0) == '$' ) n.plusAttrs.add(a);
            }
        }
        // remove all the minus attributes
        n.plusAttrs.removeAll(output.minusAttrs);
        return n;
    }

    public static StageSignature parseSignature(String sig) {
        CharacterIterator i = new StringCharacterIterator(sig);
        return new StageSignature(parseIRState(i), parseIRState(i));
    }

    public static IRState parseIRState(String i) {
        return parseIRState(new StringCharacterIterator(i));
    }

    public static IRState parseIRState(CharacterIterator i) {
        IRState b = new IRState(StringUtil.readIdentifier(i));
        parseAttributes(i, b);
        return b;
    }

    public static void parseAttributes(CharacterIterator i, IRState state) {
        parsePlusAttrs(i, state);
        parseMinusAttrs(i, state);
        parsePlusAttrs(i, state);
    }

    private static void parseMinusAttrs(CharacterIterator i, IRState state) {
        if ( StringUtil.peekAndEat(i, '-') ) {
            if ( !StringUtil.peekAndEat(i, '>') ) {
                parseAttrs(i, state.minusAttrs);
            }
        }
    }

    private static void parsePlusAttrs(CharacterIterator i, IRState state) {
        if ( StringUtil.peekAndEat(i, '+') ) {
            parseAttrs(i, state.plusAttrs);
        }
    }

    public static void parseAttrs(CharacterIterator i, Set<String> atts) {
        while ( true ) {
            char c = i.current();
            if ( c == '$') {
                // sticky attributes start with "$"
                c = i.next();
                if ( Character.isLetter(c) ) {
                    atts.add("$"+c);
                }
            } else if ( Character.isLetter(c) ) {
                // just a good ole regular attribute
                atts.add(String.valueOf(c));
            } else {
                // unknown character, break
                break;
            }
            i.next();
        }
    }

    public String toString() {
        return input+"->"+output;
    }

    public StageSignature copy() {
        return new StageSignature(copyIRState(input), copyIRState(output));
    }

    private IRState copyIRState(IRState i) {
        IRState na = new IRState(i.name);
        na.plusAttrs.addAll(i.plusAttrs);
        na.minusAttrs.addAll(i.minusAttrs);
        return na;
    }
}
