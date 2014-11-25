// @Harness: v2-exec
// @Test: pre/post increment options
// @Result: 0=0, 1=6, 2=6, 3=0

class cmpassn08_obj {
    field foo: int = 8;
    field bar: int;
}

component cmpassn08 {
    field foo: cmpassn08_obj = new cmpassn08_obj();

    method main(arg: int): int {
	foo.bar = (foo.foo -= 2);
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return foo.bar;
	return 0;
    }
}
