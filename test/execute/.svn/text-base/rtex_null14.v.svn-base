// @Harness: v2-exec
// @Test: null exceptions
// @Result: 0=42, 1=NullCheckException, 2=13, 3=17, 4=NullCheckException, 5=42

component rtex_null14 {
    field a: function(): int;
    field b: function(): int = m13;
    field c: function(): int = m17;
    field d: function(): int;

    method main(arg: int): int {
	local f = m42;
	if ( arg == 1 ) f = a;
	if ( arg == 2 ) f = b;
	if ( arg == 3 ) f = c;
	if ( arg == 4 ) f = d;
	return f();
    }

    method m13(): int { return 13; }
    method m17(): int { return 17; }
    method m42(): int { return 42; }
}
