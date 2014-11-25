// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=42

class field03b_obj {
    field a: int = 13;
    field b: int = 14;
    method geta(): int { return a; }
    method getb(): int { return b; }
}

component field03b {
    field foo: field03b_obj = new field03b_obj();

    method main(arg: int): int {
	if ( arg == 1 ) return foo.geta();
	if ( arg == 2 ) return foo.getb();
	return 42;
    }
}
