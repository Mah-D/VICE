// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=14, 3=15, 4=42

class array23_obj {
    field foo: int;
    constructor(i: int) { foo = i; }
}

component array23 {
    field i: array23_obj = new array23_obj(13);
    field j: array23_obj = new array23_obj(14);
    field k: array23_obj = new array23_obj(15);
    field m: array23_obj = new array23_obj(42);

    field a: array23_obj[][] =  { {i}, {j}, {k} };
    field ma: array23_obj[] = { m };

    method main(arg: int): int {
        local r = ma;
	if ( arg == 1 ) r = a[0];
	if ( arg == 2 ) r = a[1];
	if ( arg == 3 ) r = a[2];
	return r[0].foo;
    }
}
