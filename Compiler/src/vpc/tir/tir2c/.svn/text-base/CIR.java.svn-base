/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created Feb 20, 2006
 */
package vpc.tir.tir2c;

import cck.text.Printable;
import cck.text.Printer;
import vpc.util.Ovid;

import java.util.List;

/**
 * The <code>CIR</code> class represents a container class for classes that implement
 * an internal representation of C code. The C code internal representation is
 * used to compile from TIR to C.
 *
 * @author Ben L. Titzer
 */
public class CIR {

    /**
     * The <code>CExpr</code> class represents a C expression. It contains a
     * precedence number that can be used when generating a textual representation
     * when it is necessary to nest the expression in parentheses due to the precedence
     * rules of the C language.
     */
    public static class CExpr {
        protected final int precedence;
        protected final List<String> exprs;

        public CExpr(int prec, String... e) {
            exprs = Ovid.newList();
            for (String s : e) exprs.add(s);
            precedence = prec;
        }

        public String nest(int prec) {
            if (exprs.size() > 1) {
                return asCompoundExpr();
            } else if (precedence < prec) {
                return "(" + getFirst() + ")";
            } else {
                return getFirst();
            }
        }

        public String toString() {
            return nest(0);
        }

        private String asCompoundExpr() {
            StringBuffer buf = new StringBuffer("({");
            for (String s : exprs) {
                buf.append(s);
                buf.append("; ");
            }
            buf.append(" })");
            return buf.toString();
        }

        private String getFirst() {
            return exprs.iterator().next();
        }
    }

    /**
     * The <code>CStmt</code> class represents a C statement.
     */
    public abstract static class CStmt implements Printable {
        protected List<CStmt> unNest() {
            return null;
        }

        protected abstract String asString();
    }

    public static class CSingle extends CStmt {
        protected final String rep;

        public CSingle(String r) {
            rep = r;
        }

        public void print(Printer p) {
            p.print(rep);
            p.println(";");
        }

        public String asString() {
            return rep + ";";
        }
    }

    /**
     * The <code>CIfStmt</code> class represents a C if statement that contains a condition
     * (rendered as a string), and two statements, one for the true branch, and one for the
     * false branch.
     */
    public static class CIfStmt extends CStmt {
        protected final String before;
        protected final CStmt trueBody;
        protected final CStmt falseBody;

        public CIfStmt(String b, CStmt ts, CStmt fs) {
            before = b;
            trueBody = ts;
            falseBody = fs;
        }

        public void print(Printer p) {
            p.print(before);
            trueBody.print(p);
            p.nextln();
            if (falseBody != null) {
                p.print("else ");
                falseBody.print(p);
            }
        }

        public String asString() {
            String ec = falseBody == null ? "" : " else " + falseBody.asString();
            return before + " " + trueBody.asString() + ec;
        }
    }

    /**
     * The <code>CBlock</code> class represents a block of C statements. It may contain
     * code before and after the block. For example, a "for" loop is represented as a block
     * with the code "for (...) " at the beginning.
     */
    public static class CBlock extends CStmt {
        protected String before;
        protected CStmt body;
        protected String after;

        public CBlock(String bf, CStmt b, String a) {
            before = bf;
            body = b;
            after = a;
        }

        public void print(Printer p) {
            p.startblock(before);
            body.print(p);
            p.endblock(after);
        }

        public String asString() {
            return before + " " + body.asString() + " " + after;
        }
    }

    /**
     * The <code>CNested</code> class represents a nested list of statements that can be
     * un-nested when generating code.
     */
    public static class CNested extends CStmt {
        protected final String beginLabel;
        protected final String endLabel;
        protected final List<CStmt> body;

        public CNested(List<CStmt> b) {
            this(null, b, null);
        }

        public CNested(String bl, List<CStmt> b, String el) {
            beginLabel = bl;
            body = b;
            endLabel = el;
        }
        public void print(Printer p) {
            p.startblock();
            if ( beginLabel != null ) p.println(beginLabel+":");
            for (CStmt c : body) c.print(p);
            if ( endLabel != null ) p.println(endLabel+": ;");
            p.endblock();
        }

        protected List<CStmt> unNest() {
            if ( beginLabel == null && endLabel == null) return body;
            else return null;
        }

        public String asString() {
            StringBuffer buf = new StringBuffer();
            appendLabel(beginLabel, buf);
            buf.append("{");
            for (CStmt st : body) buf.append(st.asString());
            appendLabel(endLabel, buf);
            buf.append("}");
            return buf.toString();
        }

        private static void appendLabel(String lbl, StringBuffer buf) {
            if ( lbl != null ) {
                buf.append(lbl);
                buf.append(": ");
            }
        }
    }
}
