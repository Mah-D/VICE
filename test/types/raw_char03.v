// @Harness: v2-seman
// @Test: typechecking; char -> raw conversion
// @Result: TypeMismatch @ 7:19

class raw_char03 {
    field a: char;
    field b: 7 = a;
    field c: 16 = a;
    field d: 32 = a;
    field e: 48 = a;
    field f: 64 = a;
}
