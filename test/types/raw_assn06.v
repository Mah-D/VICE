// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > auto extension
// @Result: TypeMismatch @ 7:19

class raw_assn06 {
    field a: 11;
    field b: 8 = a;
}
