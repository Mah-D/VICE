// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=0, 2=0, 3=42

class default06b_obj {
    field foo: char;
    field bar: char = foo;
}

component default06b {
    field baz: default06b_obj = new default06b_obj();

    method main(arg: int): char {
	if ( arg == 1 ) return baz.foo;
	if ( arg == 2 ) return baz.bar;
	return '*';
    }
}
