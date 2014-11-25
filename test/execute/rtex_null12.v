// @Harness: v2-exec
// @Test: null exceptions
// @Result: 0=42, 1=NullCheckException, 2=13, 3=14, 4=NullCheckException, 5=42

class rtex_null12_obj {
    field foo: int[];
    constructor(i: int[]) { foo = i; }
}

component rtex_null12 {
    field i: int[] = { 13, 14 };
    field a: rtex_null12_obj = new rtex_null12_obj(null);
    field b: rtex_null12_obj = new rtex_null12_obj(i);
    field c: rtex_null12_obj = new rtex_null12_obj(null);

    method main(arg: int): int {
	if ( arg == 1 ) return a.foo[0];
	if ( arg == 2 ) return b.foo[0];
	if ( arg == 3 ) return b.foo[1];
	if ( arg == 4 ) return c.foo[0];
	return 42;
    }
}
