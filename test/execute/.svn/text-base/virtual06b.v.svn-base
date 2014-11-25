// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 11

class virtual06b_1 {
    method val() { virtual06b.R = 11; }
}

class virtual06b_2 extends virtual06b_1 {
}

class virtual06b_3 extends virtual06b_2 {
    method val() { virtual06b.R = 31; }
}

component virtual06b {
    field a: virtual06b_1 = new virtual06b_1();
    field b: virtual06b_1 = new virtual06b_2();
    field c: virtual06b_1 = new virtual06b_3();

    field R: int;

    method main(arg: int): int {
	b.val();
	return R;
    }
}
