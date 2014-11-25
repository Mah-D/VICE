/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 */

package vpc;

import cck.text.Terminal;
import cck.util.*;
import vpc.core.Language;
import vpc.core.Program;
import vpc.core.virgil.V2Language;
import vpc.core.virgil.V3Language;
import vpc.hil.parser.HILParser;
import vpc.sched.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * The <code>Compiler</code> class is the entrypoint the vpc compiler.
 * It parses the arguments, sets up the options, and controls the
 * compilation process.
 *
 * @author Ben L. Titzer
 */
public class Compiler {

    public static final Options options = new Options();

    public static long startMillis;

    public static final Option.Bool HELP = options.newOption("help", false,
            "Displays this help message.");
    static final Option.List CONFIG = options.newOptionList("config", "",
            "This option can be used to specify a list of configuration files that contain " +
            "compiler options and switches. This is useful to reuse a large number " +
            "of command line options without cluttering the invocation of the compiler. " +
            "The configuration files are loaded in the order that they are specified, and " +
            "additional command line options specified override the options specified " +
            "in the configuration file(s).");
    static final Option.Bool COLORS = options.newOption("colors", true,
            "This option enables or disables the printing of control characters " +
            "that color text output to the terminal.");
    public static final Option.Bool VERSION = options.newOption("version", false,
            "Display the detailed compiler version, copyright and license text.");
    static final Option.Bool STATISTICS = options.newOption("statistics", false,
            "This option enables reporting of internal compiler statistics for " +
            "each phase of compilation that includes diagnostic information " +
            "about the program being compiled and the operation of the compiler itself.");
    static final Option.Str DEVICE = options.newOption("device", "",
            "This option specifies the target hardware device for the program. The target device " +
            "description includes the memory configuration of the device as well as interrupts, " +
            "hardware registers, and other hardware capabilities that are exposed to the program " +
            "through the hardware interface language. The device specified in this option must be " +
            "compatible with the target specified in the \"target\" option.");
    static final Option.Str LANGUAGE = options.newOption("language", "virgil2",
            "This option selects the source language of the specified program. The compiler " +
            "uses the source language to select an appropriate parser, typechecker, and " +
            "front end.");

    public static final ClassMap LANGUAGES = new ClassMap("Language", Language.class);

    static {
        LANGUAGES.addClass("virgil2", V2Language.class);
        LANGUAGES.addClass("virgil3", V3Language.class);
    }

    public static void main(String[] args) {

        startMillis = System.currentTimeMillis();

        try {
            // parse the command line options
            args = parseOptions(args);

            // if the user specified the -version option, print it and exit
            if ( VERSION.get() ) {
                Help.displayVersionAndCopyright();
                return;
            }

            // no files specified on command line, print help
            if (args.length == 0 || HELP.get() ) {
                Help.printHelp(args);
                return;
            }

            // if all the files exist, begin compilation
            if (Util.verifyFilesExist(args)) {
                Stage[] stages = getStages();
                runCompilation("output", stages, args);
            }

        } catch (Util.Error e) {
            e.report();
            System.exit(1);
        } catch (Throwable t) {
            Terminal.print(Terminal.ERROR_COLOR, "Unexpected exception");
            Terminal.println(": " + t.getClass());
            t.printStackTrace();
            System.exit(2);
        }
    }

    private static Stage[] getStages() {
        return new Scheduler("").getPath(options);
    }

    private static vpc.hil.Device getDevice() throws Exception {
        if (DEVICE.isBlank()) return null;
        String fname = DEVICE.get();
        Util.verifyFileExists(fname);
        HILParser hp = new HILParser(new FileInputStream(fname));
        return hp.Device();
    }

    private static void runCompilation(String programname, Stage[] stages, String[] files) throws Exception {
        Program program = new Program(programname, (Language)LANGUAGES.getObjectOfClass(LANGUAGE.get()));
        program.setDevice(getDevice());
        Compilation c = new Compilation(program, stages, options);
        try {
            for (String f: files) c.addFile(f);
            c.run();
        } finally {
            if ( STATISTICS.get() ) c.printReport();
        }
    }

    public static String[] parseOptions(String[] args) throws IOException {
        options.parseCommandLine(args);
        List configs = CONFIG.get();
        if (!configs.isEmpty()) {
            // if the config-file option is specified, load config file options
            for (Object o : configs) {
                String fname = (String) o;
                Util.verifyFileExists(fname);
                options.loadFile(fname);
            }
            options.parseCommandLine(args);
        }
        Terminal.useColors = COLORS.get();
        return options.getArguments();
    }

}
