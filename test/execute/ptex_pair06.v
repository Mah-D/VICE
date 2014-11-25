// @Harness: v2-exec
// @Result: 0=42, 1=21, 2=31, 3=41, 4=42

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

component ptex_pair06 {
    field a: Pair<int, I> = new Pair<int, I>(21, new I(31));
    field b: I = new I(41);

    method main(arg: int): int {
        if ( arg == 1 ) return a.a;
        if ( arg == 2 ) return a.b.val;
        if ( arg == 3 ) return b.val;
	return 42;
    }
}
