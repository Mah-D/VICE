// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=13, 3=42

class default03b_obj {
    field foo: int[];
    field bar: bool = foo == null;
}

component default03b {
    field baz: default03b_obj = new default03b_obj();

    method main(arg: int): int {
	if ( arg == 1 ) return baz.foo == null ? 13 : 10;
	if ( arg == 2 ) return baz.bar ? 13 : 10;
	return 42;
    }
}
