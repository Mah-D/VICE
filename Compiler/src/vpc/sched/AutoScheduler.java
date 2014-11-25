/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Dec 11, 2006
 */

package vpc.sched;

import cck.util.Options;
import vpc.util.Ovid;

import java.util.*;

/**
 * The <code>Scheduler</code> class implements the algorithms that choose
 * the sequence of compilation passes to apply to a program. The scheduler
 * uses the model of the compiler in the <code>Registry</code> class to
 * determine the legal paths through the compiler. The scheduler then uses
 * tuning heuristics, options from the user, and the structure of the
 * program to choose and refine a set of compilation passes for the program.
 *
 * @author Ben L. Titzer
 */
public class AutoScheduler extends Scheduler {

    private final Registry registry;
    private Set<String> globalRequired;
    private Map<String, StageSignature> specialSignatures;

    protected static class Path {
        final int length;
        final Path prev;
        final String node;
        final IRState state;

        Path(Path p, String n, IRState i) {
            length = p == null ? 1 : p.length + 1;
            prev = p;
            node = n;
            state = i;
        }

        public Stage[] buildReverse(Registry registry) {
            Stage[] result = new Stage[length];
            int cntr = length;
            for ( Path p = this; p != null; p = p.prev )
                result[--cntr] = registry.getStage(p.node);
            return result;
        }
    }

    public AutoScheduler() {
        this(Registry.getDefaultRegistry(), null);
    }

    public AutoScheduler(Registry r, Collection<String> req) {
        super("The auto scheduler uses an internal model of all the known compiler passes to " +
                "construct a sequence of transformation passes that transform a program in a " +
                "given input format to a given output format, with optional optimizations.");
        registry = r;
        addOptimizations(req);
    }

    public void addOptimizations(Collection<String> req) {
        if ( req != null && !req.isEmpty()) {
            globalRequired = Ovid.newSet();
            specialSignatures = Ovid.newMap();
            globalRequired.addAll(req);
            extendSignatures();
        }
    }

    private void extendSignatures() {
        for ( String s : globalRequired ) {
            // for every pass in the required set, alter its signature
            StageSignature sig = registry.getSignature(s);
            if ( sig != null ) {
                sig = sig.copy();
                sig.output.addPlusAttr("$"+s);
                specialSignatures.put(s, sig);
            }
        }
    }

    /**
     * The <code>findPath()</code> method searches the internal model of the
     * compiler for a path of compilation stages that transforms the program
     * in the source format into the destination format. The source format
     * and the destination format are given as strings that are used to
     * build <code>IRState</code> instances.
     * @param a the source representation of the program
     * @param b the destination representation of the program
     * @return an array of <code>Stage</code> objects that represent a path
     * from the source format to the destination format if such a path
     * exists; null if no such path exists
     */
    public Stage[] findPath(String a, String b) {
        return findPath(StageSignature.parseIRState(a), StageSignature.parseIRState(b));
    }

    /**
     * The <code>findPath()</code> method searches the internal model of the
     * compiler for a path of compilation stages that transforms the program
     * in the source format into the destination format. The source format
     * and the destination format are given as <code>IRState</code> instances.
     * @param sa the source representation of the program
     * @param sb the destination representation of the program
     * @return an array of <code>Stage</code> objects that represent a path
     * from the source format to the destination format if such a path
     * exists; null if no such path exists
     */
    public Stage[] findPath(IRState sa, IRState sb) {
        // add the required stages to the input for the last state
        extendOutput(sb);

        // initialize the visited set and the queue
        Set<IRState> visited = Ovid.newSet();
        Queue<Path> queue = initQueue(sa, visited);

        for ( Path path = queue.poll(); path != null; path = queue.poll()) {
            StageSignature sig = getSignature(path.node);
            IRState ns = sig.process(path.state);
            if ( sb.isMetBy(ns) ) {
                // we found a candidate node: build a path
                return path.buildReverse(registry);
            } else {
                // add all the successors to this node to the queue
                addSuccessors(path, ns, visited, queue);
            }
        }
        return null;
    }

    private void extendOutput(IRState sb) {
        if ( globalRequired != null )
            for ( String s : globalRequired ) sb.addPlusAttr("$"+s);
    }

    private StageSignature getSignature(String stage) {
        StageSignature sig = null;
        if ( specialSignatures != null ) sig = specialSignatures.get(stage);
        if ( sig == null) sig = registry.getSignature(stage);
        return sig;
    }

    private Queue<Path> initQueue(IRState start, Set<IRState> known) {
        Queue<Path> queue = Ovid.newLinkedList();
        addSuccessors(null, start, known, queue);
        return queue;
    }

    private void addSuccessors(Path path, IRState input, Set<IRState> known, Queue<Path> queue) {
        for ( String stage : registry.stageList ) {
            StageSignature sig = getSignature(stage);
            if ( sig != null && sig.canAccept(input) ) {
                // queue this stage if it produces a previously unknown state
                IRState ns = sig.process(input);
                if ( !known.contains(ns) ) {
                    // if this is a new stage, add the node to be explored
                    queue.offer(new Path(path, stage, input));
                    known.add(ns);
                }
            }
        }
    }

    public Stage[] getPath(Options opt) {
        options.process(opt);
        addOptimizations(OPTS.get());
        return findPath(INPUT.get(), OUTPUT.get());
    }
}
