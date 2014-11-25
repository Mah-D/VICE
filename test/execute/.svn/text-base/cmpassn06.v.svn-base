// @Harness: v2-exec
// @Test: pre/post increment options
// @Result: 0=0, 1=4, 2=4, 3=0

component cmpassn06 {
    field foo: int = 8;
    field bar: int;

    method main(arg: int): int {
	bar = (foo /= 2);
	if ( arg == 1 ) return foo;
	if ( arg == 2 ) return bar;
	return 0;
    }
}
