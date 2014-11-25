// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=7, 2=13, 3=16, 4=4, 5=5, 6=6, 7=42

component array14 {
    field a: int[][] =  { {7, 13}, {16}, {4, 5, 6} };

    method getf(f: int[], i: int): int {
	return f[i];
    }

    method main(arg: int): int {
	if ( arg == 1 ) return getf(a[0], 0);
	if ( arg == 2 ) return getf(a[0], 1);
	if ( arg == 3 ) return getf(a[1], 0);
	if ( arg == 4 ) return getf(a[2], 0);
	if ( arg == 5 ) return getf(a[2], 1);
	if ( arg == 6 ) return getf(a[2], 2);
	return 42;
    }
}
