// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=6, 3=7, 4=42

component field07a {
    field a: int[] = { 6, 7 };

    method main(arg: int): int {
	if ( arg == 1 ) return a != null ? 13 : 77;
	if ( arg == 2 ) return a[0];
	if ( arg == 3 ) return a[1];
	return 42;
    }
}
