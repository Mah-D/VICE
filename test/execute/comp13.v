// @Harness: v2-exec
// @Test: integer comparison operators
// @Result: 0=42, 1=17, 2=18, 3=19, 4=20, 5=42

component comp13 {

    field a: int = 13;
    field b: int = 14;

    method main(arg: int): int {
	if ( arg == 1 ) a = 17;
	if ( arg == 2 ) b = 18;
	if ( arg == 3 ) comp13_b.c = 19;
	if ( arg == 4 ) comp13_b.d = 20;

	if ( arg == 1 ) return a;
	if ( arg == 2 ) return b;
	if ( arg == 3 ) return comp13_b.c;
	if ( arg == 4 ) return comp13_b.d;
	return 42;
    }
}

component comp13_b {
    field c: int = 15;
    field d: int = 16;
}
