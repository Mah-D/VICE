// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 31

class virtual08c_1 {
    method val(): int { return 11; }
}

class virtual08c_2 extends virtual08c_1 {
    method val(): int { return 21; }
}

class virtual08c_3 extends virtual08c_1 {
    method val(): int { return 31; }
}

component virtual08c {
    field a: virtual08c_1 = new virtual08c_1();
    field b: virtual08c_1 = new virtual08c_2();
    field c: virtual08c_1 = new virtual08c_3();

    method main(arg: int): int {
	invoke(a);
	invoke(b);
	invoke(c);
	return invoke(c);
    }

    method invoke(o: virtual08c_1): int {
	return o.val();
    }
}
