// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 12

class virtual03b_1 {
    method val(a: int): int { return a+1; }
}

class virtual03b_2 extends virtual03b_1 {
    method val(a: int): int { return a+2; }
}

class virtual03b_3 extends virtual03b_1 {
    method val(a: int): int { return a+3; }
}

component virtual03b {
    field a: virtual03b_1 = new virtual03b_1();
    field b: virtual03b_1 = new virtual03b_2();
    field c: virtual03b_1 = new virtual03b_3();

    method main(arg: int): int {
	return b.val(10);
    }
}
