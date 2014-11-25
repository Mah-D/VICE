// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=8, 2=8, 3=0

class cmpassn07_obj {
    field foo: int = 6;
    field bar: int;
}

component cmpassn07 {
    field foo: cmpassn07_obj = new cmpassn07_obj();

    method main(arg: int): int {
	foo.bar = (foo.foo += 2);
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return foo.bar;
	return 0;
    }
}
