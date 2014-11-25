// @Harness: v2-exec
// @Test: pre/post increment options
// @Result: 0=0, 1=5, 2=6, 3=0

class prepost08_obj {
    field foo: int = 6;
    field bar: int;
}

component prepost08 {
    field foo: prepost08_obj = new prepost08_obj();

    method main(arg: int): int {
	foo.bar = foo.foo--;
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return foo.bar;
	return 0;
    }
}
