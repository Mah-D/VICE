// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > auto extension
// @Result: TypeMismatch @ 7:19

class raw_assn03 {
    field a: 3;
    field b: 2 = a;
}
