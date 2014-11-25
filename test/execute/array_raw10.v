// @Harness: v2-exec
// @Test: initialization of arrays of raws
// @Result: 0=0, 1=1, 2=1, 3=1, 4=0

component array_raw10 {
    field a: 16[] = { 0xff };

    method main(arg: int): bool {
	local x: 16 = 0x2a;
	if ( arg == 1 ) a[0] = x = 0xfe71;
	if ( arg == 2 ) a[0] = x = 0x0004;
	if ( arg == 3 ) a[0] = x = 'c';
	return a[0] == x;
    }
}
