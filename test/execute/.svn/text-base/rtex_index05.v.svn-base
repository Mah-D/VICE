// @Harness: v2-exec
// @Test: array rtex_index exceptions
// @Result: 0=42, 1=13, 2=13, 3=13, 4=BoundsCheckException, 5=BoundsCheckException, 6=BoundsCheckException, 7=42

component rtex_index05 {
    field foo: int[] = new int[256];

    constructor() {
	local i = 0;
	for ( i = 0; i < foo.length; i++ ) foo[i] = 13;
    }

    method getf(i: int): int {
	return foo[i];
    }

    method main(arg: int): int {
	if ( arg == 1 ) return getf(0);
	if ( arg == 2 ) return getf(1);
	if ( arg == 3 ) return getf(255);
	if ( arg == 4 ) return getf(256);
	if ( arg == 5 ) return getf(257);
	if ( arg == 6 ) return getf(65536);
	return 42;
    }
}
