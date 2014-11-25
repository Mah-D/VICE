// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 31

class virtual07c_1 {
    method val(): int { return 11; }
}

class virtual07c_2 extends virtual07c_1 {
    method val(): int { return 21; }
}

class virtual07c_3 extends virtual07c_1 {
    method val(): int { return 31; }
}

class virtual07c_4 {
    method val(): int { return 51; }
}

class virtual07c_5 extends virtual07c_4 {
    method val(): int { return 61; }
}

component virtual07c {
    field a: virtual07c_1 = new virtual07c_1();
    field b: virtual07c_1 = new virtual07c_2();
    field c: virtual07c_1 = new virtual07c_3();
    field d: virtual07c_4 = new virtual07c_4();
    field e: virtual07c_4 = new virtual07c_5();

    method main(arg: int): int {
	return c.val();
    }
}
