// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42

class virtual08a_1 {
    method val(): int { return 11; }
}

class virtual08a_2 extends virtual08a_1 {
    method val(): int { return 21; }
}

class virtual08a_3 extends virtual08a_1 {
    method val(): int { return 31; }
}

component virtual08a {
    field a: virtual08a_1 = new virtual08a_1();
    field b: virtual08a_1 = new virtual08a_2();
    field c: virtual08a_1 = new virtual08a_3();

    method main(arg: int): int {
	if ( arg == 1 ) return invoke(a);
	if ( arg == 2 ) return invoke(b);
	if ( arg == 3 ) return invoke(c);
	return 42;
    }

    method invoke(o: virtual08a_1): int {
	return o.val();
    }
}
