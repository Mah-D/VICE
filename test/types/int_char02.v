// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: PASS

class int_char02 {
    field a: int = '0' :: int;
    field b: int = '\n' :: int;
    field c: int = '\367' :: int;
}
