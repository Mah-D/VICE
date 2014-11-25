// @Harness: v2-exec
// @Test: type check exceptions (i.e. dynamic casts)
// @Result: 0=41, 1=TypeCheckException, 2=11, 3=TypeCheckException, 4=TypeCheckException, 5=TypeCheckException, 6=12, 7=41

class rtex_type02_a {
    field foo: int;

    constructor(f: int) { 
	foo = f; 
    }
}

class rtex_type02_b extends rtex_type02_a {
    constructor(): super(11) { }
}

class rtex_type02_c extends rtex_type02_a {
    constructor(): super(12) { }
}

component rtex_type02 {
    field a: rtex_type02_a = new rtex_type02_a(10);
    field b: rtex_type02_a = new rtex_type02_b();
    field c: rtex_type02_a = new rtex_type02_c();

    method main(arg: int): int {
	if ( arg == 1 ) return (a::rtex_type02_b).foo;
	if ( arg == 2 ) return (b::rtex_type02_b).foo;
	if ( arg == 3 ) return (c::rtex_type02_b).foo;
	if ( arg == 4 ) return (a::rtex_type02_c).foo;
	if ( arg == 5 ) return (b::rtex_type02_c).foo;
	if ( arg == 6 ) return (c::rtex_type02_c).foo;
	return 41;
    }
}
