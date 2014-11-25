// @Harness: v2-exec
// @Test: initialization of arrays of raws
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=0

component array_raw12 {
    field a: 64[] = { 0x111f };

    method main(arg: int): bool {
	local x: 64 = 0x2a;
	if ( arg == 1 ) a[0] = x = 0xf0000000fe710000;
	if ( arg == 2 ) a[0] = x = 0x0000000000000004;
	if ( arg == 3 ) a[0] = x = 'c';
	if ( arg == 4 ) a[0] = x = -1;
	return a[0] == x;
    }
}
