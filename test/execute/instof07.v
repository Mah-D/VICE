// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 1

class instof07_a {
}

class instof07_k {
}

class instof07_b extends instof07_a {
}

class instof07_l extends instof07_k {
}

class instof07_c extends instof07_b {
}

component instof07 {
    field foo: instof07_a = new instof07_c();

    method main(arg: int): int {
	return foo instanceof instof07_b ? 1 : 0;
    }
}
