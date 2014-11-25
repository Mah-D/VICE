// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=104, 2=101, 3=42

class field06b_obj {
    field a: char = 'h';
    field b: char = 'e';
}

component field06b {
    field foo: field06b_obj = new field06b_obj();

    method main(arg: int): char {
	if ( arg == 1 ) return foo.a;
	if ( arg == 2 ) return foo.b;
	return '*';
    }
}
