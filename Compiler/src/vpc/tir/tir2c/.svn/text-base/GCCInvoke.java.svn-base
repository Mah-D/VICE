/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created May 5, 2007
 */
package vpc.tir.tir2c;

import cck.text.StringUtil;
import cck.util.Option;
import cck.util.Util;
import vpc.core.Program;
import vpc.sched.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * The <code>GCCInvoke</code> definition.
 *
 * @author Ben L. Titzer
 */
public class GCCInvoke extends Stage {

    protected Option.Str GCCNAME = options.newOption("gcc-name", "gcc",
            "This option specifies the name of the gcc compiler. For cross-compilation " +
            "targets, the actual gcc command name may be different than \"gcc\"."); 
    protected Option.List OPTIONS = options.newOptionList("gcc-options", "",
            "This option specifies additional compilation options to gcc.");
    protected Option.Str OPTLEVEL = options.newOption("gcc-opt-level", "2",
            "This option specifies the optimization level for gcc.");

    public void visitProgram(Program p) {
        String gcc = GCCNAME.get();
        String opt = "-O"+OPTLEVEL.get();

        List<String> args = new LinkedList<String>();
        args.add(gcc);
        args.add(opt);
        args.add("-o");
        args.add(p.name+".elf");
        args.addAll(OPTIONS.get());
        args.add(p.name+".c");
        try {
            Process process = Runtime.getRuntime().exec(args.toArray(StringUtil.EMPTY_STRING_ARRAY));
            ProcessDrainer drainer = new ProcessDrainer(process);
            drainer.start();
            process.waitFor();
            drainer.poll = false;
            if ( process.exitValue() != 0 ) {
                StringBuffer buf = new StringBuffer();
                for ( String s : args ) {
                    buf.append(s);
                    buf.append(' ');
                }
                throw Util.failure("native compiler failed: "+buf.toString());
            }
        } catch (Exception e) {
            throw Util.unexpected(e);
        }
    }

    static class ProcessDrainer extends Thread {

        public final Process process;
        public boolean poll = true;

        ProcessDrainer(Process p) {
            process = p;
        }

        public void run() {
            try {
                InputStream is = process.getInputStream();
                InputStream es = process.getErrorStream();
                byte[] buffer = new byte[4096];
                while (poll) {
                    drain(is, buffer);
                    drain(es, buffer);
                    Thread.sleep(10);
                }
                process.waitFor();
            } catch (Exception e) {
                throw Util.unexpected(e);
            }
        }

        boolean drain(InputStream is, byte[] buffer) throws IOException {
            int avail = is.available();
            if (avail > 0) is.read(buffer, 0, avail);
            if (avail < 0) return false;
            return true;
        }
    }
}
