// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class int_raw07 {
    field x: int;
    field a: 32 = m(x);
    field b: 48 = m(-109);
    field c: 64 = m(10909109);
    method m<T>(x: T): T { return x; }
}
