// @Harness: v2-seman
// @Test: typechecking; int type
// @Result: InvalidLiteral @ 6:20

class int_lit04 {
    field a: int = 2147483648;
}
