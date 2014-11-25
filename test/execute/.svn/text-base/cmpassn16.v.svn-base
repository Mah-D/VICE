// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=10, 2=10, 3=42, 4=0

component cmpassn16 {
    field foo: int[] = { 14, 42 };
    field bar: int;

    method main(arg: int): int {
	bar = foo[0] -= 4;
	if ( arg == 1 ) return foo[0];
	if ( arg == 2 ) return bar;
	if ( arg == 3 ) return foo[1];
	return 0;
    }
}
