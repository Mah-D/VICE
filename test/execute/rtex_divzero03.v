// @Harness: v2-exec
// @Test: divide by zero exception during initialization
// @Result: 0=15, 1=22, 3=DivideByZeroException, 4=215, 5=236

component rtex_divzero03 {
    field foo: int = 0;

    method div(a: int, b: int): int {
        return a / b;
    }

    method main(arg: int): int {
	return 1 + div(42, 3 - arg);
    }
}
