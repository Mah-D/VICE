// @Harness: v2-exec
// @Test: pre/post increment options
// @Result: 0=0, 1=5, 2=5, 3=0

class prepost10_obj {
    field foo: int = 6;
    field bar: int;
}

component prepost10 {
    field foo: prepost10_obj = new prepost10_obj();

    method main(arg: int): int {
	foo.bar = --foo.foo;
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return foo.bar;
	return 0;
    }
}
