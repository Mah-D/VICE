// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=13, 3=42

component default03a {
    field foo: int[];
    field bar: bool = foo == null;

    method main(arg: int): int {
	if ( arg == 1 ) return foo == null ? 13 : 10;
	if ( arg == 2 ) return bar ? 13 : 10;
	return 42;
    }
}
