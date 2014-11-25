// @Harness: v2-exec
// @Test: reading of arrays of raws
// @Result: 0=42, 1=15, 2=0, 3=99, 4=42

component array_raw09 {
    field a: 8[] = { 0x2a };

    method main(arg: int): 8 {
	if ( arg == 1 ) a[0] = 0xf;
	if ( arg == 2 ) a[0] = 0b00;
	if ( arg == 3 ) a[0] = 'c';
	return a[0];
    }
}
