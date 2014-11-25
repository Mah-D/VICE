// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 31

class virtual05c_1 {
    method val(): int { return 11; }
}

class virtual05c_2 extends virtual05c_1 {
}

class virtual05c_3 extends virtual05c_2 {
    method val(): int { return 31; }
}

component virtual05c {
    field a: virtual05c_1 = new virtual05c_1();
    field b: virtual05c_1 = new virtual05c_2();
    field c: virtual05c_1 = new virtual05c_3();

    method main(arg: int): int {
	return c.val();
    }
}
