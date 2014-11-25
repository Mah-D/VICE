// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class int_raw07 {
    field x: int;
    field a: 32 = m32(x);
    field b: 48 = m48(-109);
    field c: 64 = m64(10909109);
    method m32(x: 32): 32 { return x; }
    method m48(x: 48): 48 { return x; }
    method m64(x: 64): 64 { return x; }
}
