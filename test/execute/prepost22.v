// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=7, 2=7, 3=1, 4=0

class prepost22_obj {
    field foo: int = 6;
}

component prepost22 {
    field cnt: int;
    field foo: prepost22_obj = new prepost22_obj();
    field bar: int;
    
    method obj(): prepost22_obj {
	cnt++; // should only be called once
	return foo;
    }

    method main(arg: int): int {
	bar = ++obj().foo;
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return bar;
	if ( arg == 3 ) return cnt;
	return 0;
    }
}
