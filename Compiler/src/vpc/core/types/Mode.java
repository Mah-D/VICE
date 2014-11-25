/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc.core.types;

import vpc.util.Flags;

/**
 * The <code>Mode</code> class represents the acces mode of an identifier.
 * For example, private, final, or volatile, and combinations of those.
 * It contains several singletons that represent the possible combinations
 * of modifiers in the Virgil programming language.
 *
 * @author Ben L. Titzer
 */
public class Mode {

    public static final Flags modeFlags = new Flags();

    public static final int MODE_PRIVATE = modeFlags.addFlag("private");

    protected final int mode;

    public Mode(int m1) {
        mode = m1;
    }

    public boolean isPrivate() {
        return Flags.getFlag(mode, MODE_PRIVATE);
    }

}
