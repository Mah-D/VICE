// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 11

class virtual05b_1 {
    method val(): int { return 11; }
}

class virtual05b_2 extends virtual05b_1 {
}

class virtual05b_3 extends virtual05b_2 {
    method val(): int { return 31; }
}

component virtual05b {
    field a: virtual05b_1 = new virtual05b_1();
    field b: virtual05b_1 = new virtual05b_2();
    field c: virtual05b_1 = new virtual05b_3();

    method main(arg: int): int {
	return b.val();
    }
}
