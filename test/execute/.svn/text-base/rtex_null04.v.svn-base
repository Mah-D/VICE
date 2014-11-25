// @Harness: v2-exec
// @Test: null exceptions
// @Result: 0=42, 1=NullCheckException, 2=13, 3=NullCheckException, 4=NullCheckException, 5=42

class rtex_null04_obj {
    field baz: int = 13;
}

component rtex_null04 {
    field a: rtex_null04_obj;
    field b: rtex_null04_obj = new rtex_null04_obj();
    field c: rtex_null04_obj = null;

    method main(arg: int): int {
	if ( arg == 1 ) return getf(a);
	if ( arg == 2 ) return getf(b);
	if ( arg == 3 ) return getf(c);
	if ( arg == 4 ) return getf(null);
	return 42;
    }

    method getf(a: rtex_null04_obj): int {
	return a.baz;
    }
}
