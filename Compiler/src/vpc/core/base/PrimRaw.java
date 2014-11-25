/**
 * Copyright (c) 2006, Regents of the University of California
 * See the file "license.txt" for details.
 *
 * Created May 25, 2006
 */
package vpc.core.base;

import cck.parser.AbstractToken;
import cck.text.CharUtil;
import cck.text.StringUtil;
import cck.util.Arithmetic;
import cck.util.Util;
import vpc.core.Value;
import vpc.core.types.Type;
import vpc.dart.symc.SCValue;
import vpc.tir.expr.Operator;
import vpc.util.Maybe;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * The <code>PrimRaw</code> class encapsulates the concepts and operators relating
 * to primitive values that represent raw bits. These values can be any size
 * between 1 and 64 bits and include such operations as AND, OR, XOR, SHIFT,
 * select bit range, convert to integer, etc.
 *
 * @author Ben L. Titzer
 */
public class PrimRaw {

    protected static final int TYPE_CACHE_SIZE = 64;

    private static IType[] typeCache = new IType[TYPE_CACHE_SIZE];

    static {
        for (int cntr = 0; cntr < TYPE_CACHE_SIZE; cntr++) {
            typeCache[cntr] = new IType(cntr + 1);
        }
    }

    public abstract static class FixedWidthBinOp extends Operator.Op2 {
        public final IType resultBitType;

        protected FixedWidthBinOp(int w, int r) {
            super(getType(w), getType(w), getType(r));
            resultBitType = getType(w);
        }

        public Value apply2(Value v1, Value v2) throws Operator.Exception {
            return new Val(resultBitType, apply(fromValue(v1), fromValue(v2)));
        }

        public abstract long apply(long v1, long v2);
    }

    /**
     * The <code>PrimRaw.XOR</code> class represents an exclusive-or on raw bits.
     */
    public static class XOR extends FixedWidthBinOp {
        public XOR(int w) {
            super(w, w);
        }

        public long apply(long v1, long v2) {
            return v1 ^ v2;
        }
    }

    /**
     * The <code>PrimRaw.AND</code> class represents a bitwise-and on raw bits.
     */
    public static class AND extends FixedWidthBinOp {
        public AND(int w, int r) {
            super(w, r);
        }

        public long apply(long v1, long v2) {
            return v1 & v2;
        }
    }

    /**
     * The <code>PrimRaw.OR</code> class represents a bitwise-or on raw bits.
     */
    public static class OR extends FixedWidthBinOp {
        public OR(int w) {
            super(w, w);
        }

        public long apply(long v1, long v2) {
            return v1 | v2;
        }
    }

    /**
     * The <code>PrimRaw.SHL</code> class represents a bitwise shift left within a window.
     */
    public static class SHL extends Operator.Op2 {
        public final IType resultBitType;
        public final long mask;

        public SHL(int w) {
            super(getType(w), PrimInt32.TYPE, getType(w));
            resultBitType = getType(w);
            mask = Arithmetic.getLongBitRangeMask(0, w - 1);
        }

        public Value apply2(Value v1, Value v2) throws Operator.Exception {
            return new Val(resultBitType, apply(fromValue(v1), PrimInt32.fromValue(v2)));
        }

        public long apply(long v1, int v2) {
            return mask & v1 << v2;
        }
    }

    /**
     * The <code>PrimRaw.SHR</code> class represents a bitwise shift right.
     */
    public static class SHR extends Operator.Op2 {
        public final IType resultBitType;
        public final long mask;

        public SHR(int w) {
            super(getType(w), PrimInt32.TYPE, getType(w));
            resultBitType = getType(w);
            mask = Arithmetic.getLongBitRangeMask(0, w - 1);
        }

        public Value apply2(Value v1, Value v2) throws Operator.Exception {
            return new Val(resultBitType, apply(fromValue(v1), PrimInt32.fromValue(v2)));
        }

        public long apply(long v1, int v2) {
            return mask & v1 >> v2;
        }
    }

    /**
     * The <code>PrimRaw.Concat</code> class represents the concatenation operation,
     * which concatenates two ranges of bits.
     */
    public static class Concat extends Operator.Op2 {
        public final int shift;
        public final long mask;
        public final IType resultBitWidth;

        public Concat(IType lw, IType rw) {
            super(lw, rw, getSumType(lw, rw));
            shift = rw.width;
            mask = Arithmetic.getLongBitRangeMask(0, shift - 1);
            resultBitWidth = getSumType(lw, rw);
        }

        public Value apply2(Value v1, Value v2) {
            long lv = fromValue(v1);
            long rv = fromValue(v2);
            // mask may not be strictly necessary if rv value typechecks
            return new Val(resultBitWidth, lv << shift | rv & mask);
        }
    }

    /**
     * The <code>PrimRaw.GetBit</code> class represents the bit read operation, where
     * the program can read a single bit from a bit value.
     */
    public static class GetBit extends Operator.Op2 implements Operator.Location {
        protected final int width;
        protected final IType resultBitWidth;

        public GetBit(IType lw) {
            super(lw, PrimInt32.TYPE, getType(1));
            width = lw.width;
            resultBitWidth = getType(1);
        }

        public Value apply2(Value v1, Value v2) {
            long lv = fromValue(v1);
            int bit = PrimInt32.fromValue(v2);
            long result = 0;
            if (bit < width && bit >= 0) result = lv >> bit & 1;
            return new Val(resultBitWidth, result);
        }

        public Operator getOperator() {
            return this;
        }

        public Operator setOperator() {
            return new SetBit(resultBitWidth);
        }
    }

    /**
     * The <code>PrimRaw.SetBit</code> class represents the bit write operation, where
     * the program can read a single bit from a bit value.
     */
    public static class SetBit extends Operator.Op3 {
        protected final int width;
        protected final IType resultBitWidth;

        public SetBit(IType lw) {
            super(lw, PrimInt32.TYPE, getType(1), getType(1));
            width = lw.width;
            resultBitWidth = getType(1);
        }

        public Value apply3(Value v1, Value v2, Value v3) {
            long lv = fromValue(v1);
            int bit = PrimInt32.fromValue(v2);
            long rv = fromValue(v3);
            if (bit < width && bit >= 0) {
                long mask = 1L << bit;
                lv = lv & ~mask | rv << bit & mask;
            }
            return new Val(resultBitWidth, lv);
        }
    }

    /**
     * The <code>PrimRaw.GetRange</code> class represents the range read operation, where
     * the program can read a fixed range of bits from a larger bit value.
     */
    public static class GetRange extends Operator.Op1 {
        protected final IType resultBitWidth;
        protected final int low;
        protected final int high;
        protected final long mask;

        public GetRange(IType lw, int high, int low) {
            super(lw, getRangeType(high, low));
            resultBitWidth = getRangeType(high, low);
            this.low = low;
            this.high = high;
            mask = Arithmetic.getLongBitRangeMask(low, high);
        }

        public Value apply1(Value v1) {
            long lv = fromValue(v1);
            return new Val(resultBitWidth, (lv & mask) >>> low);
        }
    }

    /**
     * The <code>PrimRaw.Complement</code> class represents the complement,
     * which computes the one's complement (i.e. flips all bits).
     */
    public static class Complement extends Operator.Op1 {
        protected final IType resultBitWidth;
        protected final long mask;

        public Complement(int w) {
            this(getType(w));
        }

        public Complement(IType lw) {
            super(lw, lw);
            resultBitWidth = lw;
            mask = Arithmetic.getLongBitRangeMask(0, lw.width - 1);
        }

        public Value apply1(Value v1) {
            long lv = fromValue(v1);
            return new Val(resultBitWidth, ~lv & mask);
        }
    }

    /**
     * The <code>PrimRaw.SetRange</code> class represents the range write operation, where
     * the program can read update a fixed range of bits within a value.
     */
    public static class SetRange extends Operator.Op2 {
        protected final IType resultBitWidth;
        protected final int low;
        protected final int high;
        protected final long mask;

        public SetRange(IType lw, int high, int low) {
            super(lw, getRangeType(high, low), getRangeType(high, low));
            resultBitWidth = getRangeType(high, low);
            this.low = low;
            this.high = high;
            mask = Arithmetic.getLongBitRangeMask(low, high);
        }

        public Value apply2(Value v1, Value v2) {
            long lv = fromValue(v1);
            long rv = fromValue(v2) << low;
            return new Val(resultBitWidth, lv & ~mask | rv & mask);
        }
    }


    public static IType getType(int w) {
        if (w <= 0 || w > TYPE_CACHE_SIZE) throw Util.failure("No raw type for width: " + w);
        return typeCache[w - 1];
    }

    protected static IType getSumType(IType lw, IType rw) {
        return getType(lw.width + rw.width);
    }

    public static IType getMinType(IType t1, IType t2) {
        return t1.width < t2.width ? t1 : t2;
    }

    public static IType getMaxType(IType t1, IType t2) {
        return t1.width > t2.width ? t1 : t2;
    }

    protected static IType getRangeType(int low, int high) {
        return getType(high - low + 1);
    }

    public static class IType extends Type.Simple {

        public final int width;

        public IType(int w) {
            super(String.valueOf(w));
            width = w;
        }

    }

    /**
     * The <code>PrimRaw.Val</code> class represents a collection of bits between
     * 1 and 64 bits long.
     */
    public static class Val extends Value {
        public final IType type;
        public final int width;
        public final long value;

        public Val(IType t, long v) {
            super();
            type = t;
            value = v;
            width = t.width;
        }

        public Type getType() {
            return type;
        }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (o == Value.BOTTOM ) return value == 0; // 0 == bottom
            return o instanceof Val && ((Val) o).value == value;
        }

        public String toString() {
            return "raw." + width + ":" + StringUtil.to0xHex(value, (width + 3) / 4);
        }
    }

    public static long fromValue(Value v) {
        if ( v == Value.BOTTOM ) return 0; // null (bottom) means the zero long
        if (v instanceof Val) // return raw bits value
            return ((Val) v).value;
        if (v instanceof PrimInt32.Val) // convert integer to bits value
            return ((PrimInt32.Val) v).value & 0xFFFFFFFFL;
        if (v instanceof PrimChar.Val) // convert character to bits
            return ((PrimChar.Val) v).value;
        if (v instanceof PrimBool.Val) // convert character to bits
            return ((PrimBool.Val) v).value ? 1 : 0;
        throw Util.failure("Value of type " + v.getType() + " cannot be converted to raw bits");
    }

    public static Value toValue(int w, long v) {
        return new Val(getType(w), v);
    }

    public static class Lexer {
        public static Maybe<AbstractToken> consume(CharacterIterator i) {
            StringBuffer buf = new StringBuffer(24);
            if (StringUtil.peekAndEat(i, "0x") || StringUtil.peekAndEat(i, "0X")) {
                buf.append("0x");
                return consumeHex(i, buf, 16);
            } else if (StringUtil.peekAndEat(i, "0b") || StringUtil.peekAndEat(i, "0B")) {
                buf.append("0b");
                return consumeBin(i, buf, 64);
            } else if (StringUtil.peekAndEat(i, '0')) {
                buf.append("0");
                return consumeOct(i, buf, 21);
            }
            else return new Maybe<AbstractToken>();
        }

        public static Maybe<AbstractToken> consumeHex(CharacterIterator i, StringBuffer buf, int len) {
            // allow at most len+1 (including DONE) digits for hexadecimal literals
            for (int cntr = 0; cntr < len + 1; cntr++, i.next()) {
                char ch = i.current();
                if (ch == CharacterIterator.DONE || !CharUtil.isHexDigit(ch))
                    return tokenize(buf);
                buf.append(ch);
            }
            return tooLarge("hexadecimal");
        }

        public static Maybe<AbstractToken> consumeBin(CharacterIterator i, StringBuffer buf, int len) {
            // allow at most len digits for hexadecimal literals
            for (int cntr = 0; cntr < len + 1; cntr++, i.next()) {
                char ch = i.current();
                if (ch == CharacterIterator.DONE || !CharUtil.isBinDigit(ch))
                    return tokenize(buf);
                buf.append(ch);
            }
            return tooLarge("binary");
        }

        public static Maybe<AbstractToken> consumeOct(CharacterIterator i, StringBuffer buf, int len) {
            // allow at most len digits for hexadecimal literals
            for (int cntr = 0; cntr < len + 1; cntr++, i.next()) {
                char ch = i.current();
                if (ch == CharacterIterator.DONE || !CharUtil.isOctDigit(ch))
                    return tokenize(buf);
                buf.append(ch);
            }
            return tooLarge("octal");
        }

        public static Maybe<AbstractToken> tokenize(StringBuffer buf) {
            return new Maybe<AbstractToken>(AbstractToken.newToken(buf.toString(), null));
        }

        public static Maybe<AbstractToken> tooLarge(String fmt) {
            return new Maybe<AbstractToken>(new Error(fmt + " constant too large"));
        }

    }

    public static class Converter {

        public static Maybe<Val> convert(AbstractToken tok) {
            return convert(new StringCharacterIterator(tok.image));
        }

        public static Maybe<Val> convert(CharacterIterator i) {
            if (peekAndEatHex(i)) return convertHex(i, 16);
            else if (peekAndEatBin(i)) return convertBin(i, 64);
            else if (peekAndEatOct(i)) return convertOct(i, 21);
            else return new Maybe<Val>();
        }

        public static Maybe<Val> convertHex(CharacterIterator i, int len) {
            long accum = 0;
            // allow at most len+1 (including DONE) digits for hexadecimal literals
            for (int cntr = 0; cntr < len + 1; cntr++, i.next()) {
                char ch = i.current();
                if (ch == CharacterIterator.DONE) return toMaybeValue(cntr * 4, accum);
                if (!CharUtil.isHexDigit(ch)) return invalidDigit("hexadecimal", ch);
                accum = accum << 4 | CharUtil.hexValueOf(ch);
            }
            return tooLarge("hexadecimal");
        }

        public static Maybe<Val> convertBin(CharacterIterator i, int len) {
            long accum = 0;
            // allow at most len digits for hexadecimal literals
            for (int cntr = 0; cntr < len + 1; cntr++, i.next()) {
                char ch = i.current();
                if (ch == CharacterIterator.DONE) return toMaybeValue(cntr, accum);
                if (!CharUtil.isBinDigit(ch)) return invalidDigit("binary", ch);
                accum = accum << 1 | CharUtil.binValueOf(ch);
            }
            return tooLarge("binary");
        }

        public static Maybe<Val> convertOct(CharacterIterator i, int len) {
            long accum = 0;
            // allow at most len digits for hexadecimal literals
            for (int cntr = 0; cntr < len + 1; cntr++, i.next()) {
                char ch = i.current();
                if (ch == CharacterIterator.DONE) return toMaybeValue(cntr * 3, accum);
                if (!CharUtil.isOctDigit(ch)) return invalidDigit("octal", ch);
                accum = accum << 3 | CharUtil.octValueOf(ch);
            }
            return tooLarge("octal");
        }

        private static Maybe<Val> toMaybeValue(int bitWidth, long accum) {
            return new Maybe<Val>(new Val(getType(bitWidth), accum));
        }

        private static Maybe<Val> invalidDigit(String fmt, char ch) {
            return new Maybe<Val>(new Error("invalid " + fmt + " digit: " + ch));
        }

        private static Maybe<Val> tooLarge(String fmt) {
            return new Maybe<Val>(new Error(fmt + " constant too large"));
        }
    }

    public static boolean peekAndEatHex(CharacterIterator i) {
        return StringUtil.peekAndEat(i, "0x") || StringUtil.peekAndEat(i, "0X");
    }

    public static boolean peekAndEatBin(CharacterIterator i) {
        return StringUtil.peekAndEat(i, "0b") || StringUtil.peekAndEat(i, "0B");
    }

    public static boolean peekAndEatOct(CharacterIterator i) {
        return StringUtil.peekAndEat(i, '0');
    }

}
