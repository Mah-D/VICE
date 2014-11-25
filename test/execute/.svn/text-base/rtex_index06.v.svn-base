// @Harness: v2-exec
// @Test: array rtex_index exceptions
// @Result: 0=42, 1=0, 2=1, 3=15, 4=40, 5=41, 6=BoundsCheckException, 7=42

component rtex_index06 {
    field foo: int[] = new int[42];

    method scan(m: int): int {
        local cntr: int;
        for ( cntr = 0; cntr <= m; cntr++ ) foo[cntr] = cntr;
        return m;
    }

    method main(arg: int): int {
	if ( arg == 1 ) return scan(0);
	if ( arg == 2 ) return scan(1);
	if ( arg == 3 ) return scan(15);
	if ( arg == 4 ) return scan(40);
	if ( arg == 5 ) return scan(41);
	if ( arg == 6 ) return scan(42);
	return 42;
    }
}
