// @Harness: v2-exec
// @Test: compound assignment operators
// @Result: 0=0, 1=8, 2=8, 3=0

component cmpassn01 {
    field foo: int = 6;
    field bar: int;

    method main(arg: int): int {
	bar = (foo += 2);
	if ( arg == 1 ) return foo;
	if ( arg == 2 ) return bar;
	return 0;
    }
}
