// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=2, 2=30, 3=2, 4=30, 5=42

component array20 {
    field a: char[] =  {'1', '2'};
    field b: char[] =  "helloyoudirtyrat--it's go time";
    field len1: int = a.length;
    field len2: int = b.length;

    method main(arg: int): int {
	if ( arg == 1 ) return len1;
	if ( arg == 2 ) return len2;
	if ( arg == 3 ) return a.length;
	if ( arg == 4 ) return b.length;
	return 42;
    }
}
