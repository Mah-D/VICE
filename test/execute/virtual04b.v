// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 12

class virtual04b_1 {
    field th: virtual04b_1;
    method val(a: int): int { th = this; return a+1; }
}

class virtual04b_2 extends virtual04b_1 {
    method val(a: int): int { th = this; return a+2; }
}

class virtual04b_3 extends virtual04b_1 {
    method val(a: int): int { th = this; return a+3; }
}

component virtual04b {
    field a: virtual04b_1 = new virtual04b_1();
    field b: virtual04b_1 = new virtual04b_2();
    field c: virtual04b_1 = new virtual04b_3();

    method main(arg: int): int {
	return b.val(10);
    }
}
