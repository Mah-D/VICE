// @Harness: v2-exec
// @Test: array rtex_index exceptions
// @Result: 0=42, 1=BoundsCheckException, 2=13, 3=14, 4=BoundsCheckException, 5=42

component rtex_index03 {
    field foo: int[] = { 13, 14 };

    method main(arg: int): int {
	if ( arg == 1 ) return foo[-1];
	if ( arg == 2 ) return foo[0];
	if ( arg == 3 ) return foo[1];
	if ( arg == 4 ) return foo[2];
	return 42;
    }
}
