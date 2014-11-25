// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=1, 10=0

component raw_shl02 {
    field res_01: 16 = init(1);
    field res_02: 16 = init(2);
    field res_03: 16 = init(3);
    field res_04: 16 = init(4);
    field res_05: 16 = init(5);
    field res_06: 16 = init(6);
    field res_07: 16 = init(7);
    field res_08: 16 = init(8);
    field res_09: 16 = init(9);

    method op(a: 16, b: int): 16 {
	return a << b;
    }

    method init(arg: int): 16 {
	if ( arg == 1 )  return op(0xf,     1);
	if ( arg == 2 )  return op(0x2,     2);
	if ( arg == 3 )  return op(0x1,    15);
	if ( arg == 4 )  return op(0x0,     8);
	if ( arg == 5 )  return op(0xf0,   16);
	if ( arg == 6 )  return op(0xffff,  2);
	if ( arg == 7 )  return op(0x8000, 16);
	if ( arg == 8 )  return op(0xff,    5);
	if ( arg == 9 )  return op(0xaa,    0);
	return 0x0000;
    }

    method main(arg: int): bool {
	if ( arg == 1 )  return init(1) == res_01;
	if ( arg == 2 )  return init(2) == res_02;
	if ( arg == 3 )  return init(3) == res_03;
	if ( arg == 4 )  return init(4) == res_04;
	if ( arg == 5 )  return init(5) == res_05;
	if ( arg == 6 )  return init(6) == res_06;
	if ( arg == 7 )  return init(7) == res_07;
	if ( arg == 8 )  return init(8) == res_08;
	if ( arg == 9 )  return init(9) == res_09;
	return false;
    }
}
