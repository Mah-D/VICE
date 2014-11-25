// @Harness: v2-seman
// @Test: typechecking > raw types, >> operator
// @Result: TypeMismatch @ 8:16

class raw_shr02 {
    field a: 6;
    field b: 7;
    field c: 6 = a >> b;
}
