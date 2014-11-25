// @Harness: v2-seman
// @Test: typechecking > raw types > or operator
// @Result: PASS

class raw_or03 {
    field a: 16;
    field b: 16;
    field c: 16 = a | b;
    field d: 16 = b | a;
}
