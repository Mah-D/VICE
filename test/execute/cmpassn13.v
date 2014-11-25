// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=8, 2=1, 3=0

class cmpassn13_obj {
    field foo: int = 6;
}

component cmpassn13 {

    field cnt: int;
    field foo: cmpassn13_obj = new cmpassn13_obj();

    method getfoo(): cmpassn13_obj {
	cnt++;
        return foo;
    }

    method main(arg: int): int {
	getfoo().foo += 2;
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return cnt;
	return 0;
    }
}
