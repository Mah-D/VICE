// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=1, 1=0, 2=0, 3=0, 4=0, 5=1

component array08 {
    field a: bool[] = new bool[1];
    field b: bool[] = new bool[3];

    method main(arg: int): bool {
	if ( arg == 1 ) return a[0];
	if ( arg == 2 ) return b[0];
	if ( arg == 3 ) return b[1];
	if ( arg == 4 ) return b[2];
	return true;
    }
}
