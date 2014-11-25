/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Nov 25, 2006
 */
package vpc;

import cck.help.HelpCategory;
import cck.help.HelpSystem;
import cck.text.StringUtil;
import cck.text.Terminal;
import cck.util.ClassMap;
import cck.util.Util;
import vpc.core.virgil.VirgilError;
import vpc.util.Ovid;

import java.util.*;

/**
 * The <code>MainHelp</code> definition.
 *
 * @author Ben L. Titzer
 */
public class Help {

    private static final Map<String, HelpCategory> mainCategories = Ovid.newMap();

    static class LazyCategory extends HelpCategory {
        LazyCategory(String n, String h) {
            super(n, h);
        }
    }

    private static HelpCategory ALL;
    private static HelpCategory MAIN;

    public static void printHelp(String[] args) {
        title();
        printUsage();

        // build all help categories that are mentioned
        buildCategories();
        buildIndices();

        if (args.length == 0) {
            // if a category is not specified, print the main category
            MAIN.printHelp();
        } else {
            // if there are categories specified, print them
            for ( String a : args ) printHelp(a);
        }

        // print a single footer advertising the mailing list, website, etc
        printFooter();
    }

    private static void buildCategories() {
        // build the "main" category
        MAIN = new HelpCategory("main", "");
        MAIN.addSection("OVERVIEW","VPC is a prototype compiler for the Virgil programming language that " +
                "performs semantic checking on Virgil source code as well as optimizations, and generates " +
                "either a machine-independent intermediate code, or a machine-dependent C code which " +
                "is suitable for gcc. Additionally, VPC implements a number of program analysis and " +
                "verification tools for Virgil programs. Each of these analysis and optimization " +
                "tools can be accessed through the compiler options that are described in the next section.\n" +
                "The help system also contains documentation for a number of other help categories related " +
                "to compiler subsystems and the Virgil language specification. To access help and the " +
                "options related to a particular category, specify the name of the category along with the " +
                "\"help\" option.");
        MAIN.addOptionSection("The main options to VPC allow the user to select the compilation phases and " +
                "compiler optimizations. For more information on a specify option, see the short descriptions " +
                "below.", Compiler.options);

        // build the "all" help category
        ALL = new HelpCategory("all", "Print a list of all categories for which help is available.");
        ALL.addSection("OVERVIEW", "The Virgil Prototype Compiler provides help in many categories that are " +
                "all accessible from the command line. This help page gives a listing of all available " +
                "help categories. Each can be accessed by supplying it as a parameter to the \"-help\" " +
                "option.");

        addMainCategory(ALL);

        // build the rest of the categories
        addMainCategory(VirgilError.newErrorHelpCategory());
    }

    private static void buildIndices() {

        // add the main subcategories to "main"
        MAIN.addSubcategorySection("ADDITIONAL HELP CATEGORIES",
                "Additional help is available on a category by category basis. Below is a list of the " +
                "main help categories available. To access help for a specific category, specify the " +
                "\"-help\" option followed by the name of category.", getMainCategories());

        // add all subcategories to "all"
        ALL.addSubcategorySection("ALL HELP CATEGORIES", "Below is a listing of all the help categories available.",
                getCategories(HelpSystem.getSortedList()));
    }

    private static void printUsage() {
        int[] colors = {Terminal.COLOR_RED,
                        -1,
                        Terminal.COLOR_GREEN,
                        -1,
                        Terminal.COLOR_YELLOW,
                        -1,
                        Terminal.COLOR_YELLOW,
                        -1,
                        Terminal.COLOR_YELLOW,
                        -1};

        String[] strs = {"Usage", ": ", "vpc", " [", "options", "] <", "files", ">"};
        Terminal.print(colors, strs);
        Terminal.nextln();

        int[] colors2 = {Terminal.COLOR_RED,
                         -1,
                         Terminal.COLOR_GREEN,
                         -1,
                         Terminal.COLOR_YELLOW,
                         -1};

        String[] strs2 = {"Usage", ": ", "vpc -help", " [", "category", "]"};
        Terminal.print(colors2, strs2);
        Terminal.println("\n");
    }

    private static void printFooter() {
        Terminal.println("For more information, see the online documentation at: ");
        Terminal.printCyan("http://compilers.cs.ucla.edu/virgil");
        Terminal.nextln();
        Terminal.println("To report bugs or seek help, consult the Virgil mailing list: ");
        Terminal.printCyan("http://lists.ucla.edu/cgi-bin/mailman/listinfo/virgil");
        Terminal.nextln();
        Terminal.print("Please include the version number [");
        printVersion();
        Terminal.print("] when posting to the list.");
        Terminal.nextln();
    }

    private static void printHelp(String a) {

        HelpCategory hc = HelpSystem.getCategory(a);
        if ( hc != null ) hc.printHelp();
        else Util.userError("Help category "+ StringUtil.quote(a)+" not found");
    }

    public static void displayVersionAndCopyright() {
        title();
        String notice =
                    "Redistribution and use in source and binary forms, with or without " +
                    "modification, are permitted provided that the following conditions " +
                    "are met:\n\n" +

                    "Redistributions of source code must retain the above copyright notice, " +
                    "this list of conditions and the following disclaimer.\n\n" +

                    "Redistributions in binary form must reproduce the above copyright " +
                    "notice, this list of conditions and the following disclaimer in the " +
                    "documentation and/or other materials provided with the distribution.\n\n" +

                    "Neither the name of the University of California, Los Angeles nor the " +
                    "names of its contributors may be used to endorse or promote products " +
                    "derived from this software without specific prior written permission.\n\n" +

                    "THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS " +
                    "\"AS IS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT " +
                    "LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR " +
                    "A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT " +
                    "OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, " +
                    "SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT " +
                    "LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, " +
                    "DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY " +
                    "THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT " +
                    "(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE " +
                    "OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n\n";

        Terminal.print(StringUtil.formatParagraphs(notice, 0, 0, Terminal.MAXLINE));
    }

    public static void printVersion() {
        Terminal.printBrightBlue(Version.getVersion().toString());
    }

    public static void title() {
        Terminal.printBrightBlue("Virgil Prototype Compiler ");
        Terminal.print("[");
        printVersion();
        Terminal.println("]");
        Terminal.println("Copyright (c) 2004-2007, Regents of the University of California");
        Terminal.println("All rights reserved.\n");
    }

    private static void addAll() {
        addMainCategory(VirgilError.newErrorHelpCategory());
        // TODO: build all the main categories
    }

    public static void addMainCategory(HelpCategory cat) {
        HelpSystem.addCategory(cat.name, cat);
        mainCategories.put(cat.name, cat);
    }

    private static List<HelpCategory> getMainCategories() {
        Set<String> str = mainCategories.keySet();
        List list = Collections.list(Collections.enumeration(str));
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        return getCategories(list);
    }

    public static List<HelpCategory> getCategories(List list) {
        LinkedList<HelpCategory> nl = Ovid.newLinkedList();
        for (Object o : list) {
            String str = (String) o;
            nl.addLast(HelpSystem.getCategory(str));
        }
        return nl;
    }

    public static void addSubCategories(ClassMap vals) {
        List l = vals.getSortedList();
        for (Object o : l) {
            String val = (String) o;
            Class cz = vals.getClass(val);
            if (HelpCategory.class.isAssignableFrom(cz)) HelpSystem.addCategory(val, cz);
        }
    }
}
