// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=15, 4=42

class array13_obj {
    field foo: int;
    constructor(i: int) { foo = i; }
}

component array13 {
    field i: array13_obj = new array13_obj(13);
    field j: array13_obj = new array13_obj(14);
    field k: array13_obj = new array13_obj(15);
    field m: array13_obj = new array13_obj(42);

    field a: array13_obj[][] =  { {i}, {j, k} };

    method main(arg: int): int {
	if ( arg == 1 ) return a[0][0].foo;
	if ( arg == 2 ) return a[1][0].foo;
	if ( arg == 3 ) return a[1][1].foo;
	return 42;
    }

    method getf(i: int): array13_obj[] {
	return a[i];
    }
}
