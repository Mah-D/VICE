// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=6, 2=5, 3=0

component prepost01 {
    field foo: int = 5;
    field bar: int;

    method main(arg: int): int {
	bar = foo++;
	if ( arg == 1 ) return foo;
	if ( arg == 2 ) return bar;
	return 0;
    }
}
