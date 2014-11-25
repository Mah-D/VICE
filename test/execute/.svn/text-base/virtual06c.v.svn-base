// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 31

class virtual06c_1 {
    method val() { virtual06c.R = 11; }
}

class virtual06c_2 extends virtual06c_1 {
}

class virtual06c_3 extends virtual06c_2 {
    method val() { virtual06c.R = 31; }
}

component virtual06c {
    field a: virtual06c_1 = new virtual06c_1();
    field b: virtual06c_1 = new virtual06c_2();
    field c: virtual06c_1 = new virtual06c_3();

    field R: int;

    method main(arg: int): int {
	c.val();
	return R;
    }
}
