/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Oct 7, 2007
 */
package vpc.core.sets;

import vpc.util.Ovid;

import java.util.*;

/**
 * The <code>ConstraintSystem</code> definition.
 *
 * @author Ben L. Titzer
 */
public class ConstraintSystem {

    public interface SideEffect {
        public void fire(Object e1, ESet s1);
    }

    protected static class REffect {
        final ESet set;
        final SideEffect effect;
        REffect next;

        REffect(ESet s, SideEffect c) {
            set = s;
            effect = c;
        }
    }

    protected static class EffectInvoke {
        final REffect effect;
        final Object element;
        EffectInvoke(REffect e, Object o) {
            effect = e;
            element = o;
        }
    }

    protected static class Rep {
        private Set<Object> elements = Ovid.newSet();
        private Set<ESet> outgoing = Ovid.newSet();
        private REffect effects;
    }

    public static class ESet {
        protected Rep rep;
        public final String name;
        private ESet(String n) {
            this.name = n;
            this.rep = new Rep();
        }
        public String toString() {
            return "set "+name;
        }
    }

    protected Queue<EffectInvoke> invokes = new LinkedList<EffectInvoke>();

    public ESet newSet(String name) {
        return new ESet(name);
    }

    public void addElementConstraint(Object o, ESet a) {
        pushElement(o, a);
    }

    public void addSubsetConstraint(ESet a, ESet b) {
        a.rep.outgoing.add(b);
        for ( Object o : a.rep.elements ) pushElement(o, b);
    }

    public void addEqualityConstraint(ESet a, ESet b) {
        // are the sets already the same?
        if ( a.rep == b.rep ) return;
        // merge the outgoing edge lists.
        a.rep.outgoing.addAll(b.rep.outgoing);
        // merge the effect lists.
        if ( b.rep.effects != null ) {
            REffect s = b.rep.effects;
            while ( s.next != null ) s = s.next;
            s.next = a.rep.effects;
            a.rep.effects = s;
        }
        // use the same set representation
        b.rep = a.rep;
        // propagate new elements to outgoing
        for ( Object o : a.rep.elements ) pushElement(o, b);
    }

    public void addSideEffect(ESet a, SideEffect c) {
        REffect f = new REffect(a, c);
        f.next = a.rep.effects;
        a.rep.effects = f;
    }

    public void addProductSideEffect(ESet a, ESet b, SideEffect c) {
        // side effects of pairs are not yet supported.
        addSideEffect(a, new LeftSetEffect(c, b));
        addSideEffect(b, new RightSetEffect(c, a));
    }

    public void solve() {
        for ( EffectInvoke c = invokes.poll(); c != null; c = invokes.poll() ) {
            c.effect.effect.fire(c.element, c.effect.set);
        }
    }

    public Set getSolution(ESet s) {
        return s.rep.elements;
    }

    private void propagate(Object o, Collection<ESet> edges) {
        for ( ESet a : edges ) pushElement(o, a);
    }

    private void pushElement(Object o, ESet a) {
        if (a.rep.elements.add(o)) {
            collectSideEffects(o, a);
            propagate(o, a.rep.outgoing);
        }
    }

    private void collectSideEffects(Object o, ESet a) {
        for ( REffect f = a.rep.effects; f != null; f = f.next ) {
            invokes.offer(new EffectInvoke(f, o));
        }
    }

    private class LeftSetEffect implements SideEffect {
        final SideEffect effect;
        final ESet right;
        LeftSetEffect(SideEffect e, ESet r) {
            effect = e;
            right = r;
        }
        public void fire(Object o1, ConstraintSystem.ESet s1) {
            for ( Object o2 : getSolution(right) ) {
                effect.fire(new Object[] { o1, o2 }, s1);
            }
        }
    }

    private class RightSetEffect implements SideEffect {
        final SideEffect effect;
        final ESet left;
        RightSetEffect(SideEffect e, ESet r) {
            effect = e;
            left = r;
        }
        public void fire(Object o2, ConstraintSystem.ESet s1) {
            for ( Object o1 : getSolution(left) ) {
                effect.fire(new Object[] { o1, o2 }, left);
            }
        }
    }
}
