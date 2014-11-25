// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=1, 10=0

component raw_shl04 {
    field res_01: 29 = op(0x0f,     1);
    field res_02: 29 = op(0x02,     2);
    field res_03: 29 = op(0x01,    31);
    field res_04: 29 = op(0x30,     8);
    field res_05: 29 = op(0xf0,    16);
    field res_06: 29 = op(0xfffff, 12);
    field res_07: 29 = op(0x10000, 16);
    field res_08: 29 = op(0xff,     5);
    field res_09: 29 = op(0xaa,     0);

    method op(a: 29, b: int): 29 {
	return a << b;
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return op(0x0f,     1) == res_01;
	if ( arg == 2 ) return op(0x02,     2) == res_02;
	if ( arg == 3 ) return op(0x01,    31) == res_03;
	if ( arg == 4 ) return op(0x30,     8) == res_04;
	if ( arg == 5 ) return op(0xf0,    16) == res_05;
	if ( arg == 6 ) return op(0xfffff, 12) == res_06;
	if ( arg == 7 ) return op(0x10000, 16) == res_07;
	if ( arg == 8 ) return op(0xff,     5) == res_08;
	if ( arg == 9 ) return op(0xaa,     0) == res_09;
	return false;
    }
}
