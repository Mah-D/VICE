/**
 * Copyright (c) 2005, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Creation date: Jan 11, 2006
 */

package vpc.tir.stages;

import cck.parser.AbstractToken;
import cck.text.StringUtil;
import cck.util.Util;
import vpc.core.*;
import vpc.core.base.*;
import vpc.core.decl.CompoundDecl;
import vpc.core.decl.Method;
import vpc.core.types.Type;
import vpc.core.types.TypeCon;
import vpc.core.virgil.*;
import vpc.util.Maybe;
import vpc.util.Ovid;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

/**
 * The <code>HeapParser</code> class can parse and load a heap from a textual file.
 * This functionality is useful for optimizing a pre-existing program, or comparing
 * two heaps in the process of testing the heap initialization code (TIRInterpreter).
 *
 * @author Ben L. Titzer
 */
public class HeapParser {

    protected final Program program;
    protected final VirgilProgram vp;
    protected final Closure closure;

    Map<Integer, Heap.Record> records;
    Map<Type, Heap.Layout> layouts;
    Heap heap;

    public HeapParser(Program p) {
        program = p;
        vp = program.virgil;
        closure = vp.program.closure;
    }

    public Heap parseHeap(BufferedReader input) throws Exception {
        heap = new Heap(program);
        records = Ovid.newMap();
        layouts = Ovid.newMap();
        skipToHeap(input);
        return heap;
    }

    void skipToHeap(BufferedReader input) throws Exception {
        String line;
        while ((line = input.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("heap")) {
                readHeap(input);
                break;
            }
        }
    }

    private void readHeap(BufferedReader input) throws Exception {
        CharacterIterator line;
        while ((line = readLine(input)) != null) {
            if (StringUtil.peekAndEat(line, '}')) break;
            expectKeyword(line, "record");
            StringUtil.skipWhiteSpace(line);
            // read the record number
            StringUtil.expectChar(line, '#');
            int rnum = StringUtil.readDecimalValue(line, 12);
            StringUtil.expectChar(line, ':');
            int sz = StringUtil.readDecimalValue(line, 12);
            StringUtil.expectChar(line, ':');
            // read the layout
            Heap.Record r = getRecord(rnum, line);
            // parse the fields in the record
            parseRecord(r, input);
        }
    }

    private Heap.Record getRecord(int rnum, CharacterIterator line) throws Exception {
        Type tref = parseType(line);
        Heap.Layout layout;
        if (StringUtil.peekAndEat(line, '[')) {
            int len = StringUtil.readIntegerValue(line);
            StringUtil.expectChar(line, '[');
            VirgilArray.IType atype = VirgilArray.getArrayType(program.typeCache, tref);
            layout = VirgilArray.getArrayLayout(heap, atype, len);
            tref = atype;
        } else {
            layout = getLayout(tref);
        }
        return getRecord(tref, layout, rnum);
    }

    private Heap.Layout getLayout(Type t) {
        Heap.Layout layout = layouts.get(t);
        if (layout != null) return layout;
        VirgilComponent compDecl = vp.getComponentDecl(t.toString());
        if (compDecl != null) {
            layout = closure.getLayout(compDecl);
        } else {
            VirgilClass.IType ctype = (VirgilClass.IType)t;
            VirgilClass classDecl = ctype.getDecl();
            if (classDecl != null) {
                layout = closure.getLayout(classDecl);
            }
        }

        if (layout == null) throw Util.failure("unknown layout for type: " + t);
        Heap.Layout nl = heap.newLayout(layout.toString(), layout.type);
        layout.copy(nl);
        layouts.put(t, nl);
        return nl;
    }

    void parseRecord(Heap.Record r, BufferedReader input) throws Exception {
        CharacterIterator line;
        int cntr = 0;
        while ((line = readLine(input)) != null) {
            if (StringUtil.peekAndEat(line, '}')) break;
            parseField(r, line, cntr++);
        }
    }

    void parseField(Heap.Record r, CharacterIterator i, int num) throws Exception {
        expectKeyword(i, "field");
        StringUtil.skipWhiteSpace(i);
        String name = StringUtil.readIdentifier(i);
        StringUtil.expectChar(i, ':');
        Type staticType = parseType(i);
        StringUtil.skipWhiteSpace(i);
        StringUtil.expectChar(i, '=');
        Value val = parseValue(i);

        r.setValue(num, val);
    }

    Type parseType(CharacterIterator i) throws Exception {
        StringUtil.skipWhiteSpace(i);
        Type tn;
        if (StringUtil.peekAndEat(i, '(')) {
            tn = parseType(i);
            StringUtil.expectChar(i, ')');
        } else {
            String ident = StringUtil.readIdentifier(i);
            if ("function".equals(ident)) {
                tn = parseFuncType(i);
            } else if ("raw".equals(ident)) {
                tn = parseRawType(i);
            } else {
                tn = parseClassType(i, ident);
            }
        }
        tn = parseTypeSuffix(i, tn);
        return tn;
    }

    private Type parseClassType(CharacterIterator i, String ident) throws Exception {
        TypeCon tc = program.typeEnv.resolveTypeCon(ident);
        if ( tc == null )
            throw Util.failure("unknown type in heap: "+ident);
        return tc.newType(program.typeCache, parseTypeParams(i));
    }

    private Type[] parseTypeParams(CharacterIterator i) throws Exception {
        Type[] tparams = Type.NOTYPES;
        if (StringUtil.peekAndEat(i, '<')) {
            List<Type> pt = parseTypeList(i, '>');
            StringUtil.expectChar(i, '>');
            tparams = pt.toArray(Type.NOTYPES);
        }
        return tparams;
    }

    private Type parseRawType(CharacterIterator i) throws Exception {
        StringUtil.expectChar(i, '.');
        int w = StringUtil.readDecimalValue(i, 12);
        return PrimRaw.getType(w);
    }

    private Type parseTypeSuffix(CharacterIterator i, Type t) {
        while (StringUtil.peekAndEat(i, '[')) {
            if (StringUtil.peekAndEat(i, ']')) {
                t = VirgilArray.getArrayType(program.typeCache, t);
            } else {
                i.previous();
                return t;
            }
        }
        return t;
    }

    private Type parseFuncType(CharacterIterator i) throws Exception {
        StringUtil.expectChar(i, '(');
        Type ptype = Tuple.toTuple(program.typeCache, parseTypeList(i, ')'));
        Type rtype = parseReturnType(i);
        return Function.TYPECON.newType(program.typeCache, ptype, rtype);
    }

    private Type parseReturnType(CharacterIterator i) throws Exception {
        if (StringUtil.peekAndEat(i, ':')) return parseType(i);
        else return PrimVoid.TYPE;
    }

    private List<Type> parseTypeList(CharacterIterator i, char cend) throws Exception {
        List<Type> pt = Ovid.newLinkedList();
        if (StringUtil.peekAndEat(i, cend)) return pt;
        while (true) {
            pt.add(parseType(i));
            if (StringUtil.peekAndEat(i, cend)) return pt;
            StringUtil.expectChar(i, ',');
        }
    }

    Value parseValue(CharacterIterator i) throws Exception {
        StringUtil.skipWhiteSpace(i);
        if (i.current() == '#') return parseRef(i);

        if (i.current() == '[') return parseDelegate(i);

        Type dynType = parseType(i);
        StringUtil.expectChar(i, ':');

        if (dynType == PrimBool.TYPE) {
            String v = StringUtil.readIdentifier(i);
            return PrimBool.toValue(Boolean.valueOf(v));
        } else if (dynType == PrimInt32.TYPE) {
            int v = StringUtil.readIntegerValue(i);
            return PrimInt32.toValue(v);
        } else if (dynType == PrimChar.TYPE) {
            int v = StringUtil.readIntegerValue(i);
            return PrimChar.toValue((char) v);
        } else if ( dynType instanceof PrimRaw.IType ) {
            Maybe<AbstractToken> mt = PrimRaw.Lexer.consume(i);
            Maybe<PrimRaw.Val> mv = PrimRaw.Converter.convert(mt.getValue());
            return mv.getValue();
        } else if (dynType instanceof Function.IType) {
            return parseDelegate(i);
        }

        throw Util.failure("cannot parse value for type " + dynType);
    }

    private Value parseDelegate(CharacterIterator i) throws Exception {

        StringUtil.expectChar(i, '[');
        Value ref = parseRef(i);
        StringUtil.expectChar(i, ',');
        String ident = StringUtil.readIdentifier(i);
        StringUtil.expectChar(i, ':');
        String meth = StringUtil.readIdentifier(i);
        StringUtil.expectChars(i, "()]");
        CompoundDecl decl = (CompoundDecl) program.getTypeDecl(ident);
        if (decl == null) throw Util.failure("unresolved type: " + ident);
        Method m = decl.getLocalMethod(meth);
        if (m == null) throw Util.failure("unknown method: " + meth);
        return new VirgilDelegate.Val((Heap.Record) ref, m);
    }

    private Value parseRef(CharacterIterator i) throws Exception {
        // going to be a reference
        StringUtil.expectChar(i, '#');
        char ch = i.current();
        if (Character.isDigit(ch)) {
            // reference to a record
            int rnum = StringUtil.readDecimalValue(i, 12);
            StringUtil.expectChar(i, ':');
            return getRecord(rnum, i);
        } else {
            expectKeyword(i, "null");
            return Value.BOTTOM;
        }
    }

    private void expectKeyword(CharacterIterator i, String kw) {
        String str = StringUtil.readIdentifier(i);
        if (!str.equals(kw))
            Util.failure("parse error at " + i.getIndex() + ", expected keyword " + StringUtil.quote(kw));
    }

    private Heap.Record getRecord(Type type, Heap.Layout l, int rnum) {
        Heap.Record r = records.get(rnum);
        if (r == null) {
            r = heap.allocRecord(type, l.size(), rnum);
            heap.addRoot(r);
            records.put(rnum, r);
        }
        return r;
    }

    private CharacterIterator readLine(BufferedReader r) throws IOException {
        String line = r.readLine();
        StringCharacterIterator i = new StringCharacterIterator(line);
        StringUtil.skipWhiteSpace(i);
        return i;
    }
}
