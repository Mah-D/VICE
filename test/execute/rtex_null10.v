// @Harness: v2-exec
// @Test: null exceptions
// @Result: 0=42, 1=NullCheckException, 2=13, 3=NullCheckException, 4=42

class rtex_null10_obj {
    method baz(): int { return 13; }
}

component rtex_null10 {
    field a: rtex_null10_obj;
    field b: rtex_null10_obj = new rtex_null10_obj();
    field c: rtex_null10_obj;

    method main(arg: int): int {
	local m = m42;
	if ( arg == 1 ) m = getf(a);
	if ( arg == 2 ) m = getf(b);
	if ( arg == 3 ) m = getf(c);
	return m();
    }

    method m42(): int {
	return 42;
    }

    method getf(o: rtex_null10_obj): function(): int {
	return o.baz;
    }
}
