// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 0=1

class instof04_a {
}

class instof04_b extends instof04_a {
}

component instof04 {
    field foo: instof04_a = new instof04_b();

    method main(arg: int): int {
	return foo instanceof instof04_b ? 1 : 0;
    }
}
