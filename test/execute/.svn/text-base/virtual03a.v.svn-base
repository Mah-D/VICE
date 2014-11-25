// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=12, 3=13, 4=42

class virtual03a_1 {
    method val(a: int): int { return a+1; }
}

class virtual03a_2 extends virtual03a_1 {
    method val(a: int): int { return a+2; }
}

class virtual03a_3 extends virtual03a_1 {
    method val(a: int): int { return a+3; }
}

component virtual03a {
    field a: virtual03a_1 = new virtual03a_1();
    field b: virtual03a_1 = new virtual03a_2();
    field c: virtual03a_1 = new virtual03a_3();

    method main(arg: int): int {
	if ( arg == 1 ) return a.val(10);
	if ( arg == 2 ) return b.val(10);
	if ( arg == 3 ) return c.val(10);
	return 42;
    }
}
