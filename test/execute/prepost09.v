// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=7, 2=7, 3=0

class prepost09_obj {
    field foo: int = 6;
    field bar: int;
}

component prepost09 {
    field foo: prepost09_obj = new prepost09_obj();

    method main(arg: int): int {
	foo.bar = ++foo.foo;
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return foo.bar;
	return 0;
    }
}
