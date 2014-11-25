// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate04_a {
    method val(): int { return 11; }
}

class delegate04_b extends delegate04_a {
    method val(): int { return 21; }
}

component delegate04 {
    field am: function():int = new delegate04_a().val;
    field bm: function():int = new delegate04_b().val;
    field cm: function():int = val;

    method main(arg: int): int {
	if ( arg == 1 ) return am();
	if ( arg == 2 ) return bm();
	if ( arg == 3 ) return cm();

        local m = m42;
	if ( arg == 5 ) m = am;
	if ( arg == 6 ) m = bm;
	if ( arg == 7 ) m = cm;

	return m();
    }

    method val(): int {
	return 31;
    }

    method m42(): int {
	return 42;
    }
}
