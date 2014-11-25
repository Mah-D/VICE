// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 21

class virtual01b_1 {
    method val(): int { return 11; }
}

class virtual01b_2 extends virtual01b_1 {
    method val(): int { return 21; }
}

class virtual01b_3 extends virtual01b_1 {
    method val(): int { return 31; }
}

component virtual01b {
    field a: virtual01b_1 = new virtual01b_1();
    field b: virtual01b_1 = new virtual01b_2();
    field c: virtual01b_1 = new virtual01b_3();

    method main(arg: int): int {
	return b.val();
    }
}
