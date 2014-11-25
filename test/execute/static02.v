// @Harness: v2-exec
// @Test: static method invocations
// @Result: 0=0, 1=11, 2=21, 3=31, 4=0

component static02_a {
    field foo: int = 11;
    method val(): int { return foo; }
}

component static02_b {
    field baz: int = 21;
    method val(): int { return baz; }
}

component static02_c {
    field bof: int = 31;
    method val(): int { return bof; }
}

component static02 {

    method main(arg: int): int {
	if ( arg == 1 ) return static02_a.val();
	if ( arg == 2 ) return static02_b.val();
	if ( arg == 3 ) return static02_c.val();
	return 0;
    }
}
