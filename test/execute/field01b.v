// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=17, 4=42

class field01b_obj {
    field a: int = 14;
    field b: int = 17;
}

component field01b {
    field foo: field01b_obj = new field01b_obj();

    method main(arg: int): int {
	if ( arg == 1 ) return foo != null ? 13 : 77;
	if ( arg == 2 ) return foo.a;
	if ( arg == 3 ) return foo.b;
	return 42;
    }
}
