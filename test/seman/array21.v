// @Harness: v2-seman
// @Test: field initialization
// @Result: PASS

component array26 {
    field a_01: (function(): int)[] = { m, m };
    method m(): int {
	return 0;
    }
}
