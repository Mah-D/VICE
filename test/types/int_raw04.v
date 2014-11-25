// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class int_raw04 {
    field x: int;
    field a: 32 = x :: 32;
    field b: 48 = -109 :: 48;
    field c: 64 = 10909109 :: 64;
}
