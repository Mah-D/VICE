// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > auto extension
// @Result: TypeMismatch @ 7:19

class raw_assn05 {
    field a: 7;
    field b: 6 = a;
}
