// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=8, 2=10, 4=0

class cmpassn09_obj {
    field foo: int = 6;
    field bar: int;
}

component cmpassn09 {
    field foo: cmpassn09_obj = new cmpassn09_obj();

    method main(arg: int): int {
	foo.foo += 2;
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return foo.foo += 2;
	return 0;
    }
}
