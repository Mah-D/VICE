// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42

class virtual01a_1 {
    method val(): int { return 11; }
}

class virtual01a_2 extends virtual01a_1 {
    method val(): int { return 21; }
}

class virtual01a_3 extends virtual01a_1 {
    method val(): int { return 31; }
}

component virtual01a {
    field a: virtual01a_1 = new virtual01a_1();
    field b: virtual01a_1 = new virtual01a_2();
    field c: virtual01a_1 = new virtual01a_3();

    method main(arg: int): int {
	if ( arg == 1 ) return a.val();
	if ( arg == 2 ) return b.val();
	if ( arg == 3 ) return c.val();
	return 42;
    }
}
