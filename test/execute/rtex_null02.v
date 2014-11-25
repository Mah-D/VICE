// @Harness: v2-exec
// @Test: null exceptions
// @Result: 0=42, 1=NullCheckException, 2=NullCheckException, 3=NullCheckException, 4=42

component rtex_null02 {
    field foo: int[] = null;

    method main(arg: int): int {
	if ( arg == 1 ) return foo[0];
	if ( arg == 2 ) return foo[1];
	if ( arg == 3 ) return foo[2];
	return 42;
    }
}
