// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 0=0

class instof05_a {
}

class instof05_b extends instof05_a {
}

class instof05_c extends instof05_a {
}

component instof05 {
    field foo: instof05_a = new instof05_c();

    method main(arg: int): int {
	return foo instanceof instof05_b ? 1 : 0;
    }
}
