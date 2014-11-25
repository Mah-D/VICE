// @Harness: v2-seman
// @Test: typechecking > raw types > xor operator
// @Result: TypeMismatch @ 8:16

class raw_xor02 {
    field a: 6;
    field b: 7;
    field c: 6 = a^ b;
}
