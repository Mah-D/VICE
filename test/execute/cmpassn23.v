// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=42, 1=0, 2=11, 3=42

class cmpassn23_obj {
    field x: 8 = 0xb;
    field y: 8 = 0x15;
    method swap() {
	x ^= y ^= x ^= y;
    }
}

component cmpassn23 {
    field foo: cmpassn23_obj = new cmpassn23_obj();

    method main(arg: int): 8 {
	foo.swap();
	if ( arg == 1 ) return foo.x;
	if ( arg == 2 ) return foo.y;
	return 0x2a;
    }
}
