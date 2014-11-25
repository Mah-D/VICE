// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=1, 10=1, 11=0

component raw_shr02 {
    field res_01: 16 = op(0x0f,    1);
    field res_02: 16 = op(0x02,    2);
    field res_03: 16 = op(0x8000, 15);
    field res_04: 16 = op(0xefe,   8);
    field res_05: 16 = op(0xf0,   16);
    field res_06: 16 = op(0xaaaa,  4);
    field res_07: 16 = op(0x7000,  2);
    field res_08: 16 = op(0x700,   4);
    field res_09: 16 = op(0xfe09,  5);
    field res_10: 16 = op(0xaa01,  0);

    method op(a: 16, b: int): 16 {
	return a >> b;
    }

    method main(arg: int): bool {
	if ( arg == 1 )  return op(0x0f,    1) == res_01;
	if ( arg == 2 )  return op(0x02,    2) == res_02;
	if ( arg == 3 )  return op(0x8000, 15) == res_03;
	if ( arg == 4 )  return op(0xefe,   8) == res_04;
	if ( arg == 5 )  return op(0xf0,   16) == res_05;
	if ( arg == 6 )  return op(0xaaaa,  4) == res_06;
	if ( arg == 7 )  return op(0x7000,  2) == res_07;
	if ( arg == 8 )  return op(0x700,   4) == res_08;
	if ( arg == 9 )  return op(0xfe09,  5) == res_09;
	if ( arg == 10 ) return op(0xaa01,  0) == res_10;
	return false;
    }
}
