// @Harness: v2-seman
// @Test: typechecking > raw types > concatenation operator
// @Result: PASS

class raw_concat04 {
    field a: 16;
    field b: 16;
    field c: 32 = a # b;
}
