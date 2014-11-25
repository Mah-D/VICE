// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42

class Pair<X, Y> {
    field a: X;
    field b: Y;

    constructor(x: X, y: Y) {
        a = x;
        b = y;
    }
}

component ptex_pair07 {
    field a: int[] = { 21, 31 };
    field b: Pair<int, int[]> = new Pair<int, int[]>(11, a);

    method main(arg: int): int {
        if ( arg == 1 ) return b.a;
        if ( arg == 2 ) return b.b[0];
        if ( arg == 3 ) return b.b[1];
	return 42;
    }
}
