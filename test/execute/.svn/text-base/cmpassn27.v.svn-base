// @Harness: v2-exec
// @Test: compound assignment operators
// @Result: 0=11, 1=5, 2=22, 3=3, 4=15, 5=8, 11=11

component cmpassn27 {

    method main(arg: int): 8 {
	local foo: 8 = 0xb;
	if ( arg == 1 ) foo >>= 1;
	if ( arg == 2 ) foo <<= 1;
	if ( arg == 3 ) foo &= 0x3;
	if ( arg == 4 ) foo |= 0x5;
	if ( arg == 5 ) foo ^= 0x3;
	return foo;
    }
}
