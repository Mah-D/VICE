/* Generated By:JavaCC: Do not edit this line. HILParserTokenManager.java */
package vpc.hil.parser;

import cck.parser.SimpleCharStream;

import java.io.IOException;

public class HILParserTokenManager implements HILParserConstants {

    private int jjStopStringLiteralDfa_0(int pos, long active0) {
        switch (pos) {
            case 0:
                if ((active0 & 0x140L) != 0L) return 2;
                if ((active0 & 0xfe000L) != 0L) {
                    jjmatchedKind = 23;
                    return 7;
                }
                return -1;
            case 1:
                if ((active0 & 0x100L) != 0L) return 0;
                if ((active0 & 0xfe000L) != 0L) {
                    jjmatchedKind = 23;
                    jjmatchedPos = 1;
                    return 7;
                }
                return -1;
            case 2:
                if ((active0 & 0xfe000L) != 0L) {
                    jjmatchedKind = 23;
                    jjmatchedPos = 2;
                    return 7;
                }
                return -1;
            case 3:
                if ((active0 & 0xfe000L) != 0L) {
                    jjmatchedKind = 23;
                    jjmatchedPos = 3;
                    return 7;
                }
                return -1;
            case 4:
                if ((active0 & 0x76000L) != 0L) {
                    jjmatchedKind = 23;
                    jjmatchedPos = 4;
                    return 7;
                }
                if ((active0 & 0x88000L) != 0L) return 7;
                return -1;
            case 5:
                if ((active0 & 0x34000L) != 0L) {
                    jjmatchedKind = 23;
                    jjmatchedPos = 5;
                    return 7;
                }
                if ((active0 & 0x42000L) != 0L) return 7;
                return -1;
            case 6:
                if ((active0 & 0x34000L) != 0L) {
                    jjmatchedKind = 23;
                    jjmatchedPos = 6;
                    return 7;
                }
                return -1;
            case 7:
                if ((active0 & 0x10000L) != 0L) return 7;
                if ((active0 & 0x24000L) != 0L) {
                    jjmatchedKind = 23;
                    jjmatchedPos = 7;
                    return 7;
                }
                return -1;
            case 8:
                if ((active0 & 0x4000L) != 0L) return 7;
                if ((active0 & 0x20000L) != 0L) {
                    jjmatchedKind = 23;
                    jjmatchedPos = 8;
                    return 7;
                }
                return -1;
            case 9:
                if ((active0 & 0x20000L) != 0L) {
                    jjmatchedKind = 23;
                    jjmatchedPos = 9;
                    return 7;
                }
                return -1;
            default:
                return -1;
        }
    }

    private int jjStartNfa_0(int pos, long active0) {
        return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
    }

    private int jjStopAtPos(int pos, int kind) {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        return pos + 1;
    }

    private int jjStartNfaWithStates_0(int pos, int kind, int state) {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            return pos + 1;
        }
        return jjMoveNfa_0(state, pos + 1);
    }

    private int jjMoveStringLiteralDfa0_0() {
        switch (curChar) {
            case 40:
                return jjStopAtPos(0, 26);
            case 41:
                return jjStopAtPos(0, 27);
            case 46:
                return jjMoveStringLiteralDfa1_0(0x200000000L);
            case 47:
                return jjMoveStringLiteralDfa1_0(0x140L);
            case 58:
                return jjStopAtPos(0, 35);
            case 59:
                return jjStopAtPos(0, 32);
            case 61:
                return jjStopAtPos(0, 34);
            case 91:
                return jjStopAtPos(0, 30);
            case 93:
                return jjStopAtPos(0, 31);
            case 100:
                return jjMoveStringLiteralDfa1_0(0x2000L);
            case 105:
                return jjMoveStringLiteralDfa1_0(0x24000L);
            case 109:
                return jjMoveStringLiteralDfa1_0(0x8000L);
            case 114:
                return jjMoveStringLiteralDfa1_0(0x50000L);
            case 115:
                return jjMoveStringLiteralDfa1_0(0x80000L);
            case 123:
                return jjStopAtPos(0, 28);
            case 125:
                return jjStopAtPos(0, 29);
            default:
                return jjMoveNfa_0(3, 0);
        }
    }

    private int jjMoveStringLiteralDfa1_0(long active0) {
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(0, active0);
            return 1;
        }
        switch (curChar) {
            case 42:
                if ((active0 & 0x100L) != 0L) return jjStartNfaWithStates_0(1, 8, 0);
                break;
            case 46:
                return jjMoveStringLiteralDfa2_0(active0, 0x200000000L);
            case 47:
                if ((active0 & 0x40L) != 0L) return jjStopAtPos(1, 6);
                break;
            case 97:
                return jjMoveStringLiteralDfa2_0(active0, 0x8000L);
            case 101:
                return jjMoveStringLiteralDfa2_0(active0, 0x52000L);
            case 110:
                return jjMoveStringLiteralDfa2_0(active0, 0x24000L);
            case 112:
                return jjMoveStringLiteralDfa2_0(active0, 0x80000L);
            default:
                break;
        }
        return jjStartNfa_0(0, active0);
    }

    private int jjMoveStringLiteralDfa2_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) return jjStartNfa_0(0, old0);
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(1, active0);
            return 2;
        }
        switch (curChar) {
            case 46:
                if ((active0 & 0x200000000L) != 0L) return jjStopAtPos(2, 33);
                break;
            case 97:
                return jjMoveStringLiteralDfa3_0(active0, 0x80000L);
            case 103:
                return jjMoveStringLiteralDfa3_0(active0, 0x50000L);
            case 115:
                return jjMoveStringLiteralDfa3_0(active0, 0x28000L);
            case 116:
                return jjMoveStringLiteralDfa3_0(active0, 0x4000L);
            case 118:
                return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
            default:
                break;
        }
        return jjStartNfa_0(1, active0);
    }

    private int jjMoveStringLiteralDfa3_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) return jjStartNfa_0(1, old0);
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(2, active0);
            return 3;
        }
        switch (curChar) {
            case 99:
                return jjMoveStringLiteralDfa4_0(active0, 0x80000L);
            case 101:
                return jjMoveStringLiteralDfa4_0(active0, 0x4000L);
            case 105:
                return jjMoveStringLiteralDfa4_0(active0, 0x52000L);
            case 107:
                return jjMoveStringLiteralDfa4_0(active0, 0x8000L);
            case 116:
                return jjMoveStringLiteralDfa4_0(active0, 0x20000L);
            default:
                break;
        }
        return jjStartNfa_0(2, active0);
    }

    private int jjMoveStringLiteralDfa4_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) return jjStartNfa_0(2, old0);
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(3, active0);
            return 4;
        }
        switch (curChar) {
            case 99:
                return jjMoveStringLiteralDfa5_0(active0, 0x2000L);
            case 101:
                if ((active0 & 0x80000L) != 0L) return jjStartNfaWithStates_0(4, 19, 7);
                break;
            case 111:
                return jjMoveStringLiteralDfa5_0(active0, 0x40000L);
            case 114:
                return jjMoveStringLiteralDfa5_0(active0, 0x24000L);
            case 115:
                if ((active0 & 0x8000L) != 0L) return jjStartNfaWithStates_0(4, 15, 7);
                return jjMoveStringLiteralDfa5_0(active0, 0x10000L);
            default:
                break;
        }
        return jjStartNfa_0(3, active0);
    }

    private int jjMoveStringLiteralDfa5_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) return jjStartNfa_0(3, old0);
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(4, active0);
            return 5;
        }
        switch (curChar) {
            case 101:
                if ((active0 & 0x2000L) != 0L) return jjStartNfaWithStates_0(5, 13, 7);
                break;
            case 110:
                if ((active0 & 0x40000L) != 0L) return jjStartNfaWithStates_0(5, 18, 7);
                break;
            case 114:
                return jjMoveStringLiteralDfa6_0(active0, 0x4000L);
            case 116:
                return jjMoveStringLiteralDfa6_0(active0, 0x10000L);
            case 117:
                return jjMoveStringLiteralDfa6_0(active0, 0x20000L);
            default:
                break;
        }
        return jjStartNfa_0(4, active0);
    }

    private int jjMoveStringLiteralDfa6_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) return jjStartNfa_0(4, old0);
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(5, active0);
            return 6;
        }
        switch (curChar) {
            case 99:
                return jjMoveStringLiteralDfa7_0(active0, 0x20000L);
            case 101:
                return jjMoveStringLiteralDfa7_0(active0, 0x10000L);
            case 117:
                return jjMoveStringLiteralDfa7_0(active0, 0x4000L);
            default:
                break;
        }
        return jjStartNfa_0(5, active0);
    }

    private int jjMoveStringLiteralDfa7_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) return jjStartNfa_0(5, old0);
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(6, active0);
            return 7;
        }
        switch (curChar) {
            case 112:
                return jjMoveStringLiteralDfa8_0(active0, 0x4000L);
            case 114:
                if ((active0 & 0x10000L) != 0L) return jjStartNfaWithStates_0(7, 16, 7);
                break;
            case 116:
                return jjMoveStringLiteralDfa8_0(active0, 0x20000L);
            default:
                break;
        }
        return jjStartNfa_0(6, active0);
    }

    private int jjMoveStringLiteralDfa8_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) return jjStartNfa_0(6, old0);
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(7, active0);
            return 8;
        }
        switch (curChar) {
            case 105:
                return jjMoveStringLiteralDfa9_0(active0, 0x20000L);
            case 116:
                if ((active0 & 0x4000L) != 0L) return jjStartNfaWithStates_0(8, 14, 7);
                break;
            default:
                break;
        }
        return jjStartNfa_0(7, active0);
    }

    private int jjMoveStringLiteralDfa9_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) return jjStartNfa_0(7, old0);
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(8, active0);
            return 9;
        }
        switch (curChar) {
            case 111:
                return jjMoveStringLiteralDfa10_0(active0, 0x20000L);
            default:
                break;
        }
        return jjStartNfa_0(8, active0);
    }

    private int jjMoveStringLiteralDfa10_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) return jjStartNfa_0(8, old0);
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(9, active0);
            return 10;
        }
        switch (curChar) {
            case 110:
                if ((active0 & 0x20000L) != 0L) return jjStartNfaWithStates_0(10, 17, 7);
                break;
            default:
                break;
        }
        return jjStartNfa_0(9, active0);
    }

    private void jjCheckNAdd(int state) {
        if (jjrounds[state] != jjround) {
            jjstateSet[jjnewStateCnt++] = state;
            jjrounds[state] = jjround;
        }
    }

    private void jjAddStates(int start, int end) {
        do {
            jjstateSet[jjnewStateCnt++] = jjnextStates[start];
        } while (start++ != end);
    }

    static final long[] jjbitVec0 = {0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL};

    private int jjMoveNfa_0(int startState, int curPos) {
        jjnewStateCnt = 13;
        jjstateSet[0] = startState;
        int kind = 0x7fffffff;
        int i = 1;
        int startsAt = 0;
        while (true) {
            if (++jjround == 0x7fffffff) ReInitRounds();
            if (curChar < 64) {
                long l = 1L << curChar;
                do {
                    switch (jjstateSet[--i]) {
                        case 3:
                            if ((0x3fe000000000000L & l) != 0L) {
                                if (kind > 20) kind = 20;
                                jjCheckNAdd(5);
                            } else if (curChar == 48) {
                                if (kind > 20) kind = 20;
                                jjAddStates(0, 1);
                            } else if (curChar == 47) jjstateSet[jjnewStateCnt++] = 2;
                            break;
                        case 0:
                            if (curChar == 42) jjstateSet[jjnewStateCnt++] = 1;
                            break;
                        case 1:
                            if ((0xffff7fffffffffffL & l) != 0L && kind > 7) kind = 7;
                            break;
                        case 2:
                            if (curChar == 42) jjstateSet[jjnewStateCnt++] = 0;
                            break;
                        case 4:
                            if ((0x3fe000000000000L & l) == 0L) break;
                            if (kind > 20) kind = 20;
                            jjCheckNAdd(5);
                            break;
                        case 5:
                            if ((0x3ff000000000000L & l) == 0L) break;
                            if (kind > 20) kind = 20;
                            jjCheckNAdd(5);
                            break;
                        case 7:
                            if ((0x3ff000000000000L & l) == 0L) break;
                            if (kind > 23) kind = 23;
                            jjstateSet[jjnewStateCnt++] = 7;
                            break;
                        case 8:
                            if (curChar != 48) break;
                            if (kind > 20) kind = 20;
                            jjAddStates(0, 1);
                            break;
                        case 10:
                            if ((0x3ff000000000000L & l) == 0L) break;
                            if (kind > 21) kind = 21;
                            jjstateSet[jjnewStateCnt++] = 10;
                            break;
                        case 12:
                            if ((0x3000000000000L & l) == 0L) break;
                            if (kind > 22) kind = 22;
                            jjstateSet[jjnewStateCnt++] = 12;
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else if (curChar < 128) {
                long l = 1L << (curChar & 077);
                do {
                    switch (jjstateSet[--i]) {
                        case 3:
                        case 7:
                            if ((0x7fffffe87fffffeL & l) == 0L) break;
                            if (kind > 23) kind = 23;
                            jjCheckNAdd(7);
                            break;
                        case 1:
                            if (kind > 7) kind = 7;
                            break;
                        case 9:
                            if ((0x100000001000000L & l) != 0L) jjCheckNAdd(10);
                            break;
                        case 10:
                            if ((0x7e0000007eL & l) == 0L) break;
                            if (kind > 21) kind = 21;
                            jjCheckNAdd(10);
                            break;
                        case 11:
                            if ((0x400000004L & l) != 0L) jjstateSet[jjnewStateCnt++] = 12;
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else {
                int i2 = (curChar & 0xff) >> 6;
                long l2 = 1L << (curChar & 077);
                do {
                    switch (jjstateSet[--i]) {
                        case 1:
                            if ((jjbitVec0[i2] & l2) != 0L && kind > 7) kind = 7;
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff) {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 13 - (jjnewStateCnt = startsAt))) return curPos;
            try {
                curChar = input_stream.readChar();
            } catch (IOException e) {
                return curPos;
            }
        }
    }

    private int jjMoveStringLiteralDfa0_3() {
        switch (curChar) {
            case 42:
                return jjMoveStringLiteralDfa1_3(0x800L);
            default:
                return 1;
        }
    }

    private int jjMoveStringLiteralDfa1_3(long active0) {
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            return 1;
        }
        switch (curChar) {
            case 47:
                if ((active0 & 0x800L) != 0L) return jjStopAtPos(1, 11);
                break;
            default:
                return 2;
        }
        return 2;
    }

    private int jjMoveStringLiteralDfa0_1() {
        return jjMoveNfa_1(0, 0);
    }

    private int jjMoveNfa_1(int startState, int curPos) {
        jjnewStateCnt = 3;
        jjstateSet[0] = startState;
        int kind = 0x7fffffff;
        int i = 1;
        int startsAt = 0;
        while (true) {
            if (++jjround == 0x7fffffff) ReInitRounds();
            if (curChar < 64) {
                long l = 1L << curChar;
                do {
                    switch (jjstateSet[--i]) {
                        case 0:
                            if ((0x2400L & l) != 0L) {
                                if (kind > 9) kind = 9;
                            }
                            if (curChar == 13) jjstateSet[jjnewStateCnt++] = 1;
                            break;
                        case 1:
                            if (curChar == 10 && kind > 9) kind = 9;
                            break;
                        case 2:
                            if (curChar == 13) jjstateSet[jjnewStateCnt++] = 1;
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            } else do {
                switch (jjstateSet[--i]) {
                    default:
                        break;
                }
            } while (i != startsAt);
            if (kind != 0x7fffffff) {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt))) return curPos;
            try {
                curChar = input_stream.readChar();
            } catch (IOException e) {
                return curPos;
            }
        }
    }

    private int jjMoveStringLiteralDfa0_2() {
        switch (curChar) {
            case 42:
                return jjMoveStringLiteralDfa1_2(0x400L);
            default:
                return 1;
        }
    }

    private int jjMoveStringLiteralDfa1_2(long active0) {
        try {
            curChar = input_stream.readChar();
        } catch (IOException e) {
            return 1;
        }
        switch (curChar) {
            case 47:
                if ((active0 & 0x400L) != 0L) return jjStopAtPos(1, 10);
                break;
            default:
                return 2;
        }
        return 2;
    }

    static final int[] jjnextStates = {9, 11,};
    public static final String[] jjstrLiteralImages = {"", null, null, null, null, null, null, null, null, null, null, null, null, "\144\145\166\151\143\145", "\151\156\164\145\162\162\165\160\164", "\155\141\163\153\163", "\162\145\147\151\163\164\145\162", "\151\156\163\164\162\165\143\164\151\157\156", "\162\145\147\151\157\156", "\163\160\141\143\145", null, null, null, null, null, null, "\50", "\51", "\173", "\175", "\133", "\135", "\73", "\56\56\56", "\75", "\72",};
    public static final int[] jjnewLexState = {-1, -1, -1, -1, -1, -1, 1, 2, 3, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,};
    static final long[] jjtoToken = {0xffcffe001L,};
    static final long[] jjtoSkip = {0xe3eL,};
    static final long[] jjtoSpecial = {0xe00L,};
    protected SimpleCharStream input_stream;
    private final int[] jjrounds = new int[13];
    private final int[] jjstateSet = new int[26];
    StringBuffer image;
    int jjimageLen;
    int lengthOfMatch;
    protected char curChar;

    public HILParserTokenManager(SimpleCharStream stream) {
        input_stream = stream;
    }

    private void ReInitRounds() {
        jjround = 0x80000001;
        for (int i = 13; i-- > 0;)
            jjrounds[i] = 0x80000000;
    }

    protected Token jjFillToken() {
        Token t = Token.newToken(jjmatchedKind);
        t.kind = jjmatchedKind;
        String im = jjstrLiteralImages[jjmatchedKind];
        t.image = im == null ? input_stream.GetImage() : im;
        t.beginLine = input_stream.getBeginLine();
        t.beginColumn = input_stream.getBeginColumn();
        t.endLine = input_stream.getEndLine();
        t.endColumn = input_stream.getEndColumn();
        return t;
    }

    int curLexState = 0;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;

    public Token getNextToken() {
        Token specialToken = null;
        int curPos = 0;

        EOFLoop:
        while (true) {
            Token matchedToken;
            try {
                curChar = input_stream.BeginToken();
            } catch (IOException e) {
                jjmatchedKind = 0;
                matchedToken = jjFillToken();
                return matchedToken;
            }
            image = null;
            jjimageLen = 0;

            while (true) {
                switch (curLexState) {
                    case 0:
                        try {
                            input_stream.backup(0);
                            while (curChar <= 32 && (0x100003600L & 1L << curChar) != 0L)
                                curChar = input_stream.BeginToken();
                        } catch (IOException e1) {
                            continue EOFLoop;
                        }
                        jjmatchedKind = 0x7fffffff;
                        jjmatchedPos = 0;
                        curPos = jjMoveStringLiteralDfa0_0();
                        break;
                    case 1:
                        jjmatchedKind = 0x7fffffff;
                        jjmatchedPos = 0;
                        curPos = jjMoveStringLiteralDfa0_1();
                        if (jjmatchedPos == 0 && jjmatchedKind > 12) {
                            jjmatchedKind = 12;
                        }
                        break;
                    case 2:
                        jjmatchedKind = 0x7fffffff;
                        jjmatchedPos = 0;
                        curPos = jjMoveStringLiteralDfa0_2();
                        if (jjmatchedPos == 0 && jjmatchedKind > 12) {
                            jjmatchedKind = 12;
                        }
                        break;
                    case 3:
                        jjmatchedKind = 0x7fffffff;
                        jjmatchedPos = 0;
                        curPos = jjMoveStringLiteralDfa0_3();
                        if (jjmatchedPos == 0 && jjmatchedKind > 12) {
                            jjmatchedKind = 12;
                        }
                        break;
                }
                if (jjmatchedKind != 0x7fffffff) {
                    if (jjmatchedPos + 1 < curPos) input_stream.backup(curPos - jjmatchedPos - 1);
                    if ((jjtoToken[jjmatchedKind >> 6] & 1L << (jjmatchedKind & 077)) != 0L) {
                        matchedToken = jjFillToken();
                        if (jjnewLexState[jjmatchedKind] != -1) curLexState = jjnewLexState[jjmatchedKind];
                        return matchedToken;
                    } else if ((jjtoSkip[jjmatchedKind >> 6] & 1L << (jjmatchedKind & 077)) != 0L) {
                        if ((jjtoSpecial[jjmatchedKind >> 6] & 1L << (jjmatchedKind & 077)) == 0L)
                            SkipLexicalActions();
                        else {
                            matchedToken = jjFillToken();
                            if (specialToken == null) specialToken = matchedToken;
                            else {
                                specialToken = specialToken.next = matchedToken;
                            }
                            SkipLexicalActions();
                        }
                        if (jjnewLexState[jjmatchedKind] != -1) curLexState = jjnewLexState[jjmatchedKind];
                        continue EOFLoop;
                    }
                    MoreLexicalActions();
                    if (jjnewLexState[jjmatchedKind] != -1) curLexState = jjnewLexState[jjmatchedKind];
                    curPos = 0;
                    jjmatchedKind = 0x7fffffff;
                    try {
                        curChar = input_stream.readChar();
                        continue;
                    } catch (IOException e1) {
                    }
                }
                int error_line = input_stream.getEndLine();
                int error_column = input_stream.getEndColumn();
                String error_after = null;
                boolean EOFSeen = false;
                try {
                    input_stream.readChar();
                    input_stream.backup(1);
                } catch (IOException e1) {
                    EOFSeen = true;
                    error_after = curPos <= 1 ? "" : input_stream.GetImage();
                    if (curChar == '\n' || curChar == '\r') {
                        error_line++;
                        error_column = 0;
                    } else error_column++;
                }
                if (!EOFSeen) {
                    input_stream.backup(1);
                    error_after = curPos <= 1 ? "" : input_stream.GetImage();
                }
                throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
            }
        }
    }

    void SkipLexicalActions() {
        switch (jjmatchedKind) {
            default:
                break;
        }
    }

    void MoreLexicalActions() {
        jjimageLen += lengthOfMatch = jjmatchedPos + 1;
        switch (jjmatchedKind) {
            case 7:
                if (image == null) image = new StringBuffer();
                image.append(input_stream.GetSuffix(jjimageLen));
                jjimageLen = 0;
                input_stream.backup(1);
                break;
            default:
                break;
        }
    }
}