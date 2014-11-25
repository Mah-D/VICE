// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=15, 4=42

class array27_obj {
    field foo: int;
    constructor(i: int) { foo = i; }
}

component array27 {
    field i: array27_obj = new array27_obj(13);
    field j: array27_obj = new array27_obj(14);
    field k: array27_obj = new array27_obj(15);

    field a: array27_obj[] =  { i, j, k };

    method main(arg: int): int {
	if ( arg == 1 ) return a[0].foo;
	if ( arg == 2 ) return a[1].foo;
	if ( arg == 3 ) return a[2].foo;
	return 42;
    }
}
