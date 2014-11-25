// @Harness: v2-exec
// @Test: dynamically allocated memory exceptions
// @Result: 0=42, 1=AllocationException, 2=AllocationException, 3=AllocationException, 4=42

class rtex_alloc03_obj {
    field foo: int;
    constructor(i: int) { 
	foo = i; 
    }
}

component rtex_alloc03 {

    method foo(): rtex_alloc03_obj {
	return new rtex_alloc03_obj(11);
    }

    method main(arg: int): int {
	if ( arg == 1) return foo().foo;
	if ( arg == 2) return foo().foo;
	if ( arg == 3) return foo().foo;
	return 42;
    }
}
