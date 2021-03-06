// @Harness: v2-exec
// @Test: initialization of arrays of raws
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=0

component array_raw07 {
    field a: 29[] = { 0xfe71000, 0b100, 'c', 0xffedd };

    method main(arg: int): bool {
	if ( arg == 1 ) return a[0] == 0xfe71000;
	if ( arg == 2 ) return a[1] == 0x0000004;
	if ( arg == 3 ) return a[2] == 0x0000063;
	if ( arg == 4 ) return a[3] == 0x00ffedd;
	return false;
    }
}
