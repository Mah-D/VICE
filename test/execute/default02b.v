// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=10, 2=13, 3=42

class default02b_1 {
    field foo: bool;
    field bar: bool = !foo;
}

component default02b {
    field baz: default02b_1 = new default02b_1();

    method main(arg: int): int {
	if ( arg == 1 ) return baz.foo ? 13 : 10;
	if ( arg == 2 ) return baz.bar ? 13 : 10;
	return 42;
    }
}
