// @Harness: v2-init
// @Test: divide by zero exception during initialization
// @Result: DivideByZeroException @ 10:18

component divzero03 {
    field foo: int = 0;
    field bar: int = div(1, foo);

    method div(a: int, b: int): int {
        return a / b;
    }
}
