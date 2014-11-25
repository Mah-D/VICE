// @Harness: v2-exec
// @Test: integer comparison operators
// @Result: 0=42, 1=11, 2=21, 3=31, 4=37, 5=41, 6=42

class W<T> {
    field val: T;
    constructor(v: T) {
        val = v;
    }
}

component ptex_wrap07 {

    field a: W<int> = new W<int>(11);
    field b: W<int> = new W<int>(21);
    field c: W<W<int> > = new W<W<int> >(new W<int>(31));
    field d: W<W<int> > = new W<W<int> >(new W<int>(37));
    field e: W<W<function(): int> > = new W<W<function(): int> >(new W<function(): int>(m41));

    method main(arg: int): int {
	if ( arg == 1 ) return a.val;
	if ( arg == 2 ) return b.val;
	if ( arg == 3 ) return c.val.val;
	if ( arg == 4 ) return d.val.val;
	if ( arg == 5 ) return e.val.val();
	return 42;
    }

    method m41(): int {
        return 41;
    }
}
