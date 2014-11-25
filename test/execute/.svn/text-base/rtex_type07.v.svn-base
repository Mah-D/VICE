// @Harness: v2-exec
// @Test: type check exceptions (i.e. dynamic casts)
// @Result: 0=11, 1=41, 2=41, 3=41, 4=11

class rtex_type07_a {
    field foo: int;

    constructor(f: int) { 
	foo = f; 
    }
}

class rtex_type07_b extends rtex_type07_a {
    constructor(): super(11) { }
}

class rtex_type07_c extends rtex_type07_a {
    constructor(): super(12) { }
}

component rtex_type07 {

    field a: rtex_type07_a = new rtex_type07_a(11);

    method main(arg: int): int {
	local x: rtex_type07_a = a;
	local y: rtex_type07_a = null;
	local r: int = 41;

	if ( arg == 1 ) x = y :: rtex_type07_a;
	if ( arg == 2 ) x = y :: rtex_type07_b;
	if ( arg == 3 ) x = y :: rtex_type07_c;

	if ( x != null ) return x.foo;
	else return r;
    }
}
