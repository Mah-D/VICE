// @Harness: v2-exec
// @Test: null exceptions
// @Result: 0=42, 1=NullCheckException, 2=13, 3=17, 4=NullCheckException, 5=42

component rtex_null11 {
    field a: function(): int;
    field b: function(): int = m13;
    field c: function(): int = m17;
    field d: function(): int;

    method main(arg: int): int {
	if ( arg == 1 ) return a();
	if ( arg == 2 ) return b();
	if ( arg == 3 ) return c();
	if ( arg == 4 ) return d();
	return 42;
    }

    method m13(): int { return 13; }
    method m17(): int { return 17; }
}
