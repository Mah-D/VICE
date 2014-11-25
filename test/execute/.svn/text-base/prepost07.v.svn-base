// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=7, 2=6, 3=0

class prepost07_obj {
    field foo: int = 6;
    field bar: int;
}

component prepost07 {
    field foo: prepost07_obj = new prepost07_obj();

    method main(arg: int): int {
	foo.bar = foo.foo++;
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return foo.bar;
	return 0;
    }
}
