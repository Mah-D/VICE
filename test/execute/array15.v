// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=0, 1=1, 2=0, 3=1, 4=0, 5=1, 6=0, 7=0

component array15 {
    field a: bool[][] =  { {true, false}, {true}, {false, true, false} };

    method getf(b: bool[], i: int): bool {
	return b[i];
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return getf(a[0], 0);
	if ( arg == 2 ) return getf(a[0], 1);
	if ( arg == 3 ) return getf(a[1], 0);
	if ( arg == 4 ) return getf(a[2], 0);
	if ( arg == 5 ) return getf(a[2], 1);
	if ( arg == 6 ) return getf(a[2], 2);
	return false;
    }
}
