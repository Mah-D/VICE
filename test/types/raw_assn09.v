// @Harness: v2-seman
// @Test: typechecking > raw types > exact bit sizes > auto extension
// @Result: TypeMismatch @ 7:19

class raw_assn09 {
    field a: 21;
    field b: 20 = a;
}
