// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=7, 2=4, 3=0

class cmpassn11_obj {
    field foo: 4 = foo = 0x4;
    field bar: 4;
}

component cmpassn11 {
    field foo: cmpassn11_obj = new cmpassn11_obj();

    method main(arg: int): int {
	foo.foo ^= 0x3;
	if ( arg == 1 ) return foo.foo :: int;
	if ( arg == 2 ) return (foo.foo ^= 0x3) :: int;
	return 0;
    }
}
