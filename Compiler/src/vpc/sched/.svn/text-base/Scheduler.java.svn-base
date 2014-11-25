/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: May 3, 2007
 */

package vpc.sched;

import cck.help.HelpCategory;
import cck.text.StringUtil;
import cck.util.*;

import java.util.Collection;

/**
 * The <code>Scheduler</code> class generates a list of compiler passes
 * to apply given a user's input options.
 *
 * @author Ben L. Titzer
 */
public class Scheduler extends HelpCategory {

    public static final ClassMap targetMap = new ClassMap("Target", Compilation.Target.class);

    public static final String[] optlevels = {
        "virgil,init,opt0,emit-c,gcc",
        "virgil,init,opt1,emit-c,gcc",
        "virgil,init,opt2,emit-c,gcc",
        "virgil,init,opt3,emit-c,gcc"
    };

    static {
        targetMap.addClass("portable", PortableTarget.class);
        targetMap.addClass("avr", AVRTarget.class);
    }

    public static Stage[] getFixedPath(String str) {
        return getFixedPath(Registry.getDefaultRegistry(), str);
    }

    public static Stage[] getFixedPath(Registry reg, String str) {
        String[] names = str.split(",");
        Stage[] stages = new Stage[names.length];
        for (int i = 0; i < names.length; i++ ) {
            stages[i] = reg.getStage(names[i]);
        }
        return stages;
    }

    public static Stage[] getAutoPath(Registry reg, Collection<String> req, String input, String output) {
        AutoScheduler scheduler = new AutoScheduler(reg, req);
        return scheduler.findPath(input, output);
    }

    /**
     * The <code>PortableTarget</code> class implements a portable compilation target
     * that generates C code that is compiled to native code by a native compiler.
     * This target will generate platform-independent code that relies on neither
     * the target machine architecture, operating system, etc.
     */
    public static class PortableTarget extends Compilation.Target {

        protected void setOptions(Scheduler sched, Options options) {
            options.setOption("linkage", "c:user");
            options.setOption("gcc-opt-level", sched.OPTLEVEL.stringValue());
        }
    }

    /**
     * The <code>AVRTarget<code> class implements a target specific to the AVR architecture.
     * It will generate code that is dependent on the AVR platform and contains C idioms
     * that are specified to avr-gcc, which are needed for things such as interrupt handlers.
     */
    public static class AVRTarget extends Compilation.Target {

        protected void setOptions(Scheduler sched, Options options) {
            String device = options.getOptionValue("device");
            if ( "".equals(device) )
                Util.userError("The \"device\" option must be specified when targetting AVR.");
            options.setOption("avr-rom", "true");
            options.setOption("gcc-name", "avr-gcc");
            options.setOption("linkage", "c:avr");
            options.setOption("gcc-options", "-mmcu="+ StringUtil.baseFileName(device));
            options.setOption("gcc-opt-level", sched.OPTLEVEL.stringValue());
        }
    }

    protected final Option.Str TARGET = options.newOption("target", "portable",
            "This option selects the target machine of the compiler. For example, specifying the " +
            "\"portable\" value for this option selects portable C source code as the output format. " +
            "Other targets include specific options for the compilation passes and " +
            "optimization levels.");
    protected final Option.Long OPTLEVEL = options.newOption("opt-level", 1,
            "This option selects the optimization level applied to the program.");
    protected final Option.Str INPUT = options.newOption("input", "vsc",
            "This option allows the user to specify the input format of the program. The " +
            "input format includes the source language and its representation. For example, " +
            "the default, \"vsc\" corresponds to Virgil source code.");
    protected final Option.Str OUTPUT = options.newOption("output", "",
            "This option selects the output format of the compiler. For example, specifying the " +
            "\"csc\" value for this option selects C source code as the output format; " +
            "the compiler will transform the input program through a series of passes " +
            "and optimizations and produce C source code.");
    protected final Option.List OPTS = options.newOptionList("opts", "",
            "This option allows the user to specify a list of optional passes that should be " +
            "performed by the compiler. The compiler stage scheduler will attempt to find " +
            "a compilation path from the source representation to the output representation " +
            "that includes the specified optional paths.");
    protected final Option.List STAGES = options.newOptionList("stages", "",
            "This option specifies a list of the compilation stages (or phases) to " +
            "apply to the program. Each phase produces a representation of the " +
            "program that is fed into the next stage.");


    public Scheduler(String help) {
        super("scheduler", help);

        addSection("SCHEDULER OVERVIEW", help);
        addOptionSection("Help for the options accepted by this scheduler is below.", options);
    }

    /**
     * The <code>getPath()</code> method gets the compilation path for the specified
     * set of options.
     * @param opt the options specified on the command line
     * @return an array of <code>Stage</code> objects representing the compilation path
     */
    public Stage[] getPath(Options opt) {
        options.process(opt);
        if ( STAGES.get().size() > 0 ) {
            // if the user specified a fixed list of stages, get them
            return getFixedPath(STAGES.stringValue());
        } else if ( !OUTPUT.isBlank() ) {
            // if the user specified a specific output format, get it
            AutoScheduler auto = new AutoScheduler();
            auto.addOptimizations(OPTS.get());
            return auto.findPath(INPUT.get(), OUTPUT.get());
        } else {
            // otherwise, use the basic scheduler.
            Compilation.Target t = (Compilation.Target)targetMap.getObjectOfClass(TARGET.get());
            t.setOptions(this, opt);
            return t.getPath(this, opt);
        }
    }

}
