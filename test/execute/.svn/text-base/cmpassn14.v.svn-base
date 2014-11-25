// @Harness: v2-exec
// @Test: pre/post increment operations
// @Result: 0=0, 1=6, 2=6, 3=1, 4=0

class cmpassn14_obj {
    field foo: int = 8;
}

component cmpassn14 {

    field cnt: int;
    field foo: cmpassn14_obj = new cmpassn14_obj();

    method getfoo(): cmpassn14_obj {
	cnt++;
        return foo;
    }

    method main(arg: int): int {
	local bar = getfoo().foo -= 2;
	if ( arg == 1 ) return foo.foo;
	if ( arg == 2 ) return bar;
	if ( arg == 3 ) return cnt;
	return 0;
    }
}
