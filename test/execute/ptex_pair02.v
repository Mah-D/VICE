// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=21, 3=31, 4=32, 5=97, 6=42

class Pair<X, Y> {
    field a: X;
    field b: Y;

    constructor(x: X, y: Y) {
        a = x;
        b = y;
    }
}

class I {
    field val: int;
    constructor(v: int) {
        val = v;
    }
}

component ptex_pair02 {
    field a: Pair<int, Pair<bool, bool> > = new Pair<int, Pair<bool, bool> >(11, new Pair<bool, bool>(true, false));
    field b: Pair<int, I> = new Pair<int, I>(21, new I(31));
    field c: Pair<function(): int, char> = new Pair<function(): int, char>(f32, 'a');

    method main(arg: int): int {
        if ( arg == 1 ) return a.a;
        if ( arg == 2 ) return b.a;
        if ( arg == 3 ) return b.b.val;
        if ( arg == 4 ) return c.a();
        if ( arg == 5 ) return c.b;
	return 42;
    }

    method f32(): int { return 32; }
}
