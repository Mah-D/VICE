// @Harness: v2-exec
// @Test: null exceptions
// @Result: 0=42, 1=NullCheckException, 2=13, 3=NullCheckException, 4=42

class rtex_null09_obj {
    method baz(): int { return 13; }
}

component rtex_null09 {
    field a: rtex_null09_obj;
    field b: rtex_null09_obj = new rtex_null09_obj();
    field c: rtex_null09_obj;

    method main(arg: int): int {
	local m = m42;
	if ( arg == 1 ) m = a.baz;
	if ( arg == 2 ) m = b.baz;
	if ( arg == 3 ) m = c.baz;
	return m();
    }

    method m42(): int {
	return 42;
    }
}
