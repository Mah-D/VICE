// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class int_char03 {
    field a: int = m('0');
    field b: int = m('\n');
    field c: int = m('\367');
    method m<T>(x: T): T { return x; }
}
