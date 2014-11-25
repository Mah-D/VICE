// @Harness: v2-exec
// @Test: pre/post increment options
// @Result: 0=0, 1=32, 2=32, 3=0

component cmpassn05 {
    field foo: 8 = 0x8;
    field bar: 8;

    method main(arg: int): int {
	bar = (foo <<= 2);
	if ( arg == 1 ) return foo :: int;
	if ( arg == 2 ) return bar :: int;
	return 0;
    }
}
