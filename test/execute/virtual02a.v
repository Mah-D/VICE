// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42

class virtual02a_1 {
    method val() { virtual02a.R = 11; }
}

class virtual02a_2 extends virtual02a_1 {
    method val() { virtual02a.R = 21; }
}

class virtual02a_3 extends virtual02a_2 {
    method val() { virtual02a.R = 31; }
}

component virtual02a {
    field a: virtual02a_1 = new virtual02a_1();
    field b: virtual02a_1 = new virtual02a_2();
    field c: virtual02a_1 = new virtual02a_3();
    field R: int;

    method main(arg: int): int {
	R = 42;
	if ( arg == 1 ) a.val();
	if ( arg == 2 ) b.val();
	if ( arg == 3 ) c.val();
	return R;
    }
}
