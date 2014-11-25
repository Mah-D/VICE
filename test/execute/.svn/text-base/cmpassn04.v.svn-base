// @Harness: v2-exec
// @Test: pre/post increment options
// @Result: 0=0, 1=4, 2=4, 3=0

component cmpassn04 {
    field foo: 8 = 0x8;
    field bar: 8;

    method main(arg: int): int {
	bar = (foo >>= 1);
	if ( arg == 1 ) return foo :: int;
	if ( arg == 2 ) return bar :: int;
	return 0;
    }
}
