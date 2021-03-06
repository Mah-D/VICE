// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=11, 2=21, 3=31, 4=55, 5=42

class this02_1 {
    field foo: int;
    field th: this02_1 = this;

    constructor(i: int) {
	foo = i;
    }
    method getf(): int {
	if ( th == this ) return this.foo;
	else return 55;
    }
}

component this02 {
    field a: this02_1 = new this02_1(11);
    field b: this02_1 = new this02_1(21);
    field c: this02_1 = new this02_1(31);
    field d: this02_1;

    constructor() {
	d = new this02_1(57);
	d.th = null;
    }	

    method main(arg: int): int {
	if ( arg == 1 ) return a.getf();
	if ( arg == 2 ) return b.getf();
	if ( arg == 3 ) return c.getf();
	if ( arg == 4 ) return d.getf();
	return 42;
    }
}
