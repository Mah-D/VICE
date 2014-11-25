// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=2, 2=13, 3=2, 4=13, 5=2, 6=13, 7=42

component array21 {
    field a: char[] =  {'1', '2'};
    field b: char[] =  "earth\tmoon\n11";
    field len1: int = length(a);
    field len2: int = length(b);

    method length(a: char[]): int {
	return a.length;
    }

    method main(arg: int): int {
	if ( arg == 1 ) return len1;
	if ( arg == 2 ) return len2;
	if ( arg == 3 ) return a.length;
	if ( arg == 4 ) return b.length;
	if ( arg == 5 ) return length(a);
	if ( arg == 6 ) return length(b);
	return 42;
    }

}
