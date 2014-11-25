// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=42, 1=21, 2=11, 3=42

class cmpassn24_obj {
    field x: 8 = 0xb;
    field y: 8 = 0x15;
    method swap() {
	y = (x ^= (y ^= x)) ^ y;
    }
}

component cmpassn24 {
    field foo: cmpassn24_obj = new cmpassn24_obj();

    method main(arg: int): 8 {
	foo.swap();
	if ( arg == 1 ) return foo.x;
	if ( arg == 2 ) return foo.y;
	return 0x2a;
    }
}
