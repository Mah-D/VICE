// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=7, 2=6, 3=1, 4=0

class prepost21_obj {
    field foo: int = 6;
}

component prepost21 {
    field cnt: int;
    field foo: prepost21_obj = new prepost21_obj();
    field bar: int;
    
    method obj(): prepost21_obj {
	cnt++; // should only be called once
	return foo;
    }

    method main(arg: int): int {
	bar = obj().foo++;
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return bar;
	if ( arg == 3 ) return cnt;
	return 0;
    }
}
