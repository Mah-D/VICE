/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 26, 2006
 */

package vpc.vst;

import cck.parser.*;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.Language;
import vpc.core.Program;
import vpc.core.virgil.*;
import vpc.sched.Stage;
import vpc.vst.stages.*;
import vpc.vst.tir.TIRBuilder;
import vpc.vst.tree.VSTModule;
import vpc.vst.tree.VSTPrinter;

import java.io.*;

/**
 * The <code>VSTStages</code> class implements a namespace class (i.e. contains inner classes)
 * for each of the compilation stages associated with the Virgil syntax tree representation,
 * including parsing, verifying, building types, typechecking, and translating VST to TIR.
 *
 * @author Ben L. Titzer
 */
public class VSTStages {

    /**
     * The <code>Parse</code> class implements the phase of compilation
     * relating to parsing the source file and building the core program
     * representation for later phases of the compilation.
     *
     * @author Ben L. Titzer
     */
    public static class Parse extends Stage {

        public void visitProgram(Program program) {
            for (File f : program.getFiles()) {
                try {
                    Language language = program.language;
                    if (language instanceof V3Language) {
                        VSTModule m = vpc.vst.parser.v3.Virgil3Parser.parseModule(new FileInputStream(f), f.getName(), program.typeEnv);
                        Verifier.buildAndVerifyModule(program, m);
                    } else if (language instanceof V2Language) {
                        VSTModule m = vpc.vst.parser.v2.VirgilParser.parseModule(new FileInputStream(f), f.getName(), program.typeEnv);
                        Verifier.buildAndVerifyModule(program, m);
                    } else {
                        Util.userError("No parser for unknown language: "+ language);
                    }
                } catch (vpc.vst.parser.v3.ParseException e) {
                    // report the parse exception as a source error
                    reportParseException(f, e, e.currentToken.next);
                } catch (vpc.vst.parser.v2.ParseException e) {
                    // report the parse exception as a source error
                    reportParseException(f, e, e.currentToken.next);
                } catch (IOException e) {
                    Util.userError("IOException: " + e.getMessage());
                }
            }
        }

        private void reportParseException(File f, SourceError e, AbstractToken tok) {
            SourcePoint pp = new SourcePoint(f.getName(), tok.beginLine, tok.beginColumn, tok.endColumn);
            String report = "parse error at token \"" + tok + "\"\n" + e.getMessage();
            throw new SourceError("ParseError", pp, report, StringUtil.EMPTY_STRING_ARRAY);
        }
    }


    /**
     * The <code>BuildTypes</code> stage of compilation walks the Virgil syntax tree (without
     * inspecting method bodies) and builds representations of each of the declared types,
     * including classes and components. It does a number of checks on the well-formedness of
     * the types declared. When this stage is completed, all of the types needed in the
     * typechecking phase are found, resolved, and built, so the type system is complete and
     * consistent.
     */
    public static class BuildTypes extends Stage {
        public void visitProgram(Program p) {
            new VSTTypeBuilder(p, new VSTErrorReporter()).buildProgramTypes(p);
        }

    }

    /**
     * The <code>TypeCheck</code> stage of compilation walks the Virgil syntax tree (including
     * the method bodies and expressions initializing fields) and type checks each expression
     * against the type system that was built in the <code>BuildTypes</code> stage.
     */
    public static class TypeCheck extends Stage {
        public void visitProgram(Program p) {
            TypeChecker t = new TypeChecker(p, new VSTErrorReporter());
            for (VirgilClass d : p.virgil.getDeclaredClasses()) t.typeCheckClass(d);
            for (VirgilComponent d : p.virgil.getDeclaredComponents()) t.typeCheckComponent(d);
        }
    }

    /**
     * The <code>PrintVST</code> stage is an ending stage that emits the Virgil syntax tree
     * in a textual format. This is used to output the syntax tree in a format close to
     * the original text.
     */
    public static class PrintVST extends Stage {
        private VSTPrinter printer;

        public PrintVST(VSTPrinter p) {
            printer = p;
        }

        public void visitProgram(Program program) {
            for (File f : program.getFiles()) {

                try {
                    VSTModule m = vpc.vst.parser.v2.VirgilParser.parseModule(new FileInputStream(f), f.getName(), program.typeEnv);
                    m.accept(printer, null);
                } catch (vpc.vst.parser.v2.ParseException e) {
                    vpc.vst.parser.v2.Token tok = e.currentToken.next;
                    SourcePoint pp = new SourcePoint(f.getName(), tok.beginLine, tok.beginColumn, tok.endColumn);
                    String report = "parse error at token \"" + tok + "\"\n" + e.getMessage();
                    throw new SourceError("ParseError", pp, report, StringUtil.EMPTY_STRING_ARRAY);
                } catch (IOException e) {
                    Util.userError("IOException: " + e.getMessage());
                }
            }
        }
    }

    /**
     * The <code>BuildTIR</code> stage of compilation translates a Virgil syntax tree program
     * into a program in the <code>TIR</code> format.
     */
    public static class BuildTIR extends Stage {

        public void visitProgram(Program p) {
            TIRBuilder.buildIRForProgram(p);
        }
    }
}
