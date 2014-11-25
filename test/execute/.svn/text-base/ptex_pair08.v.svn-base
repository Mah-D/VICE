// @Harness: v2-exec
// @Result: 0=42, 1=11, 2=21, 3=31, 4=NullCheckException, 5=NullCheckException, 6=42

class Pair<X, Y> {
    field a: X;
    field b: Y;

    constructor(x: X, y: Y) {
        a = x;
        b = y;
    }
}

component ptex_pair08 {
    field a: int[] = { 21, 31 };
    field b: Pair<int, int[]> = new Pair<int, int[]>(11, a);
    field c: Pair<int, int>[] = new Pair<int, int>[3]; 

    method main(arg: int): int {
        if ( arg == 1 ) return b.a;
        if ( arg == 2 ) return b.b[0];
        if ( arg == 3 ) return b.b[1];
        if ( arg == 4 ) return c[0].a;
        if ( arg == 5 ) return c[1].b;
	return 42;
    }
}
