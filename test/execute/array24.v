// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=15, 4=42

component array24 {
    field a: int[] = f();

    method f(): int[] {
        return null;
    }

    method main(arg: int): int {
	if ( arg == 1 ) return a == f() ? 13 : 77;
	if ( arg == 2 ) return a == null ? 14 : 77;
	if ( arg == 3 ) return f() == null ? 15 : 77;
	return 42;
    }
}
