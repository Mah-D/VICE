// @Harness: v2-exec
// @Test: divide by zero exception during initialization
// @Result: 0=71, 1=61, 2=43, 3=DivideByZeroException, 4=221

component rtex_divzero04 {
    field foo: int[] = { 4, 5, 9, 0 };

    method main(arg: int): int {
	local r = 200;
	if ( (arg >= 0) and (arg < foo.length) ) r /= foo[arg];
	return 21 + r;
    }
}
