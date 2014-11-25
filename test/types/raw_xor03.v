// @Harness: v2-seman
// @Test: typechecking > raw types > xor operator
// @Result: PASS

class raw_xor03 {
    field a: 16;
    field b: 16;
    field c: 16 = a ^ b;
}
