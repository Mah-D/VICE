// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: TypeMismatch @ 7:21

class char_raw02 {
    field a: char;
    field b: 7 = a;
}
