// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=6, 2=6, 3=0 

class cmpassn12_obj {
    field foo: int = 13;
    field bar: int = foo %= 7;
}

component cmpassn12 {
    field foo: cmpassn12_obj = new cmpassn12_obj();

    method main(arg: int): int {
	foo.bar = (foo.foo %= 7);
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return foo.bar;
	return 0;
    }
}
