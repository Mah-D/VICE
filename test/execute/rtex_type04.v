// @Harness: v2-exec
// @Test: type check exceptions (i.e. dynamic casts)
// @Result: 0=41, 1=TypeCheckException, 2=TypeCheckException, 3=12, 4=41

class rtex_type04_a {
    field foo: int;

    constructor(f: int) { 
	foo = f; 
    }
}

class rtex_type04_b extends rtex_type04_a {
    constructor(): super(11) { }
}

class rtex_type04_c extends rtex_type04_a {
    constructor(): super(12) { }
}

component rtex_type04 {
    field a: rtex_type04_a = new rtex_type04_a(10);
    field b: rtex_type04_a = new rtex_type04_b();
    field c: rtex_type04_a = new rtex_type04_c();

    method main(arg: int): int {
	local x: rtex_type04_a = null;

	if ( arg == 1 ) x = a;
	if ( arg == 2 ) x = b;
	if ( arg == 3 ) x = c;

	local y = x :: rtex_type04_c;
	return y == null ? 41 : y.foo;
    }
}
