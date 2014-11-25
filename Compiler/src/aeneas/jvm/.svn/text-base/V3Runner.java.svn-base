/*
 * Copyright (c) 2009 Sun Microsystems, Inc.  All rights reserved.
 *
 * Sun Microsystems, Inc. has intellectual property rights relating to technology embodied in the product
 * that is described in this document. In particular, and without limitation, these intellectual property
 * rights may include one or more of the U.S. patents listed at http://www.sun.com/patents and one or
 * more additional patents or pending patent applications in the U.S. and in other countries.
 *
 * U.S. Government Rights - Commercial software. Government users are subject to the Sun
 * Microsystems, Inc. standard license agreement and applicable provisions of the FAR and its
 * supplements.
 *
 * Use is subject to license terms. Sun, Sun Microsystems, the Sun logo, Java and Solaris are trademarks or
 * registered trademarks of Sun Microsystems, Inc. in the U.S. and other countries. All SPARC trademarks
 * are used under license and are trademarks or registered trademarks of SPARC International, Inc. in the
 * U.S. and other countries.
 *
 * UNIX is a registered trademark in the U.S. and other countries, exclusively licensed through X/Open
 * Company, Ltd.
 */
package aeneas.jvm;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.io.File;

/**
 * The <code>V3Runner</code> class definition.
 *
 * @author Ben L. Titzer
 */
public class V3Runner {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("No Virgil program specified.");
            return;
        }
        Method mainMethod = findVirgilEntrypoint(getJavaClassName(args[0]));
        Class[] argumentTypes = mainMethod.getParameterTypes();
        mainMethod.setAccessible(true);
        if (argumentTypes.length == 1 && argumentTypes[0] == byte[][].class) {
            // invoke the main method with an array of byte arrays
            invokeMainMethod(mainMethod, new Object[] {convertArgsToByteArrays(args)});
        } else if (argumentTypes.length == 1 && argumentTypes[0] == String[].class) {
            // invoke the main method with an array of byte arrays
            invokeMainMethod(mainMethod, new Object[] {trimArgs(args)});
        } else if (argumentTypes.length == 1 && argumentTypes[0] == int.class) {
            // invoke the main method with a single integer
            if (args.length < 2) {
                System.out.println("Usage: " + args[0] + " <int>");
            } else {
                invokeMainMethod(mainMethod, new Object[] {Integer.parseInt(args[1])});
            }
        } else {
            System.out.println("Invalid main entrypoint signature: " + mainMethod.getParameterTypes());
        }
    }

    static String[] trimArgs(String[] args) {
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);
        return newArgs;
    }

    static byte[][] convertArgsToByteArrays(String[] args) {
        byte[][] newArgs = new byte[args.length - 1][];
        for (int i = 0; i < newArgs.length; i++) {
            newArgs[i] = args[i + 1].getBytes();
        }
        return newArgs;
    }

    static void invokeMainMethod(Method mainMethod, Object[] arguments) throws Exception {
        try {
            mainMethod.invoke(null, arguments);
        } catch (InvocationTargetException e) {
            final Throwable cause = e.getTargetException();
            final StackTraceElement[] stackTraceElements = cause.getStackTrace();
            final String message = cause.getMessage();
            final String demangled = demangleString(message);
            System.out.print(cause.getClass().getName());
            System.out.print(": ");
            System.out.print(message);
            if (!message.equals(demangled)) {
                System.out.print(" (" + demangled + ")");
            }
            System.out.println();
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                final String trace = stackTraceElement.toString();
                final String demangledTrace = demangleString(trace);
                System.out.print("\tin ");
                System.out.println(trace);
                if (!trace.equals(demangledTrace)) {
                    System.out.print("\t\t(");
                    System.out.print(demangledTrace);
                    System.out.println(")");
                }
            }
            throw e;
        }
    }

    static String demangleString(String string) {
        if (string == null) return null;
        StringBuffer buffer = new StringBuffer(string.length() + 20);
        final int max = string.length();
        for (int i = 0; i < max; i++) {
            char ch = string.charAt(i);
            if (ch == '$' && i < max - 1) {
                i++;
                char n = string.charAt(i);
                switch (n) {
                    case 'A':
                        buffer.append(" -> ");
                        break;
                    case 'C':
                        buffer.append(',');
                        break;
                    case '_':
                        buffer.append(' ');
                        break;
                    case 'P':
                        buffer.append('(');
                        break;
                    case 'Q':
                        buffer.append(')');
                        break;
                    case 'L':
                        buffer.append('<');
                        break;
                    case 'R':
                        buffer.append('>');
                        break;
                    case 'D':
                        buffer.append('.');
                        break;
                    default:
                        buffer.append(ch);
                        i--;
                }
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    public static Method findVirgilEntrypoint(String className) throws Exception {
        Class testClass;
        try {
            testClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new Exception("could not find class " + className);
        }
        for (Method method : testClass.getDeclaredMethods()) {
            if ((method.getModifiers() & Modifier.STATIC) != 0 && "main".equals(method.getName()))
                return method;
        }
        throw new Exception("could not find main method in class " + className);
    }

    public static String getJavaClassName(String className) {
        int ext = className.indexOf(".v3");
        if (ext > 0 && ext == className.length() - 3) className = className.substring(0, ext);
        int sep = className.lastIndexOf(File.separatorChar);
        if (sep > 0) className = className.substring(sep + 1);
        return "V3K_" + className;
    }
}
