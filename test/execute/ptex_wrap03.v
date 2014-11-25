// @Harness: v2-exec
// @Test: integer comparison operators
// @Result: 0=42, 1=11, 2=21, 3=48, 4=97, 5=42

class W<T> {
    field val: T;
    constructor(v: T) {
        val = v;
    }
}

component ptex_wrap03 {

    field a: W<int> = new W<int>(11);
    field b: W<int> = new W<int>(21);
    field c: W<char> = new W<char>('0');
    field d: W<char> = new W<char>('a');

    method main(arg: int): int {
	if ( arg == 1 ) return a.val;
	if ( arg == 2 ) return b.val;
	if ( arg == 3 ) return c.val;
	if ( arg == 4 ) return d.val;
	return 42;
    }
}
