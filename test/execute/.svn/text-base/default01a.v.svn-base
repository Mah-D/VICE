// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=13, 1=0, 2=11, 3=13

component default01a {
    field foo: int;
    field bar: int = foo + 11;

    method main(arg: int): int {
	if ( arg == 1 ) return foo;
	if ( arg == 2 ) return bar;
	return 13;
    }
}
