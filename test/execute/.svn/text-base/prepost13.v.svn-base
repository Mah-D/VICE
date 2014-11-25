// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=6, 2=7, 3=0

class prepost13_obj {
    field foo: int = 6;
    field bar: int;
    
    method inc(): int {
	local i = foo;
        foo = i++;
        return i;
    }
}

component prepost13 {
    field foo: prepost13_obj = new prepost13_obj();

    method main(arg: int): int {
	foo.bar = foo.inc();
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return foo.bar;
	return 0;
    }
}
