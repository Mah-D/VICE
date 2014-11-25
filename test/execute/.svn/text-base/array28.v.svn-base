// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=15, 4=42

class array28_obj {
    field foo: int;
    constructor(i: int) { foo = i; }
}

component array28 {
    field i: array28_obj = new array28_obj(13);
    field j: array28_obj = new array28_obj(14);
    field k: array28_obj = new array28_obj(15);
    field m: array28_obj = new array28_obj(42);

    field a: array28_obj[] =  { i, j, k };

    method main(arg: int): int {
	local o = m;
	if ( arg == 1 ) o = a[0];
	if ( arg == 2 ) o = a[1];
	if ( arg == 3 ) o = a[2];
	return o.foo;
    }
}
