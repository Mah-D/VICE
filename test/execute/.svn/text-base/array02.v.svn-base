// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=0, 1=1, 2=1, 3=1, 4=0, 5=0

component array02 {
    field a_01: bool[] = {true};
    field a_02: bool[] = {true, true, false};

    method main(arg: int): bool {
	if ( arg == 1 ) return a_01[0];
	if ( arg == 2 ) return a_02[0];
	if ( arg == 3 ) return a_02[1];
	if ( arg == 4 ) return a_02[2];
	return false;
    }
}
