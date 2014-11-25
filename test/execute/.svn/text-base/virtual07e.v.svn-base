// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 61

class virtual07e_1 {
    method val(): int { return 11; }
}

class virtual07e_2 extends virtual07e_1 {
    method val(): int { return 21; }
}

class virtual07e_3 extends virtual07e_1 {
    method val(): int { return 31; }
}

class virtual07e_4 {
    method val(): int { return 51; }
}

class virtual07e_5 extends virtual07e_4 {
    method val(): int { return 61; }
}

component virtual07e {
    field a: virtual07e_1 = new virtual07e_1();
    field b: virtual07e_1 = new virtual07e_2();
    field c: virtual07e_1 = new virtual07e_3();
    field d: virtual07e_4 = new virtual07e_4();
    field e: virtual07e_4 = new virtual07e_5();

    method main(arg: int): int {
	return e.val();
    }
}
