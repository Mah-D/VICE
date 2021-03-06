// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=17, 3=42

component field03a {
    field a: int = 13;
    field b: int = 17;

    method geta(): int { return a; }
    method getb(): int { return b; }

    method main(arg: int): int {
	if ( arg == 1 ) return geta();
	if ( arg == 2 ) return getb();
	return 42;
    }
}
