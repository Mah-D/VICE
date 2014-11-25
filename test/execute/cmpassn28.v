// @Harness: v2-exec
// @Test: compound assignment operators
// @Result: 0=11, 1=5, 2=22, 3=3, 4=15, 5=8, 6=11

component cmpassn28 {

    field foo: 8[] = { 0xb, 0x2a };

    method main(arg: int): 8 {
	if ( arg == 1 ) foo[0] >>= 1;
	if ( arg == 2 ) foo[0] <<= 1;
	if ( arg == 3 ) foo[0] &= 0x3;
	if ( arg == 4 ) foo[0] |= 0x5;
	if ( arg == 5 ) foo[0] ^= 0x3;
	return foo[0];
    }
}
