// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=17, 4=42

class field07b_obj {
    field a: int[] = { 14, 17 };
}

component field07b {
    field foo: field07b_obj = new field07b_obj();

    method main(arg: int): int {
	if ( arg == 1 ) return foo != null and foo.a != null ? 13 : 77;
	if ( arg == 2 ) return foo.a[0];
	if ( arg == 3 ) return foo.a[1];
	return 42;
    }
}
