// @Harness: v2-exec
// @Test: pre/post increment options
// @Result: 0=0, 1=15, 2=15, 3=0

class cmpassn10_obj {
    field foo: 16 = 0xffff;
    field bar: 16;
}

component cmpassn10 {
    field foo: cmpassn10_obj = new cmpassn10_obj();

    method main(arg: int): int {
	foo.bar = (foo.foo &= 0xf);
	if ( arg == 1 ) return foo.foo :: int;
	if ( arg == 2 ) return foo.bar :: int;
	return 0;
    }
}
