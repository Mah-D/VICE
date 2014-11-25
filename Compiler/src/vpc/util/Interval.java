/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Apr 10, 2007
 */

package vpc.util;

/**
 * The <code>Interval</code> class is used to create objects that represent
 * integer intervals.
 *
 * @author Ben L. Titzer
 */
public class Interval {
    public final int min;
    public final int max;

    public Interval(int mn, int mx) {
        min = mn;
        max = mx;
    }

    public boolean equals(Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Interval) ) return false;
        Interval i = (Interval)o;
        return min == i.min && max == i.max;
    }

    public boolean intersects(Interval i) {
        return max >= i.min && min <= i.max;
    }

    public boolean contains(Interval i) {
        return min <= i.min && max >= i.max;
    }

    public boolean isSingleton() {
        return min == max;
    }

    public String toString() {
        return "[" + min + ", " + max + "]";
    }

    public int hashCode() {
        return min + 13 * max;
    }

}
