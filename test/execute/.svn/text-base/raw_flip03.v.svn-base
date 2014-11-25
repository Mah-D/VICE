// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=0

component raw_flip03 {
    field res_01: 16 = flip(0x0ffe);
    field res_02: 16 = flip(0x0002);
    field res_03: 16 = flip(0x0001);
    field res_04: 16 = flip(0x8000);
    field res_05: 16 = flip(0xf007);
    field res_06: 16 = flip(0x8877);
    field res_07: 16 = flip(0xaa01);

    method flip(a: 16): 16 {
	return (a :: 8) # ((a >> 8) :: 8);
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return flip(0x0ffe) == res_01;
	if ( arg == 2 ) return flip(0x0002) == res_02;
	if ( arg == 3 ) return flip(0x0001) == res_03;
	if ( arg == 4 ) return flip(0x8000) == res_04;
	if ( arg == 5 ) return flip(0xf007) == res_05;
	if ( arg == 6 ) return flip(0x8877) == res_06;
	if ( arg == 7 ) return flip(0xaa01) == res_07;
	return false;
    }
}
