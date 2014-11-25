// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=1, 10=1, 11=0

component arith08 {
    field res_01: int = op(1, -2);
    field res_02: int = op(2, -1);
    field res_03: int = op(-1, -1);
    field res_04: int = op(-1, 0);
    field res_05: int = op(-200, -13);
    field res_06: int = op(65535, -1);
    field res_07: int = op(0, -17);
    field res_08: int = op(255, -12);
    field res_09: int = op(255, 255);
    field res_10: int = op(1000, -48576);

    method op(a: int, b: int): int {
	return a * b;
    }

    method main(arg: int): bool {
	if ( arg == 1 ) return (1 * -2) == res_01;
	if ( arg == 2 )  return (2 * -1)         == res_02;
	if ( arg == 3 )  return (-1 * -1)        == res_03;
	if ( arg == 4 )  return (-1 * 0)         == res_04;
	if ( arg == 5 )  return (-200 * -13)     == res_05;
	if ( arg == 6 )  return (65535 * -1)     == res_06;
	if ( arg == 7 )  return (0 * -17)        == res_07;
	if ( arg == 8 )  return (255 * -12)      == res_08;
	if ( arg == 9 )  return (255 * 255)      == res_09;
	if ( arg == 10 ) return (1000 * -48576)  == res_10;
	return false;
    }

}
