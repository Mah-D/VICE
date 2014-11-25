/*
 * Copyright (c) 2008, Ben L. Titzer
 * See license.txt for details.
 *
 * Created: May 19, 2008
 */
package aeneas.jvm;

import java.io.*;

/**
 * The <code>V3System</code> class implements system calls for Virgil III programs compiled
 * to Java bytecode.
 *
 * @author Ben L. Titzer
 */
public class V3System {
    private static final int PRINT_SIZE = 128;
    private static final int MAX_FILES = 128;

    public static final File[] files;
    public static final FileInputStream[] fileInput;
    public static final FileOutputStream[] fileOutput;

    static {
        files = new File[MAX_FILES];
        fileInput = new FileInputStream[MAX_FILES];
        fileOutput = new FileOutputStream[MAX_FILES];
    }

    public static void putc(byte ch) {
        System.out.print((char) ch);
    }

    public static void puts(byte[] str) {
        for (int i = 0; i < str.length; i += PRINT_SIZE) {
            int len = PRINT_SIZE;
            if (i + len >= str.length) len = str.length - i;
            System.out.write(str, i, len);
        }
    }

    public static void puti(int i) {
        System.out.print(i);
    }

    public static byte fileRead(int fd) throws IOException {
        if (files[fd] != null && fileInput[fd] != null) {
            return (byte)fileInput[fd].read();
        }
        return 0;
    }

    public static void fileWrite(int fd, byte ch) throws IOException {
        if (files[fd] != null && fileOutput[fd] != null) {
            fileOutput[fd].write(ch);
        }
    }

    public static int fileOpen(byte[] name, boolean input) {
        for (int i = 0; i < files.length; i++) {
            if (files[i] == null) {
                File file = new File(new String(name));
                files[i] = file;
                try {
                    if (input) {
                        fileInput[i] = new FileInputStream(file);
                    } else {
                        fileOutput[i] = new FileOutputStream(file);
                    }
                } catch (FileNotFoundException e) {
                    files[i] = null;
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }

    public static int fileLeft(int fd) throws IOException {
        if (files[fd] != null && fileInput[fd] != null) {
            return fileInput[fd].available();
        }
        return 0;
    }

    public static void fileClose(int fd) {
        if (files[fd] != null) {
            try {
                if (fileInput[fd] != null) {
                    fileInput[fd].close();
                }
                if (fileOutput[fd] != null) {
                    fileOutput[fd].close();
                }
                files[fd] = null;
                fileInput[fd] = null;
                fileOutput[fd] = null;
            } catch (IOException e) {
                // do nothing.
            }
        }
    }

    public static byte[] fileLoad(byte[] fname) {
        try {
            File file = new File(new String(fname));
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            int pos = 0;
            while (pos < buffer.length) {
                pos += fis.read(buffer, pos, buffer.length - pos);
            }
            fis.close();
            return buffer;
        } catch (IOException e) {
            return null;
        }
    }

    public static void error(byte[] type, byte[] message) throws Exception {
        throw new Exception(new String(type) + ": " + new String(message));
    }

    public static boolean equals(Object a, Object b) {
        return a == b || (a == null ? b.equals(a) : a.equals(b));
    }
}
