/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Dec 8, 2006
 */

package vpc.sched;

import vpc.util.Ovid;

import java.util.Set;

/**
 * The <code>IRState</code> class represents the internal representation state
 * for a compilation stage. Each IRState specifies an intermediate representation
 * and any attributes that exist or are guaranteed not to exist. This class
 * is used to enforce correct ordering of compilation within an intermediate
 * representation as well as to schedule the overall compilation process.
 *
 * @author Ben L. Titzer
 */
public class IRState {
    public final String name;
    public final Set<String> plusAttrs;
    public final Set<String> minusAttrs;
    private String sname;

    public IRState(String n) {
        name = n;
        plusAttrs = Ovid.newSet();
        minusAttrs = Ovid.newSet();
    }

    public boolean isMetBy(IRState other) {
        if ( !other.name.equals(name) ) return false;
        if ( !other.plusAttrs.containsAll(plusAttrs) ) return false;
        for ( String s : minusAttrs ) {
            if ( other.plusAttrs.contains(s) ) return false;
        }
        return true;
    }
    public String toString() {
        if ( sname == null ) {
            StringBuffer buf = new StringBuffer(name);
            appendAttrs(buf, plusAttrs, '+');
            appendAttrs(buf, minusAttrs, '-');
            sname = buf.toString();
        }
        return sname;
    }

    public void addPlusAttr(String s) {
        plusAttrs.add(s);
        // invalidate the name and hashing cache
        sname = null;
    }

    public void addMinusAttr(String s) {
        minusAttrs.add(s);
        // invalidate the name and hashing cache
        sname = null;
    }

    private void appendAttrs(StringBuffer buf, Set<String> pa, char h) {
        if (!pa.isEmpty() ) {
            buf.append(h);
            for ( String s : pa ) buf.append(s);
        }
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean equals(Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof IRState) ) return false;
        IRState s = (IRState)o;
        if ( !name.equals(s.name) ) return false;
        if ( toString().equals(s.toString()) ) return true;
        if ( !plusAttrs.equals(s.plusAttrs) ) return false;
        return minusAttrs.equals(s.minusAttrs);
    }
}
