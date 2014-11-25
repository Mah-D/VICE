// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 21

class virtual08b_1 {
    method val(): int { return 11; }
}

class virtual08b_2 extends virtual08b_1 {
    method val(): int { return 21; }
}

class virtual08b_3 extends virtual08b_1 {
    method val(): int { return 31; }
}

component virtual08b {
    field a: virtual08b_1 = new virtual08b_1();
    field b: virtual08b_1 = new virtual08b_2();
    field c: virtual08b_1 = new virtual08b_3();

    method main(arg: int): int {
	invoke(a);
	invoke(b);
	invoke(c);
	return invoke(b);
    }

    method invoke(o: virtual08b_1): int {
	return o.val();
    }
}
