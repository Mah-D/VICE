// @Harness: v2-seman
// @Test: Lvalue correctness
// @Result: NotAnLvalue @ 6:17

class lvalue16 {
    field bar: int = 0;
    field foo: int = bar + bar = 2;
}
