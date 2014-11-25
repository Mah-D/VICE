// @Harness: v2-exec
// @Test: array rtex_index exceptions
// @Result: 0=42, 1=13, 2=13, 4=BoundsCheckException, 5=42

component rtex_index04 {
    field foo: int[] = new int[16];

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
	if ( arg == 3 ) return getf(15);
	if ( arg == 4 ) return getf(16);
	return 42;
    }
}
