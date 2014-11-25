// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 31

class virtual02c_1 {
    method val() { virtual02c.R = 11; }
}

class virtual02c_2 extends virtual02c_1 {
    method val() { virtual02c.R = 21; }
}

class virtual02c_3 extends virtual02c_2 {
    method val() { virtual02c.R = 31; }
}

component virtual02c {
    field a: virtual02c_1 = new virtual02c_1();
    field b: virtual02c_1 = new virtual02c_2();
    field c: virtual02c_1 = new virtual02c_3();
    field R: int;

    method main(arg: int): int {
	c.val();
	return R;
    }
}
