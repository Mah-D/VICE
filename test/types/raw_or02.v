// @Harness: v2-seman
// @Test: typechecking > raw types > or operator
// @Result: TypeMismatch @ 8:16

class raw_or02 {
    field a: 6;
    field b: 7;
    field c: 7 = a | b;
    field d: 6 = a | b;
}
