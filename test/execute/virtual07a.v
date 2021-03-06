// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=51, 5=61, 6=42

class virtual07a_1 {
    method val(): int { return 11; }
}

class virtual07a_2 extends virtual07a_1 {
    method val(): int { return 21; }
}

class virtual07a_3 extends virtual07a_1 {
    method val(): int { return 31; }
}

class virtual07a_4 {
    method val(): int { return 51; }
}

class virtual07a_5 extends virtual07a_4 {
    method val(): int { return 61; }
}

component virtual07a {
    field a: virtual07a_1 = new virtual07a_1();
    field b: virtual07a_1 = new virtual07a_2();
    field c: virtual07a_1 = new virtual07a_3();
    field d: virtual07a_4 = new virtual07a_4();
    field e: virtual07a_4 = new virtual07a_5();

    method main(arg: int): int {
	if ( arg == 1 ) return a.val();
	if ( arg == 2 ) return b.val();
	if ( arg == 3 ) return c.val();
	if ( arg == 4 ) return d.val();
	if ( arg == 5 ) return e.val();
	return 42;
    }
}
