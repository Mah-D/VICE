// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 0

class instof09_a {
}

class instof09_k {
}

class instof09_b extends instof09_a {
}

class instof09_l extends instof09_k {
}

class instof09_c extends instof09_b {
}

component instof09 {
    field foo: instof09_a = null;

    method main(arg: int): int {
	return foo instanceof instof09_b ? 1 : 0;
    }
}
