// @Harness: v2-exec
// @Test: integer comparison operators
// @Result: 0=42, 1=0, 2=0, 3=0, 4=0, 5=42

class W<T> {
    field val: T;
}

component ptex_wrap01 {

    field a: W<int> = new W<int>();
    field b: W<int> = new W<int>();
    field c: W<int> = new W<int>();
    field d: W<int> = new W<int>();

    method main(arg: int): int {
	if ( arg == 1 ) return a.val;
	if ( arg == 2 ) return b.val;
	if ( arg == 3 ) return c.val;
	if ( arg == 4 ) return d.val;
	return 42;
    }
}
