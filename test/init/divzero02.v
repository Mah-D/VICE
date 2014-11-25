// @Harness: v2-init
// @Test: divide by zero exception during initialization
// @Result: DivideByZeroException @ 7:24

component divzero02 {
    field foo: int = 0;
    field bar: int = 1 / foo;
}
