// @Harness: v2-init
// @Test: divide by zero exception during initialization
// @Result: DivideByZeroException @ 10:18

component divzero04 {
    field foo: int = 0;
    field bar: int = div(1, 2, 4);

    method div(a: int, b: int, c: int): int {
        return c / (a / b);
    }
}
