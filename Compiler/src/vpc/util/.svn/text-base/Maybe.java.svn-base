/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created May 27, 2006
 */
package vpc.util;

import cck.util.Util;

/**
 * The <code>Maybe</code> class is a utility object that represents a value
 * that may or not be valid, such as the result of parsing or converting a
 * integer from a string. This is useful in situations where the handling of
 * exceptions needs to be deferred without the clutter of try/catch blocks.
 * <p/>
 * </p>
 * The <code>Maybe</code> class either contains a valid value of the specified
 * generic parameter type, or a <code>Throwable</code> that indicates why the
 * value is not valid. Attempts to access the value through the
 * <code>getValue()</code> method when the value is not valid will cause
 * failure; thus clients should always consult the <code>exists()</code> method
 * before calling <code>getValue()</code>
 *
 * @author Ben L. Titzer
 */
public class Maybe<T> {

    protected final T value;
    protected final Throwable error;

    /**
     * The default constructor for the <code>Maybe</code> class creates an object
     * representing an invalid value.
     */
    public Maybe() {
        value = null;
        error = null;
    }

    /**
     * This constructor will create a new <code>Maybe</code> object containing a
     * valid value.
     *
     * @param v the value to store in this <code>Maybe</code> object.
     */
    public Maybe(T v) {
        value = v;
        error = null;
    }

    /**
     * This constructor will create a new <code>Maybe</code> object that contains
     * an invalid value, with the specified exception.
     *
     * @param e the exception that represents the reason the value is invalid
     */
    public Maybe(Throwable e) {
        value = null;
        error = e;
    }

    /**
     * The <code>exists()</code> method checks whether the value exists.
     *
     * @return true if a valid value exists in this object; false otherwise
     */
    public boolean exists() {
        return value != null;
    }

    /**
     * The <code>getValue()</code> method retrieves the value stored in this object,
     * if it exists. If the value does not exist, this method will fail.
     *
     * @return the value stored in this object, if it is valid
     * @throws Util.InternalError if the value is not valid
     */
    public T getValue() {
        if (exists()) return value;
        else throw Util.failure("Value does not exist: " + error);
    }

    /**
     * The <code>getError()</code> method returns the error corresponding to the
     * reason that this value object is not valid.
     *
     * @return the error for an invalid value
     */
    public Throwable getError() {
        return error;
    }
}
