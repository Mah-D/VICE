// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=104, 2=101, 3=108, 4=108, 5=111, 6=42

class field05b_obj {
    field a: char[] = "hello";
}

component field05b {
    field foo: field05b_obj = new field05b_obj();

    method main(arg: int): char {
	if ( arg == 1 ) return foo.a[0];
	if ( arg == 2 ) return foo.a[1];
	if ( arg == 3 ) return foo.a[2];
	if ( arg == 4 ) return foo.a[3];
	if ( arg == 5 ) return foo.a[4];
	return '*';
    }
}
