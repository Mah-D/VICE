// @Harness: v2-exec
// @Test: integer comparison operators
// @Result: 0=42, 1=0, 2=0, 3=NullCheckException, 4=0, 5=42

class W<T> {
    field val: T;
}

component ptex_wrap08 {

    field a: W<int> = new W<int>();
    field b: W<int> = new W<int>();
    field c: W<int[]> = new W<int[]>();
    field d: W<char> = new W<char>();

    method main(arg: int): int {
	if ( arg == 1 ) return a.val;
	if ( arg == 2 ) return b.val;
	if ( arg == 3 ) return c.val[0];
	if ( arg == 4 ) return d.val;
	return 42;
    }
}
