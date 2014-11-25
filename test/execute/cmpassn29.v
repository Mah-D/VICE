// @Harness: v2-exec
// @Test: compound assignment operators
// @Result: 0=11, 1=5, 2=22, 3=3, 4=15, 5=8, 6=11

component cmpassn29 {

    field foo: 8[] = { 0xb, 0x2a };

    method main(arg: int): 8 {
	local ind = 0;
	if ( arg == 1 ) foo[ind++] >>= 1;
	if ( arg == 2 ) foo[ind++] <<= 1;
	if ( arg == 3 ) foo[ind++] &= 0x3;
	if ( arg == 4 ) foo[ind++] |= 0x5;
	if ( arg == 5 ) foo[ind++] ^= 0x3;
	return foo[0];
    }
}
