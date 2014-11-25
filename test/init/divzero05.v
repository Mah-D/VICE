// @Harness: v2-init
// @Test: divide by zero exception during initialization
// @Result: DivideByZeroException @ 6:24

component divzero05 {
    field foo: int = 1 % 0;
}
