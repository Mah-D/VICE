/**
 * Copyright (c) 2007, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Mar 28, 2007
 */

package vpc.core.csr;

import cck.util.Util;
import vpc.core.Value;
import vpc.core.decl.Variable;
import vpc.core.types.Type;
import vpc.tir.expr.Operator;
import vpc.util.Ovid;

import java.util.List;

/**
 * The <code>CSRData</code> class represents a C source representation of the
 * declared static data of a program. This includes any global variables as well
 * as the heap containing instances of structs, arrays, etc.
 *
 * @author Ben L. Titzer
 */
public class CSRData {

    protected final Space defspace = new Space("heap");

    /**
     * The <code>Space</code> class represents an address space that may contain
     * a portion of the heap. A space may represent a memory address space such as
     * a ROM, a RAM, EEPROM, etc.
     */
    public static class Space {
        public final String name;

        public Space(String n) {
            name = n;
        }
    }

    /**
     * The <code>CSRData.Global</code> represents a global variable in the C program.
     */
    public static class Global implements Variable {
        public final String name;
        public final CSRType type;
        public final List<String> attributes;
        public Value value;

        public Global(Space s, String n, CSRType t) {
            name = n;
            type = t;
            attributes = Ovid.newList();
        }

        public String getName() {
            return name;
        }

        public Type getType() {
            return type;
        }
    }

    public static class GetGlobal extends Operator.Op0 implements Operator.Location {

        public final Global global;

        public GetGlobal(Global g) {
            super(g.type);
            global = g;
        }

        public Value apply0() throws Operator.Exception {
            throw Util.unimplemented();
        }

        public Operator getOperator() {
            return this;
        }

        public Operator setOperator() {
            return new SetGlobal(global);
        }
    }

    public static class SetGlobal extends Operator.Op1 {

        public final Global global;

        public SetGlobal(Global g) {
            super(g.type, g.type);
            global = g;
        }

        public Value apply1(Value v1) throws Operator.Exception {
            throw Util.unimplemented();
        }
    }

    /**
     * The <code>CSRValue</code> class represents a value that is specific to the CSR
     * representation.
     *
     * @author Ben L. Titzer
     */
    public abstract static class CSRValue extends Value {

        public final CSRType type;

        protected CSRValue(CSRType t) {
            super();
            type = t;
        }

        public Type getType() {
            return type;
        }
    }
}
