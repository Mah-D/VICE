// @Harness: v2-seman
// @Test: typechecking; char type
// @Result: PASS

class char_int05 {
    field a: int;
    field b: char = m(a :: char);
    method m<T>(x: T): T { return x; }
}
