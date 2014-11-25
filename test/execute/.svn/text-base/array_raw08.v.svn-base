// @Harness: v2-exec
// @Test: reading of arrays of raws
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=0

component array_raw08 {
    field a: 51[] = { 0x1000fe710000, 0b100, 'c', -1 };
    field av: 51  = a[0];

    method main(arg: int): bool {
	if ( arg == 1 ) return a[0] == 0x01000fe710000;
	if ( arg == 2 ) return a[1] == 0x0000000000004;
	if ( arg == 3 ) return a[2] == 0x0000000000063;
	if ( arg == 4 ) return a[3] == 0x7ffffffffffff;
	return false;
    }
}
