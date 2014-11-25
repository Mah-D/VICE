// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=0, 1=1, 2=0, 3=1, 4=0, 5=1, 6=0, 7=0

component array05 {
    field a: bool[][] =  { {true, false}, {true}, {false, true, false} };

    method main(arg: int): bool {
	if ( arg == 1 ) return a[0][0];
	if ( arg == 2 ) return a[0][1];
	if ( arg == 3 ) return a[1][0];
	if ( arg == 4 ) return a[2][0];
	if ( arg == 5 ) return a[2][1];
	if ( arg == 6 ) return a[2][2];
	return false;
    }
}
