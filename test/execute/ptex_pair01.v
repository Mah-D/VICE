// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=21, 3=31, 4=41, 5=32, 6=33, 7=42

class Pair<X, Y> {
    field a: X;
    field b: Y;

    constructor(x: X, y: Y) {
        a = x;
       b = y;
    }
}

component ptex_pair01 {
    field a: Pair<int, int> = new Pair<int, int>(11, 21);
    field b: Pair<char, int> = new Pair<char, int>('c', 31);
    field c: Pair<int, bool> = new Pair<int, bool>(41, true);
    field d: Pair<function(): int, function(): int> = new Pair<function(): int, function(): int>(f32, f33);

    method main(arg: int): int {
        if ( arg == 1 ) return a.a;
        if ( arg == 2 ) return a.b;
        if ( arg == 3 ) return b.b;
        if ( arg == 4 ) return c.a;
        if ( arg == 5 ) return d.a();
        if ( arg == 6 ) return d.b();
	return 42;
    }

    method f32(): int { return 32; }
    method f33(): int { return 33; }
}
