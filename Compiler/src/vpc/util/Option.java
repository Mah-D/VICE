/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Oct 31, 2007
 */
package vpc.util;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * The {@code Option} class represents a command-line or other configuration
 * option with a particular name, type, and description.
 *
 * @author Ben L. Titzer
 */
public class Option<T> {

    /**
     * The {@code Option.Type} class represents a type for an option. This class
     * implements method for parsing and unparsing values from strings.
     */
    public static abstract class Type<U> {
        protected final String typeName;
        protected final Class<U> javaClass;
        protected Type(String tn, Class<U> c) {
            typeName = tn;
            javaClass = c;
        }
        public String getTypeName() {
            return typeName;
        }
        public String unparseValue(U v) {
            return v.toString();
        }
        public abstract U parseValue(String str);
    }

    protected final String name;
    protected final T defvalue;
    protected final Type<T> type;
    protected final String help;
    protected T value;

    /**
     * The constructor for the {@code Option} class creates constructs a new
     * option with the specified parameters.
     * @param name the name of the option as a string
     * @param defvalue the default value of the option
     * @param type the type of the option, which is used for parsing and unparsing values
     * @param help a help description which is usually used to generate a formatted
     * help output
     */
    public Option(String name, T defvalue, Type<T> type, String help) {
        this.defvalue = defvalue;
        this.name = name;
        this.type = type;
        this.help = help;
        this.value = defvalue;
    }

    /**
     * The {@code getName()} method returns the name of this option as a string.
     * @return the name of this option
     */
    public String getName() {
        return name;
    }

    /**
     * The {@code getDefaultValue()} method returns the default value for this option,
     * which is the value that the option retains if no assignment is made.
     * @return the default value of the option
     */
    public T getDefaultValue() {
        return defvalue;
    }

    /**
     * The {@code getValue()} method retrieves the current value of this option.
     * @return the current value of this option
     */
    public T getValue() {
        return value;
    }

    /**
     * The {@code setValue()) method sets the value of this option.
     * @param n the new value to this option
     */
    public void setValue(T n) {
        value = n;
    }

    /**
     * The {@code setValue()} method sets the value of this option, given a string value.
     * The type of this option is used to determine how to parse the string into a value
     * of the appropriate type. Thus this method may potentially throw runtime exceptions
     * if parsing fails.
     * @param val the new value of this option as a string
     */
    public void setValue(String val) {
        setValue(type.parseValue(val));
    }

    /**
     * The {@code getString()} method retrieves the value of this option as a string.
     * The type of this option is used to determine how to unparse the value into a string.
     * @return the value of this option as a string
     */
    public String getString() {
        return type.unparseValue(value);
    }
}
