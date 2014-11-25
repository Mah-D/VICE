// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=14, 2=24, 3=34, 4=3, 5=3, 6=42

component array19 {
    field a: int[] =  {14, 24, 34};
    field len: int =  a.length;

    method main(arg: int): int {
	if ( arg == 1 ) return a[0];
	if ( arg == 2 ) return a[1];
	if ( arg == 3 ) return a[2];
	if ( arg == 4 ) return len;
	if ( arg == 5 ) return a.length;
	return 42;
    }
}
