// @Harness: v2-exec
// @Test: null exceptions
// @Result: 0=42, 1=NullCheckException, 2=NullCheckException, 3=NullCheckException, 4=42

class rtex_null08_obj {
    field baz: int;
}

component rtex_null08 {
    field foo: rtex_null08_obj = null;

    method main(arg: int): int {
	if ( arg == 1 ) foo.baz = 13;
	if ( arg == 2 ) foo.baz = 15;
	if ( arg == 3 ) foo.baz = 17;
	return 42;
    }
}
