// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 1

class instof06_a {
}

class instof06_b extends instof06_a {
}

class instof06_c extends instof06_b {
}

component instof06 {
    field foo: instof06_a = new instof06_c();

    method main(arg: int): int {
	return foo instanceof instof06_b ? 1 : 0;
    }
}
