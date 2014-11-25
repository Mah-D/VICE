// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=49, 2=49, 3=50, 4=104, 5=105, 6=42

component array03 {
    field a_01: char[] =  {'1'};
    field a_02: char[] =  {'1', '2'};
    field a_03: char[] =  "hi";

    method main(arg: int): char {
	if ( arg == 1 ) return a_01[0];
	if ( arg == 2 ) return a_02[0];
	if ( arg == 3 ) return a_02[1];
	if ( arg == 4 ) return a_03[0];
	if ( arg == 5 ) return a_03[1];
	return '*';
    }
}
