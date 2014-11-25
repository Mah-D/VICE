// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=10, 2=13, 3=42

component default02a {
    field foo: bool;
    field bar: bool = !foo;

    method main(arg: int): int {
	if ( arg == 1 ) return foo ? 13 : 10;
	if ( arg == 2 ) return bar ? 13 : 10;
	return 42;
    }
}
