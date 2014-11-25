// @Harness: v2-exec
// @Test: initialization of arrays of raws
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=0

component array_raw11 {
    field a: 32[] = { 0xfe710000 };

    method main(arg: int): bool {
	local x: 32 = 0x42;
	if ( arg == 1 ) a[0] = x = 0xfe710000;
	if ( arg == 2 ) a[0] = x = 0x00000004;
	if ( arg == 3 ) a[0] = x = 'c';
	if ( arg == 4 ) a[0] = x = -1;
	return a[0] == x;
    }
}
