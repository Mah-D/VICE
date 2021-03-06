// @Harness: v2-exec
// @Test: type check exceptions (i.e. dynamic casts)
// @Result: 0=41, 1=TypeCheckException, 2=42, 3=42, 4=TypeCheckException, 5=TypeCheckException, 6=42, 7=41

class rtex_type01_a {
}

class rtex_type01_b extends rtex_type01_a {
    field foo: int = 42;
}

class rtex_type01_c extends rtex_type01_b {
}

component rtex_type01 {
    field a: rtex_type01_a = new rtex_type01_a();
    field b: rtex_type01_a = new rtex_type01_b();
    field c: rtex_type01_a = new rtex_type01_c();

    method main(arg: int): int {
	if ( arg == 1 ) return (a::rtex_type01_b).foo;
	if ( arg == 2 ) return (b::rtex_type01_b).foo;
	if ( arg == 3 ) return (c::rtex_type01_b).foo;
	if ( arg == 4 ) return (a::rtex_type01_c).foo;
	if ( arg == 5 ) return (b::rtex_type01_c).foo;
	if ( arg == 6 ) return (c::rtex_type01_c).foo;
	return 41;
    }
}
