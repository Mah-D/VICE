// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=6, 2=6, 3=0

component prepost06 {
    method main(arg: int): int {
	local foo = 5;
	local bar = ++foo;
	if ( arg == 1 ) return foo;
	if ( arg == 2 ) return bar;
	return 0;
    }
}
