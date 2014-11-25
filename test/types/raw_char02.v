// @Harness: v2-seman
// @Test: typechecking; char -> raw conversion
// @Result: PASS

class raw_char02 {
    field a: char;
    field b: 8 = a;
    field c: 16 = a;
    field d: 32 = a;
    field e: 48 = a;
    field f: 64 = a;
}
