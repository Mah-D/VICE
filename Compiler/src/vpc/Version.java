/**
 * Copyright (c) 2005, Ben L. Titzer
 * See the file "license.txt" for details.
 */

package vpc;

/**
 * The <code>Version</code> class represents a version number, including the major version and the commit
 * number as well as the date and time of the last commit. This class also has a main method that
 * can be used to print out the version of the compiler as a suitable file suffix, which is used
 * in some shell scripts to generate jar files and zip files.
 *
 * @author Ben L. Titzer
 */
public class Version {

    /**
     * The <code>release</code> field stores the a boolean that is set to true for release versions,
     * but maintained as false for internal versions.
     */
    public static final boolean release = false;

    /**
     * The <code>major</code> field stores the string that represents the major version number (the release
     * number).
     */
    public static final String major = "B-03";

    /**
     * The <code>majlow</code> field stores the string that represents the major version number (the release
     * number), but in lower case and without the hyphen. This is used to generate suffixes for filenames
     * such as "vpc-b03.zip".
     */
    public static final String majlow = "b03";

    /**
     * The <code>commit</code> field stores the commit number (i.e. the number of code revisions committed to
     * CVS since the last release).
     */
    public static final int commit = 53;

    /**
     * The <code>getVersion()</code> method returns a reference to a <code>Version</code> object
     * that represents the version of the code base.
     *
     * @return a <code>Version</code> object representing the current version
     */
    public static Version getVersion() {
        return new Version();
    }

    /**
     * The <code>toString()</code> method converts this version to a string.
     *
     * @return a string representation of this version
     */
    public String toString() {
        if ( release ) return "Release "+ major;
        else return "Version " + major + '.' + commitNumber();
    }

    /**
     * The <code>toFileSuffix()</code> method converts this version to a
     * that is suitable for a file suffix.
     *
     * @return a string representation of this version
     */
    public String toFileSuffix() {
        if ( release ) return majlow;
        else return majlow + '-' + commitNumber();
    }

    private static String commitNumber() {
        if ( commit < 10 ) return "00"+commit;
        if ( commit < 100 ) return "0"+commit;
        return Integer.toString(commit);
    }

    /**
     * The <code>main()</code> method for the versiobn simply outputs the version number as a
     * file suffix. This is used in some scripts that generate jar files, etc.
     * @param args the arguments to the main method; ignored
     */
    public static void main(String[] args) {
        System.out.println(getVersion().toFileSuffix());
    }
}
