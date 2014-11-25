/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created May 25, 2006
 */
package vpc.util;

import cck.util.Arithmetic;

import java.util.Map;

/**
 * The <code>Flags</code> class implements utilities for sets of flags that are
 * used to represent attributes of classes, declarations, members, and types, such
 * as <code>private</code>, etc.
 *
 * @author Ben L. Titzer
 */
public class Flags {

    protected int numFlags;
    protected final Map<String, Integer> flagSet;
    protected final String[] flagNames;

    public Flags() {
        flagSet = Ovid.newMap();
        flagNames = new String[32];
    }

    /**
     * The <code>addFlag()</code> method adds a flag to this set of flags, assigning
     * the flag a new bit in the word.
     *
     * @param name the name of the new flag
     * @return the new index of the flag
     */
    public int addFlag(String name) {
        Integer ind = flagSet.get(name);
        if (ind != null) return ind;
        int i = numFlags++;
        flagNames[i] = name;
        flagSet.put(name, i);
        return i;
    }

    public int getFlagIndex(String name) {
        Integer ind = flagSet.get(name);
        if (ind == null) return -1;
        return ind;
    }

    /**
     * The <code>getFlagName()</code> method gets the name of a flag as a string by
     * its integer bit index.
     *
     * @param bit the index within the word
     * @return the name of the flag if a flag exists at the bit location; null otherwise
     */
    public String getFlagName(int bit) {
        return flagNames[bit];
    }

    /**
     * The <code>getFlagNames()</code> method gets the names of all the flags set
     * in the specified word as strings.
     *
     * @param word the word containing the flags
     * @return an array containing all the flag names that are set within the word
     */
    public String[] getFlagNames(int word) {
        String[] result = new String[32];
        int cursor = 0;
        for (int cntr = 0; cntr < 32; cntr++) {
            if (getFlag(word, cntr)) result[cursor++] = getFlagName(cntr);
        }
        return ArrayUtil.trunc(result, cursor);
    }

    /**
     * The <code>getFlags()</code> method gets the indices of all the flags set
     * in the specified word as integers.
     *
     * @param word the word containing the flags
     * @return an array containing all the flags that are set within the word
     */
    public static int[] getFlags(int word) {
        int[] result = new int[32];
        int cursor = 0;
        for (int cntr = 0; cntr < 32; cntr++) {
            if (getFlag(word, cntr)) result[cursor++] = cntr;
        }
        int[] r = new int[cursor];
        System.arraycopy(result, 0, r, 0, cursor);
        return r;
    }

    /**
     * The <code>getFlag()</code> method gets a flag from a word.
     *
     * @param flags the flags word
     * @param bit   the bit within the flags word
     * @return true if the flag is set in the word; false otherwise
     */
    public static boolean getFlag(int flags, int bit) {
        return Arithmetic.getBit(flags, bit);
    }

    public static boolean getFlag(long flags, int bit) {
        return Arithmetic.getBit(flags, bit);
    }

    public static int setFlag(int flags, int bit) {
        return Arithmetic.setBit(flags, bit, true);
    }

    public static long setFlag(long flags, int bit) {
        return Arithmetic.setBit(flags, bit, true);
    }

    public static int clearFlag(int flags, int bit) {
        return Arithmetic.setBit(flags, bit, false);
    }

    public static long clearFlag(long flags, int bit) {
        return Arithmetic.setBit(flags, bit, false);
    }

    public static int setFlags(int... ind) {
        int flags = 0;
        for (int anInd : ind) {
            flags |= 1 << anInd;
        }
        return flags;
    }
}
