// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=15, 4=42

class array12_obj {
    field foo: int;
    constructor(i: int) { foo = i; }
}

component array12 {
    field i: array12_obj = new array12_obj(13);
    field j: array12_obj = new array12_obj(14);
    field k: array12_obj = new array12_obj(15);
    field m: array12_obj = new array12_obj(42);

    field a: array12_obj[][] =  { {i}, {j, k} };

    method main(arg: int): int {
	if ( arg == 1 ) return getf(0)[0].foo;
	if ( arg == 2 ) return getf(1)[0].foo;
	if ( arg == 3 ) return getf(1)[1].foo;
	return 42;
    }

    method getf(i: int): array12_obj[] {
	return a[i];
    }
}
