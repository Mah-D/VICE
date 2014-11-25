// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=13, 1=0, 2=11, 3=13

class default01b_1 {
    field foo: int;
    field bar: int = foo + 11;
}

component default01b {
    field baz: default01b_1 = new default01b_1();

    method main(arg: int): int {
	if ( arg == 1 ) return baz.foo;
	if ( arg == 2 ) return baz.bar;
	return 13;
    }
}
