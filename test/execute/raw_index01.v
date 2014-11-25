// @Harness: v2-exec
// @Test: getbit operator
// @Result: 0=0, 1=0, 2=1, 3=1, 4=1, 5=0, 6=0, 7=1, 8=0, 9=0, 10=0

component raw_index01 {
    field foo: 32 = 0x3ff0eeee;

    method main(arg: int): 1 {
	if ( arg == 1 ) return foo[0];
	if ( arg == 2 ) return foo[1];
	if ( arg == 3 ) return foo[5];
	if ( arg == 4 ) return foo[11];
	if ( arg == 5 ) return foo[16];
	if ( arg == 6 ) return foo[17];
	if ( arg == 7 ) return foo[20];
	if ( arg == 8 ) return foo[30];
	if ( arg == 9 ) return foo[31];
	return 0b0;
    }
}
