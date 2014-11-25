// @Harness: v2-exec
// @Test: pre/post increment options
// @Result: 0=0, 1=5, 2=5, 3=0

component prepost04 {
    field foo: int = 6;
    field bar: int;
    method main(arg: int): int {
	bar = --foo;
	if ( arg == 1 ) return foo;
	if ( arg == 2 ) return bar;
	return 0;
    }
}
