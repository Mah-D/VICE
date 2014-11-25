// @Harness: v2-seman
// @Test: typechecking > raw types > and operator
// @Result: TypeMismatch @ 8:16

class raw_and02 {
    field a: 6;
    field b: 7;
    field c: 6 = a & b;
    field d: 5 = a & b;
}
