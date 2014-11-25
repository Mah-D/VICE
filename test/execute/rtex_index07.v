// @Harness: v2-exec
// @Test: array rtex_index exceptions
// @Result: 0=42, 1=255, 2=0, 3=14, 4=39, 5=40, 6=BoundsCheckException, 7=BoundsCheckException

component rtex_index07 {
    field foo: int[] = new int[42];

    method scan(max: int, min: int): int {
        local cntr: int;
        for ( cntr = max; cntr >= min; cntr-- )
	    foo[cntr] = cntr;
        return cntr;
    }

    method main(arg: int): int {
	if ( arg == 1 ) return scan(5, 0);
	if ( arg == 2 ) return scan(7, 1);
	if ( arg == 3 ) return scan(40, 15);
	if ( arg == 4 ) return scan(41, 40);
	if ( arg == 5 ) return scan(41, 41);
	if ( arg == 6 ) return scan(42, 0);
	if ( arg == 7 ) return scan(40, -1);
	return 42;
    }
}
