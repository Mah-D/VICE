// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=15, 4=42

class array11_obj {
    field foo: int;
    constructor(i: int) { foo = i; }
}

component array11 {
    field i: array11_obj = new array11_obj(13);
    field j: array11_obj = new array11_obj(14);
    field k: array11_obj = new array11_obj(15);
    field m: array11_obj = new array11_obj(42);

    field a: array11_obj[] =  { i, j, k };

    method main(arg: int): int {
	if ( arg == 1 ) return getf(0).foo;
	if ( arg == 2 ) return getf(1).foo;
	if ( arg == 3 ) return getf(2).foo;
	return 42;
    }

    method getf(i: int): array11_obj {
	return a[i];
    }
}
