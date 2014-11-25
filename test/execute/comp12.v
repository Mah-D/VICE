// @Harness: v2-exec
// @Test: integer comparison operators
// @Result: 0=42, 1=13, 2=14, 3=15, 4=16, 5=42

component comp12 {

    field a: int = 13;
    field b: int = 14;

    method main(arg: int): int {
	if ( arg == 1 ) return a;
	if ( arg == 2 ) return b;
	if ( arg == 3 ) return comp12_b.c;
	if ( arg == 4 ) return comp12_b.d;
	return 42;
    }
}

component comp12_b {
    field c: int = 15;
    field d: int = 16;
}
