// @Harness: v2-exec
// @Test: type check exceptions (i.e. dynamic casts)
// @Result: 0=41, 1=10, 2=11, 3=12, 4=10, 5=11, 6=12, 7=41

class rtex_type05_a {
    field foo: int;

    constructor(f: int) { 
	foo = f; 
    }
}

class rtex_type05_b extends rtex_type05_a {
    constructor(): super(11) { }
}

class rtex_type05_c extends rtex_type05_a {
    constructor(): super(12) { }
}

component rtex_type05 {
    field a: rtex_type05_a = new rtex_type05_a(10);
    field b: rtex_type05_b = new rtex_type05_b();
    field c: rtex_type05_c = new rtex_type05_c();

    method main(arg: int): int {
	if ( arg == 1 ) return (a::rtex_type05_a).foo;
	if ( arg == 2 ) return (b::rtex_type05_a).foo;
	if ( arg == 3 ) return (c::rtex_type05_a).foo;
	if ( arg == 4 ) return (a::rtex_type05_a).foo;
	if ( arg == 5 ) return (b::rtex_type05_b).foo;
	if ( arg == 6 ) return (c::rtex_type05_c).foo;

	return 41;
    }
}
