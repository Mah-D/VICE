// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=1, 2=1, 3=1, 4=1, 5=1, 6=1, 7=1, 8=1, 9=1, 10=1, 11=0

component raw_comp04 {
    field res_01: 32 = op(-2);
    field res_02: 32 = op(-1);
    field res_03: 32 = op(1100);
    field res_04: 32 = op(0);
    field res_05: 32 = op(-13);
    field res_06: 32 = op(1);
    field res_07: 32 = op(-17);
    field res_08: 32 = op(-65536);
    field res_09: 32 = op(255);
    field res_10: 32 = op(1000000);

    method op(a: 32): 32 {
	return (~(a & 0x0fffffff)) >> 4;
    }

    method main(arg: int): bool {
	if ( arg == 1 )  return op(-2)      == res_01;
	if ( arg == 2 )  return op(-1)      == res_02;
	if ( arg == 3 )  return op(1100)    == res_03;
	if ( arg == 4 )  return op(0)       == res_04;
	if ( arg == 5 )  return op(-13)     == res_05;
	if ( arg == 6 )  return op(1)       == res_06;
	if ( arg == 7 )  return op(-17)     == res_07;
	if ( arg == 8 )  return op(-65536)  == res_08;
	if ( arg == 9 )  return op(255)     == res_09;
	if ( arg == 10 ) return op(1000000) == res_10;
	return false;
    }

}
