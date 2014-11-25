// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 1

class instof08_a {
}

class instof08_k {
}

class instof08_b extends instof08_a {
}

class instof08_l extends instof08_k {
}

class instof08_c extends instof08_b {
}

component instof08 {
    field foo: instof08_k = new instof08_l();

    method main(arg: int): int {
	return foo instanceof instof08_k ? 1 : 0;
    }
}
