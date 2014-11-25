// @Harness: v2-exec
// @Test: null exceptions
// @Result: 0=42, 1=NullCheckException, 2=NullCheckException, 3=NullCheckException, 4=42

component rtex_null05 {
    field foo: int[];

    method main(arg: int): int {
	if ( arg == 1 ) foo[0] = 13;
	if ( arg == 2 ) foo[1] = 15;
	if ( arg == 3 ) foo[2] = 17;
	return 42;
    }
}
