// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: PASS

class char_raw05 {
    field a: char;
    field b: 8 = m(a);
    field c: 16 = m(a);
    field d: 32 = m(a);
    field e: 64 = m(a);
    method m(x: char): char { return x; }
}
