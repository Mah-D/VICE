// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 51

class virtual07d_1 {
    method val(): int { return 11; }
}

class virtual07d_2 extends virtual07d_1 {
    method val(): int { return 21; }
}

class virtual07d_3 extends virtual07d_1 {
    method val(): int { return 31; }
}

class virtual07d_4 {
    method val(): int { return 51; }
}

class virtual07d_5 extends virtual07d_4 {
    method val(): int { return 61; }
}

component virtual07d {
    field a: virtual07d_1 = new virtual07d_1();
    field b: virtual07d_1 = new virtual07d_2();
    field c: virtual07d_1 = new virtual07d_3();
    field d: virtual07d_4 = new virtual07d_4();
    field e: virtual07d_4 = new virtual07d_5();

    method main(arg: int): int {
	return d.val();
    }
}
