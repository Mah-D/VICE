// @Harness: v2-exec
// @Test: integer comparison operators
// @Result: 0=42, 1=17, 2=18, 3=19, 4=20, 5=42

component comp15 {

    field a: int = 13;
    field b: int = 14;

    method main(arg: int): int {
	if ( arg == 1 ) a = 17;
	if ( arg == 2 ) b = 18;
	if ( arg == 3 ) comp15_b.a = 19;
	if ( arg == 4 ) comp15_b.b = 20;

	if ( arg == 1 ) return a;
	if ( arg == 2 ) return b;
	if ( arg == 3 ) return comp15_b.a;
	if ( arg == 4 ) return comp15_b.b;
	return 42;
    }
}

component comp15_b {
    field a: int = 15;
    field b: int = 16;
}
