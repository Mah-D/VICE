// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=0, 1=1, 2=0, 3=0

class field04b_obj {
    field a: bool = true;
    field b: bool = false;
}

component field04b {
    field foo: field04b_obj = new field04b_obj();

    method main(arg: int): bool {
	if ( arg == 1 ) return foo.a;
	if ( arg == 2 ) return foo.b;
	return false;
    }
}
