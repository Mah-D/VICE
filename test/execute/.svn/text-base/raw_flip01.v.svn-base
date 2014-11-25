// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=0

component raw_flip01 {

    method flip(a: 16): 16 {
	return (a :: 8) # ((a >> 8) :: 8);
    }

    method flip2(x: 16): bool {
	return flip(flip(x)) == x;
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return flip2(0x0ffe);
	if ( arg == 2 ) return flip2(0x0002);
	if ( arg == 3 ) return flip2(0x0001);
	if ( arg == 4 ) return flip2(0x8000);
	if ( arg == 5 ) return flip2(0xf007);
	if ( arg == 6 ) return flip2(0x8877);
	if ( arg == 7 ) return flip2(0xaa01);
	return false;
    }
}
