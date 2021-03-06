// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=11, 3=31, 4=42

class virtual06a_1 {
    method val() { virtual06a.R = 11; }
}

class virtual06a_2 extends virtual06a_1 {
}

class virtual06a_3 extends virtual06a_2 {
    method val() { virtual06a.R = 31; }
}

component virtual06a {
    field a: virtual06a_1 = new virtual06a_1();
    field b: virtual06a_1 = new virtual06a_2();
    field c: virtual06a_1 = new virtual06a_3();

    field R: int;

    method main(arg: int): int {
	R = 42;
	if ( arg == 1 ) a.val();
	if ( arg == 2 ) b.val();
	if ( arg == 3 ) c.val();
	return R;
    }
}
