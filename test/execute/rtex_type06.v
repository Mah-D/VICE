// @Harness: v2-exec
// @Test: type check exceptions (i.e. dynamic casts)
// @Result: 0=41, 1=41, 2=41, 3=41, 4=41

class rtex_type06_a {
    field foo: int;

    constructor(f: int) { 
	foo = f; 
    }
}

class rtex_type06_b extends rtex_type06_a {
    constructor(): super(11) { }
}

class rtex_type06_c extends rtex_type06_a {
    constructor(): super(12) { }
}

component rtex_type06 {

    method main(arg: int): int {
	local x: rtex_type06_a = null;
	local r: int = 41;

	if ( arg == 1 ) x = x :: rtex_type06_a;
	if ( arg == 2 ) x = x :: rtex_type06_b;
	if ( arg == 3 ) x = x :: rtex_type06_c;

	if ( x != null ) return x.foo;
	else return r;
    }
}
