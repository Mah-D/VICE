// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 21

class virtual07b_1 {
    method val(): int { return 11; }
}

class virtual07b_2 extends virtual07b_1 {
    method val(): int { return 21; }
}

class virtual07b_3 extends virtual07b_1 {
    method val(): int { return 31; }
}

class virtual07b_4 {
    method val(): int { return 51; }
}

class virtual07b_5 extends virtual07b_4 {
    method val(): int { return 61; }
}

component virtual07b {
    field a: virtual07b_1 = new virtual07b_1();
    field b: virtual07b_1 = new virtual07b_2();
    field c: virtual07b_1 = new virtual07b_3();
    field d: virtual07b_4 = new virtual07b_4();
    field e: virtual07b_4 = new virtual07b_5();

    method main(arg: int): int {
	return b.val();
    }
}
