// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=6, 0=0

class prepost11_obj {
    field foo: int = 6;
}

component prepost11 {
    field foo: prepost11_obj = new prepost11_obj();

    method main(arg: int): int {
	foo.foo = foo.foo++;
	if ( arg == 1 ) return foo.foo;
	return 0;
    }
}
