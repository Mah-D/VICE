// @Harness: v2-exec
// @Test: concat and bit operators
// @Result: 0=15, 1=14, 2=2, 3=1, 4=8, 5=15, 6=7, 7=10, 8=15

component raw_flip02 {

    method flip(a: 4): 4 {
	return a[0] # a[1] # a[2] # a[3];
    }

    method flip2(a: 4): 4 {
	return flip(flip(a));
    }

    method main(arg: int): 4 {
	if ( arg == 1 ) return flip2(0xe);
	if ( arg == 2 ) return flip2(0x2);
	if ( arg == 3 ) return flip2(0x1);
	if ( arg == 4 ) return flip2(0x8);
	if ( arg == 5 ) return flip2(0xf);
	if ( arg == 6 ) return flip2(0x7);
	if ( arg == 7 ) return flip2(0xa);
	return 0xF;
    }
}
