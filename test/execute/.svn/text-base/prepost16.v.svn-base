// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=7, 2=42, 3=7, 4=0

component prepost16 {
    field foo: int[] = { 6, 42 };
    field bar: int;

    method main(arg: int): int {
	bar = ++foo[0];
	if ( arg == 1 ) return foo[0];
	if ( arg == 2 ) return foo[1];
	if ( arg == 3 ) return bar;
	return 0;
    }

}
