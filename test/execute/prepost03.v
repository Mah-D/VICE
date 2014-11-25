// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=14, 2=14, 3=0

component prepost03 {
    field foo: int = 13;
    field bar: int;

    method main(arg: int): int {
	bar = ++foo;
	if ( arg == 1 ) return foo;
	if ( arg == 2 ) return bar;
	return 0;
    }
}
