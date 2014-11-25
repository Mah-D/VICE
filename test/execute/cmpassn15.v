// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=10, 2=42, 3=0

component cmpassn15 {
    field foo: int[] = { 6, 42 };
    field bar: int;

    method main(arg: int): int {
	foo[0] += 4;
	if ( arg == 1 ) return foo[0];
	if ( arg == 2 ) return foo[1];
	return 0;
    }

}
