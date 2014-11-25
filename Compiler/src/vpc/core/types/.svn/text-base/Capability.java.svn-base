/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Feb 13, 2006
 */

package vpc.core.types;

import vpc.core.Value;
import vpc.tir.expr.Operator;

/**
 * The <code>Capability</code> class represents a capability for a type. A capability
 * is a syntactic construct representing a legal use of expressions of a particular
 * type. For example, a <code>Capability.BinOp</code> instance represents a binary
 * infix operator such as <code>+</code>, which is supported by types such as <code>int</code>
 * in Virgil.
 *
 * @author Ben L. Titzer
 */
public interface Capability {

    /**
     * The <code>BinOp</code> class represents the ability of a type to support a
     * particular infix binary operator such as, for example, addition (+).
     */
    public class BinOp {
        public final String opname;
        public final Operator.Op2 binop;

        public BinOp(String o, Operator.Op2 b) {
            opname = o;
            binop = b;
        }

    }

    /**
     * The <code>UnaryOp</code> class represents the ability of a type to support a
     * particular prefix unary operator such as, for example, bitwise negation (~).
     */
    public class UnaryOp {
        public final String opname;
        public final Operator.Op1 unop;

        public UnaryOp(String o, Operator.Op1 b) {
            opname = o;
            unop = b;
        }
    }

    public class AutoOp {
        public final String opname;
        public final Operator.Op2 binop;
        public final Value value;

        public AutoOp(String o, Operator.Op2 b, Value v) {
            opname = o;
            binop = b;
            value = v;
        }
    }

    public class IndexOp {
        public final String opname;
        public final Operator.Op2 read;
        public final Operator.Op3 write;

        public IndexOp(String o, Operator.Op2 r, Operator.Op3 w) {
            opname = o;
            read = r;
            write = w;
        }
    }

}
