// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 21

class virtual02b_1 {
    method val() { virtual02b.R = 11; }
}

class virtual02b_2 extends virtual02b_1 {
    method val() { virtual02b.R = 21; }
}

class virtual02b_3 extends virtual02b_2 {
    method val() { virtual02b.R = 31; }
}

component virtual02b {
    field a: virtual02b_1 = new virtual02b_1();
    field b: virtual02b_1 = new virtual02b_2();
    field c: virtual02b_1 = new virtual02b_3();
    field R: int;

    method main(arg: int): int {
	b.val();
	return R;
    }
}
