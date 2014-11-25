/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 2, 2006
 */

package vpc.sched;

import cck.util.ClassMap;
import cck.util.Options;
import vpc.core.Program;
import vpc.tir.opt.*;
import vpc.tir.opt.rma.TIR_RMA;
import vpc.tir.stages.*;
import vpc.tir.tir2c.CSREmit;
import vpc.tir.tir2c.GCCInvoke;
import vpc.util.Ovid;
import vpc.vst.VSTStages;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The <code>Registry</code> class implements a registry of all the compiler
 * passes, including optimizations and translations between various program
 * representations. This entries in this registry are used to build the list
 * of compiler passes to apply to the program.
 *
 * @author Ben L. Titzer
 */
public class Registry {

    protected static Registry globalRegistry;

    public static synchronized Registry getDefaultRegistry() {
        if ( globalRegistry == null ) {
            globalRegistry = new Registry();
            globalRegistry.initDefault();
        }
        return globalRegistry;
    }

    protected final ClassMap stageMap;
    protected final List<String> stageList;
    protected final Map<String, String> signatures;
    protected final Map<String, StageSignature> sigMap;

    public Registry() {
        stageMap = new ClassMap("Compilation Stage", Stage.class);
        stageList = Ovid.newLinkedList();
        signatures = Ovid.newMap();
        sigMap = Ovid.newMap(Ovid.WEAK);
    }

    /**
     * The <code>Macro</code> class implements a compilation stage that
     * is the combination of a sequence of other compilation stages.
     */
    public static class Macro extends Stage {
        protected final Stage[] stages;

        public Macro(String str) {
            stages = Scheduler.getFixedPath(str);
        }

        public void visitProgram(Program p) throws IOException {
            for (Stage s : stages){ 
            	s.visitProgram(p);
            	
            }
        }

        public void processOptions(Options o) {
            for (Stage s : stages) s.processOptions(o);
        }
    }

    void initDefault() {
        // Parse: parse the source code to VST and verify structural properties
        addStage("virgil-parse", "vsc->vst+v",         VSTStages.Parse.class);
        // BuildTypes: build the type system from the declared types in the program
        addStage("virgil-gtb",   "vst+v->vst+t",       VSTStages.BuildTypes.class);
        // TypeCheck: type check the VST
        addStage("virgil-tc",    "vst+tv->vst+c",      VSTStages.TypeCheck.class);
        // BuildTIR: build TIR code from the VST
        addStage("virgil-tir",   "vst+tvc->tir",       VSTStages.BuildTIR.class);
        // PrintVST: debugging stage to output the VST tree
        addStage("virgil-emit",  "vst->vst",           VSTStages.PrintVST.class);
        // SimpleClosure: compute the closure over classes and methods
        addStage("closure",      "tir->tir+c",         SimpleClosure.class);
        // ObjectLayout: compute object layouts
        addStage("layout",       "tir+c->tir+l",       ObjectLayout.class);
        // TIRInit: run the initializers
        addStage("init",         "tir+cl->tir+i",      TIRInit.class);
        // TIR_CCP: perform conditional constant propagation optimization
        addStage("ccp",          "tir->tir-v",         TIR_CCP.class);
        // TIRInliner: perform method inlining
        addStage("inline",       "tir->tir-sv",        TIRInliner.class);
        // TIRSimplifier: perform TIR simplification
        addStage("simp",         "tir->tir+s-v",       TIRSimplifier.class);
        // RawConversion: convert raw operations
        addStage("raw",          "tir+s->tir+rs-v",    RawConversion.class);
        // ObjectConversion: convert object operations
        addStage("obj",          "tir+rs->tir+or-v",   ObjectConversion.class);
        // Monomorphizer: convert polymorphic code into monomorphic code
        addStage("mono",         "tir+i->tir+n-l",     Monomorphizer.class);
        // TIRChecker: verify well-formedness of TIR code
        addStage("check",        "tir->tir+v",         TIRChecker.class);
        // RMAnalyzer: perform RMA optimization
        addStage("rma",          "tir+i-m->tir+lm-v",  TIR_RMA.class);
        // TIREmit: emit TIR code as text
        addStage("emit-tir",     "tir->tir",           TIREmit.class);
        // CSREmit: emit TIR code as C source code
        addStage("emit-c",       "tir+iorslmn->csc",   CSREmit.class);
        // EmitHeap: emit the heap as a dot graph
        addStage("emit-heap",    "tir+i->tir",         EmitHeap.class);
        // gcc: call gcc to compile the outputted C file
        addStage("gcc",          "csc->elf",           GCCInvoke.class);

        // macros: for shorter optimization sequences
        addMacro("virgil", "virgil-parse,virgil-gtb,virgil-tc,virgil-tir,closure");
        addMacro("init", "layout,simp,init");

        addMacro("vfront", "virgil,init,mono,raw,rma");
        addMacro("vback", "obj,simp,emit-c");
        addMacro("opt0", "mono,raw,obj,simp");
        addMacro("opt1", "mono,raw,rma,obj,simp");
        addMacro("opt2", "mono,raw,rma,inline,rma,simp,obj,simp");
        addMacro("opt3", "mono,raw,rma,ccp,inline,ccp,inline,ccp,rma,simp,obj,simp");
    }

    public void addMacro(String alias, String str) {
        stageMap.addInstance(alias, new Macro(str));
    }

    public void addStage(String alias, String sstr, Class<? extends Stage> clazz) {
        signatures.put(alias, sstr);
        stageMap.addClass(alias, clazz);
        stageList.add(alias);
    }

    public void addStage(String alias, String sstr, Stage stg) {
        signatures.put(alias, sstr);
        stageMap.addInstance(alias, stg);
        stageList.add(alias);
    }

    public StageSignature getSignature(String alias) {
        String sig = signatures.get(alias);
        if ( sig == null ) return null;
        StageSignature sobj = sigMap.get(sig);
        if ( sobj == null ) {
            sobj = StageSignature.parseSignature(sig);
            sigMap.put(sig, sobj);
        }
        return sobj;
    }

    public StageSignature getSignature(Stage stage) {
        return getSignature(stageMap.getAlias(stage));
    }

    public Stage getStage(String alias) {
        return (Stage) stageMap.getObjectOfClass(alias);
    }

    public static String getStageName(Stage s) {
        return getDefaultRegistry().stageMap.getAlias(s);
    }
}
