// @Harness: v2-exec
// @Test: static method invocations
// @Result: 1 = 11, 2 = 21, 3 = 31, 4 = 0

component static01_a {
    method val(): int { return 11; }
}

component static01_b {
    method val(): int { return 21; }
}

component static01_c {
    method val(): int { return 31; }
}

component static01 {
    field val: int;

    method main(arg: int): int {
	if ( arg == 1 ) val = static01_a.val();
	if ( arg == 2 ) val = static01_b.val();
	if ( arg == 3 ) val = static01_c.val();
	return val;
    }
}
