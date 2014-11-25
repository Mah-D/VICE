// @Harness: v2-exec
// @Test: type check exceptions (i.e. dynamic casts)
// @Result: 0=41, 1=TypeCheckException, 2=42, 3=42, 4=41

class rtex_type03_a {
}

class rtex_type03_b extends rtex_type03_a {
    field foo: int = 42;
}

class rtex_type03_c extends rtex_type03_b {
}

component rtex_type03 {
    field a: rtex_type03_a = new rtex_type03_a();
    field b: rtex_type03_a = new rtex_type03_b();
    field c: rtex_type03_a = new rtex_type03_c();

    method main(arg: int): int {
	local x: rtex_type03_a = null;

	if ( arg == 1 ) x = a;
	if ( arg == 2 ) x = b;
	if ( arg == 3 ) x = c;

	local y = x :: rtex_type03_b;
	return y == null ? 41 : y.foo;
    }
}
