// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=1, 10=1, 11=0

component raw_or01 {
    field res_01: 32 = op(0x0f, 0xfe);
    field res_02: 32 = op(2, -1);
    field res_03: 32 = op(4, 2);
    field res_04: 32 = op(0, -1);
    field res_05: 32 = op(0xf0, 0xaa);
    field res_06: 32 = op(65535, 345);
    field res_07: 32 = op(0x10000, 65537);
    field res_08: 32 = op(64, 8);
    field res_09: 32 = op(255, 5);
    field res_10: 32 = op(0xaa, 0x1a);

    method op(a: 32, b: 32): 32 {
	return a | b;
    }

    method main(arg: int): bool {
	if ( arg == 1 )  return op(0x0f, 0xfe)     == res_01;
	if ( arg == 2 )  return op(2, -1)          == res_02;
	if ( arg == 3 )  return op(4, 2)           == res_03;
	if ( arg == 4 )  return op(0, -1)          == res_04;
	if ( arg == 5 )  return op(0xf0, 0xaa)     == res_05;
	if ( arg == 6 )  return op(65535, 345)     == res_06;
	if ( arg == 7 )  return op(0x10000, 65537) == res_07;
	if ( arg == 8 )  return op(64, 8)          == res_08;
	if ( arg == 9 )  return op(255, 5)         == res_09;
	if ( arg == 10 ) return op(0xaa, 0x1a)     == res_10;
	return false;
    }
}
