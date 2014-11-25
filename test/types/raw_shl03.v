// @Harness: v2-seman
// @Test: typechecking > raw types, << operator
// @Result: PASS

class raw_shl03 {
    field a: 16;
    field b: int;
    field c: 16 = a << b;
}
