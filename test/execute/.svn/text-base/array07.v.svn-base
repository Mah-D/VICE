// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=0, 2=0, 3=0, 4=42

component array07 {
    field a: int[] =  new int[1];
    field b: int[] =  new int[2];

    method main(arg: int): int {
	if ( arg == 1 ) return a[0];
	if ( arg == 2 ) return b[0];
	if ( arg == 3 ) return b[1];
	return 42;
    }
}
