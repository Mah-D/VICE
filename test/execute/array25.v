// @Harness: v2-exec
// @Test: field initialization
// @Result: 0=42, 1=13, 2=15, 3=17, 4=42

component array25 {
    field a: (function(): int)[] = { m13, m15, m17 };

    method main(arg: int): int {
	if ( arg == 1 ) return a[0]();
	if ( arg == 2 ) return a[1]();
	if ( arg == 3 ) return a[2]();
	return 42;
    }

    method m13(): int { return 13; }
    method m15(): int { return 15; }
    method m17(): int { return 17; }
}
