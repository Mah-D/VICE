// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 31

class virtual01c_1 {
    method val(): int { return 11; }
}

class virtual01c_2 extends virtual01c_1 {
    method val(): int { return 21; }
}

class virtual01c_3 extends virtual01c_1 {
    method val(): int { return 31; }
}

component virtual01c {
    field a: virtual01c_1 = new virtual01c_1();
    field b: virtual01c_1 = new virtual01c_2();
    field c: virtual01c_1 = new virtual01c_3();

    method main(arg: int): int {
	return c.val();
    }
}
