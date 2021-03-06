// @Harness: v2-exec
// @Test: concat and bit operators
// @Result: 0=15, 1=7, 2=4, 3=8, 4=1, 5=15, 6=14, 7=5, 8=15

component raw_flip04 {

    method flip(a: 4): 4 {
	return a[0] # a[1] # a[2] # a[3];
    }

    method main(arg: int): 4 {
	if ( arg == 1 ) return flip(0xe);
	if ( arg == 2 ) return flip(0x2);
	if ( arg == 3 ) return flip(0x1);
	if ( arg == 4 ) return flip(0x8);
	if ( arg == 5 ) return flip(0xf);
	if ( arg == 6 ) return flip(0x7);
	if ( arg == 7 ) return flip(0xa);
	return 0xF;
    }
}
