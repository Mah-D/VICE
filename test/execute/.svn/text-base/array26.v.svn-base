// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=15, 3=17, 4=3, 5=42

component array26 {
    field a: (function(): int)[] = { m13, m15, m17 };

    method main(arg: int): int {
	local f = m42;
	if ( arg == 1 ) f = a[0];
	if ( arg == 2 ) f = a[1];
	if ( arg == 3 ) f = a[2];
        if ( arg == 4 ) return a.length;
	return f();
    }

    method m13(): int { return 13; }
    method m15(): int { return 15; }
    method m17(): int { return 17; }
    method m42(): int { return 42; }
}
