// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=11, 2=21, 3=31, 4=58, 5=59, 6=60, 7=42

class this03_1 {
    field foo: int;

    constructor(i: int) { foo = i; }

    method equal(o: this03_1, a: int): int { 
	return o == this ? foo : a; 
    }
}

component this03 {
    field a: this03_1 = new this03_1(11);
    field b: this03_1 = new this03_1(21);
    field c: this03_1 = new this03_1(31);

    method main(arg: int): int {
	if ( arg == 1 ) return a.equal(a, 55);
	if ( arg == 2 ) return b.equal(b, 56);
	if ( arg == 3 ) return c.equal(c, 57);
	if ( arg == 4 ) return a.equal(c, 58);
	if ( arg == 5 ) return b.equal(a, 59);
	if ( arg == 6 ) return c.equal(b, 60);
	return 42;
    }
}
