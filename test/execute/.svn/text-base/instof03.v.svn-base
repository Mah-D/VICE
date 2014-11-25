// @Harness: v2-exec
// @Test: operation of instanceof construct
// @Result: 0=0

class instof03_a {
}

class instof03_b extends instof03_a {
}

component instof03 {
    field foo: instof03_a = new instof03_a();
    method main(arg: int): int {
	return foo instanceof instof03_b ? 1 : 0;
    }
}
