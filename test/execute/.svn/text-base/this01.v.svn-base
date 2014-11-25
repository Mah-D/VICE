// @Harness: v2-exec
// @Test: uses of "this"
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42

class this01_1 {
    field foo: int;

    constructor(i: int) {
	foo = i;
    }

    method getf(): int { 
	return this.foo;
    }
}

component this01 {
    field a: this01_1 = new this01_1(11);
    field b: this01_1 = new this01_1(21);
    field c: this01_1 = new this01_1(31);

    method main(arg: int): int {
	if ( arg == 1 ) return a.getf();
	if ( arg == 2 ) return b.getf();
	if ( arg == 3 ) return c.getf();
	return 42;
    }
}
