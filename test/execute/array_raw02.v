// @Harness: v2-exec
// @Test: initialization of arrays of raws
// @Result: 0=0, 1=1, 2=1, 3=1, 4=0

component array_raw02 {
    field a: 16[] = { 0xfe71, 0b100, 'c' };

    method main(arg: int): bool {
	if ( arg == 1 ) return a[0] == 0xfe71;
	if ( arg == 2 ) return a[1] == 0x0004;
	if ( arg == 3 ) return a[2] == 0x0063;
	return false;
    }
}
