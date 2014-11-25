// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=15, 4=16, 5=42

class field02b_obj {
    field a: int;
    field b: int;
    field th: field02b_obj = this;

    constructor() {
        a = 15;
	b = 16;
    }
}

component field02b {
    field foo: field02b_obj = new field02b_obj();

    method main(arg: int): int {
	if ( arg == 1 ) return foo != null ? 13 : 77;
	if ( arg == 2 ) return foo.th == foo ? 14 : 77;
	if ( arg == 3 ) return foo.a;
	if ( arg == 4 ) return foo.b;
	return 42;
    }
}
