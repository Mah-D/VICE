// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=0, 1=13, 2=7, 3=17, 4=0

component array30 {
    field a_01: int[] =  {13};
    field a_02: char[] =  {7::char, 17::char};

    method main(arg: int): int {
	if ( arg == 1 ) return a_01[0];
	if ( arg == 2 ) return a_02[0]::int;
	if ( arg == 3 ) return a_02[1]::int;
	return 0;
    }
}
