/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Oct 31, 2007
 */
package vpc.util;

import cck.util.Util;

import java.util.*;
import java.io.*;

/**
 * The {@code OptionSet} class parses and collects options from the command line and
 * configuration files.
 *
 * @author Ben L. Titzer
 */
public class OptionSet {

    /**
     * The {@code Syntax} enum allows different options to be parsed differently,
     * depending on their usage.
     */
    public enum Syntax {
        EQUALS_OR_BLANK,
        REQUIRES_EQUALS,
        REQUIRES_BLANK,
        CONSUMES_NEXT
    }

    protected final HashMap<String, Option> optMap;
    protected final HashMap<String, Syntax> optSyntax;
    protected final HashMap<String, String> optValues;

    protected String[] arguments;

    public OptionSet() {
        optValues = Ovid.newHashMap();
        optMap = Ovid.newHashMap();
        optSyntax = Ovid.newHashMap();
    }

    /**
     * The {@code parseArguments()} method parses arguments from a list of
     * command line arguments, extracting valid options and returning the "leftover"
     * arguments to the caller.
     * @param args the arguments
     * @param fatal a flag to indicate whether the parser should generate fatal errors
     * for unrecognized options
     * @return the new command line arguments
     */
    public String[] parseArguments(String[] args, boolean fatal) {
        // parse the options
        int cntr = 0;
        for (; cntr < args.length; cntr++) {
            String arg = args[cntr];
            if (arg.charAt(0) == '-') {
                // is the beginning of a valid option.
                int index = arg.indexOf('=');
                String optname = getOptionName(arg, index);
                String value = getOptionValue(arg, index);
                Syntax syntax = optSyntax.get(optname);
                // check the syntax of this option
                checkSyntax(optname, syntax, value, cntr, args);
                if (syntax == Syntax.CONSUMES_NEXT) {
                    value = args[++cntr];
                }
                setValue(optname, value, fatal);
            } else {
                // is not a valid option, therefore the start of arguments
                break;
            }
        }

        int left = args.length - cntr;
        arguments = new String[left];
        System.arraycopy(args, cntr, arguments, 0, left);
        return arguments;
    }

    /**
     * The {@code getArguments()} method gets the leftover command line options
     * from the last call to {@code parseArguments}</code>
     * @return the leftover command line options
     */
    public String[] getArguments() {
        return arguments;
    }

    /**
     * The {@code loadSystemProperties()} method loads the value of the valid
     * options from the systems properties with the specified prefix.
     * @param prefix the prefix of each system property, used to disambiguate
     * these options from other system properties.
     */
    public void loadSystemProperties(String prefix) {
        for (Option opt : optMap.values()) {
            String val = System.getProperty(prefix + opt.getName());
            if (val != null) {
                opt.setValue(val);
            }
        }
    }

    /**
     * The {@code storeSystemProperties()} method stores these option values
     * into the system properties.
     * @param prefix the prefix to append to all option names
     */
    public void storeSystemProperties(String prefix) {
        // TODO: store all options, or only explicitly set ones?
        for (Map.Entry<String, String> e : optValues.entrySet()) {
            System.setProperty(prefix + e.getKey(), e.getValue());
        }
    }

    /**
     * The {@code loadProperties()} method loads the specified properties into
     * this set of options.
     * @param p the properties set to load into this set of options
     */
    public void loadProperties(Properties p) {
        for (Object o : p.keySet()) {
            String name = (String) o;
            String val = p.getProperty(name);
            setValue(name, val);
        }
    }

    /**
     * The {@code loadFile()} method parses properties from a file and loads them
     * into this set of options.
     * @param fname the filename from while to load the properties
     * @throws IOException if there is a problem opening or reading the file
     */
    public void loadFile(String fname) throws IOException {
        File f = new File(fname);
        Properties defs = new Properties();
        defs.load(new FileInputStream(f));
        loadProperties(defs);
    }

    /**
     * The {@code loadOptions()} method loads a set of options from another OptionSet
     * object.
     * @param options the option set from which to load the option values
     */
    public void loadOptions(OptionSet options) {
        for (Map.Entry<String, String> e : options.optValues.entrySet()) {
            setValue(e.getKey(), e.getValue());
        }
    }

    protected void checkSyntax(String optname, Syntax syntax, String value, int cntr, String[] args) {
        if (syntax == Syntax.REQUIRES_BLANK && value != null)
            Util.userError("Syntax error parsing: -"+ optname);
        if (syntax == Syntax.REQUIRES_EQUALS && value == null)
            Util.userError("Syntax error parsing -"+ optname);
        if (syntax == Syntax.CONSUMES_NEXT && value != null)
            Util.userError("Syntax error parsing: -"+ optname);
    }

    protected String getOptionName(String arg, int equalIndex) {
        if (equalIndex < 0) { // naked option
            return arg.substring(1, arg.length());
        } else {
            return arg.substring(1, equalIndex);
        }
    }
    protected String getOptionValue(String arg, int equalIndex) {
        if (equalIndex < 0) { // naked option
            return null;
        } else {
            return arg.substring(equalIndex + 1);
        }
    }

    /**
     * The {@code addOption()} method adds an option to this option set.
     * @param option the new option to add to this set
     * @return the option passed as the argument, after it has been added to this option set
     */
    public <T> Option<T> addOption(Option<T> option) {
        String name = option.getName();
        optMap.put(name, option);
        return option;
    }
    @SuppressWarnings("unchecked")
	public <T> Option<T> getOption(String name) {
    	return optMap.get(name);
    }

    /**
     * The {@code addOption()} method adds an option to this option set.
     * @param option the new option to add to this set
     * @param syntax the syntax of the option, which specifies how to parse the option
     * from command line parameters
     * @return the option passed as the argument, after it has been added to this option set
     */
    public <T> Option<T> addOption(Option<T> option, Syntax syntax) {
        String name = option.getName();
        optMap.put(name, option);
        optSyntax.put(name, syntax);
        return option;
    }

    /**
     * The {@code setSyntax()} method sets the syntax of a particular option.
     * @param option the option for which to change the syntax
     * @param syntax the new syntax for the instruction
     */
    public void setSyntax(Option option, Syntax syntax) {
        optSyntax.put(option.getName(), syntax);
    }

    /**
     * The {@code setValue()} method sets the value of the specified option in
     * this option set. If there is no option by the specified name, the name/value
     * pair will simply be remembered.
     * @param name the name of the option
     * @param val the new value of the option as a string
     */
    public void setValue(String name, String val) {
        setValue(name, val, false);
    }

    protected void setValue(String name, String val, boolean fatal) {
        if (val == null) val = "";
        Option opt = optMap.get(name);
        if (opt != null) {
            opt.setValue(val);
        } else {
            if (fatal) {
                Util.userError("Unrecognized option: -" + name);
            }
        }
        optValues.put(name, val);
    }

}
