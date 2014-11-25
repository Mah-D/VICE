// @Harness: v2-exec
// @Test: divide by zero exception during initialization
// @Result: 0=42, 1=DivideByZeroException, 2=DivideByZeroException, 3=DivideByZeroException, 4=42

component rtex_divzero02 {
    field foo: int = 0;

    method div(n: int, d: int): int {
	return n / d;
    }

    method main(arg: int): int {
	local r = 21;
	if ( arg == 1 ) r = div(1, 0);
	if ( arg == 2 ) r = div(1, foo);
	if ( arg == 3 ) r = div(1, r - 21);
	return r + 21;
    }
}
