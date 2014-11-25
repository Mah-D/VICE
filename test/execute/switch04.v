// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=10

component switch04 {
	field foo: int;
	method main(args: int): int {
		switch (foo) {
			case (0) return 10;
			case (1) return 11;
			default return -1;
		}
	}
}
