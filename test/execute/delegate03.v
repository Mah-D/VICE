// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate03_a {
    field val: int;
    constructor(i: int) { val = i; }
    method getf(): delegate03_a { return this; }
}

class delegate03_b extends delegate03_a {
    constructor(i: int) : super(i) { }
}

class delegate03_c extends delegate03_a {
    constructor(i: int) : super(i) { }
}

component delegate03 {
    field a: delegate03_a = new delegate03_a(11);
    field b: delegate03_a = new delegate03_b(21);
    field c: delegate03_a = new delegate03_c(31);
    field am: function():delegate03_a = a.getf;
    field bm: function():delegate03_a = b.getf;
    field cm: function():delegate03_a = c.getf;

    field f42: function():delegate03_a = new delegate03_a(42).getf;

    method main(arg: int): int {
	if ( arg == 1 ) return am().val;
	if ( arg == 2 ) return bm().val;
	if ( arg == 3 ) return cm().val;

        local m = f42;
	if ( arg == 5 ) m = a.getf;
	if ( arg == 6 ) m = b.getf;
	if ( arg == 7 ) m = c.getf;

	return m().val;
    }
}
