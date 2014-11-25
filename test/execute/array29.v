// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=15, 4=42

class array29_obj {
    field foo: int;
    constructor(i: int) { foo = i; }
}

component array29 {
    field i: array29_obj = new array29_obj(13);
    field j: array29_obj = new array29_obj(14);
    field k: array29_obj = new array29_obj(15);
    field m: array29_obj = new array29_obj(42);

    field a: array29_obj[] =  { i, j, k };

    method main(arg: int): int {
	if ( arg == 1 ) return getf(a[0]);
	if ( arg == 2 ) return getf(a[1]);
	if ( arg == 3 ) return getf(a[2]);
	return 42;
    }

    method getf(o: array29_obj): int {
	return o.foo;
    }
}
