// @Harness: v2-seman
// @Test: typechecking > raw types > concatenation operator
// @Result: PASS

class raw_concat01 {
    field a: 1;
    field b: 2;
    field c: 3 = a # b;
}
