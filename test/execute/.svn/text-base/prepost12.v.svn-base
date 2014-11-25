// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=7, 2=0

class prepost12_obj {
    field foo: int = 6;
}

component prepost12 {
    field foo: prepost12_obj = new prepost12_obj();

    method main(arg: int): int {
	foo.foo = ++foo.foo;
	if ( arg == 1 ) return foo.foo;
	return 0;
    }
}
