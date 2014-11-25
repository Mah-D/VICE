// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class int_raw01 {
    field x: int;
    field a: 32 = x;
    field b: 48 = -109;
    field c: 64 = 10909109;
}
