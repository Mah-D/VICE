// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > auto extension
// @Result: TypeMismatch @ 7:19

class raw_assn07 {
    field a: 16;
    field b: 15 = a;
}
