/**
 * Copyright (c) 2007, Ben L. Titzer
 * See the file "license.txt" for details.
 *
 * Created Oct 31, 2007
 */
package vpc.util;

import java.util.List;
import java.util.LinkedList;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.io.File;

/**
 * The <code>BasicOptions</code> class extends the general options class with several
 * useful option types, including all the basic Java types, strings, files, and lists.
 * It contains a number of utility methods that make creating options really simple.
 *
 * @author Ben L. Titzer
 */
public class BasicOptionSet extends OptionSet {
    public static Option.Type<String> STRING_TYPE = new Option.Type<String>("string", String.class) {
        public String parseValue(String v) {
            return v;
        }
    };
    public static Option.Type<Double> DOUBLE_TYPE = new Option.Type<Double>("double", Double.class) {
        public Double parseValue(String v) {
            if (v.length() == 0) return 0.0d;
            return Double.valueOf(v);
        }
    };
    public static Option.Type<Float> FLOAT_TYPE = new Option.Type<Float>("float", Float.class) {
        public Float parseValue(String v) {
            if (v.length() == 0) return 0.0f;
            return Float.valueOf(v);
        }
    };
    public static Option.Type<Long> LONG_TYPE = new Option.Type<Long>("long", Long.class) {
        public Long parseValue(String v) {
            if (v.length() == 0) return 0L;
            return Long.valueOf(v);
        }
    };
    public static Option.Type<Integer> INT_TYPE = new Option.Type<Integer>("int", int.class) {
        public Integer parseValue(String v) {
            if (v.length() == 0) return 0;
            return Integer.valueOf(v);
        }
    };
    public static Option.Type<Boolean> BOOLEAN_TYPE = new Option.Type<Boolean>("boolean", Boolean.class) {
        public Boolean parseValue(String v) {
            if (v.length() == 0) return true;
            return Boolean.valueOf(v);
        }
    };
    public static Option.Type<File> FILE_TYPE = new Option.Type<File>("file", File.class) {
        public File parseValue(String v) {
            if (v == null || v.length() == 0) return null;
            return new File(v);
        }
    };
    public static Option.Type<List> LIST_TYPE = new ListType(',');

    public static class ListType extends Option.Type<List> {
        protected final char separator;
        public ListType(char sep) {
            super("list", List.class);
            separator = sep;
        }
        public String unparseValue(List val) {
            StringBuffer buf = new StringBuffer();
            boolean prev = false;
            for (Object o: val) {
                if (prev) buf.append(separator);
                prev = true;
                buf.append(o.toString());
            }
            return buf.toString();
        }
        public List parseValue(String val) {
            LinkedList list = new LinkedList();
            if ("".equals(val)) return list;

            CharacterIterator i = new StringCharacterIterator(val);
            StringBuffer buf = new StringBuffer(32);
            while (i.current() != CharacterIterator.DONE) {
                if (i.current() == separator) {
                    list.add(buf.toString().trim());
                    buf = new StringBuffer(32);
                } else {
                    buf.append(i.current());
                }
                i.next();
            }
            list.add(buf.toString().trim());
            return list;
        }
    }

    public Option<String> newOption(String name, String val, String help) {
        return addOption(new Option<String>(name, val, STRING_TYPE, help));
    }

    public Option<Integer> newOption(String name, int val, String help) {
        return addOption(new Option<Integer>(name, val, INT_TYPE, help));
    }

    public Option<Long> newOption(String name, long val, String help) {
        return addOption(new Option<Long>(name, val, LONG_TYPE, help));
    }

    public Option<Float> newOption(String name, float val, String help) {
        return addOption(new Option<Float>(name, val, FLOAT_TYPE, help));
    }

    public Option<Double> newOption(String name, double val, String help) {
        return addOption(new Option<Double>(name, val, DOUBLE_TYPE, help));
    }

    public Option<List> newListOption(String name, String val, String help) {
        return addOption(new Option<List>(name, LIST_TYPE.parseValue(val), LIST_TYPE, help));
    }

    public Option<List> newListOption(String name, String val, char sep, String help) {
        Option.Type<List> type = new ListType(sep);
        return addOption(new Option<List>(name, type.parseValue(val), type, help));
    }

    public Option<File> newFileOption(String name, String val, String help) {
        return addOption(new Option<File>(name, FILE_TYPE.parseValue(val), FILE_TYPE, help));
    }

    public Option<Boolean> newOption(String name, boolean val, String help) {
        return addOption(new Option<Boolean>(name, val, BOOLEAN_TYPE, help));
    }

    public <T> Option<T> newOption(String name, String val, Option.Type<T> type, String help) {
        return addOption(new Option<T>(name, type.parseValue(val), type, help));
    }
}
