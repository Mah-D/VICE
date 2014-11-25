// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=11, 3=31, 4=42, 5=11, 6=11, 7=31, 8=42

class delegate02_a {
    method val(): int { return 11; }
}

class delegate02_b extends delegate02_a {
}

class delegate02_c extends delegate02_a {
    method val(): int { return 31; }
}

component delegate02 {
    field a: delegate02_a = new delegate02_a();
    field b: delegate02_a = new delegate02_b();
    field c: delegate02_a = new delegate02_c();
    field am: function():int = a.val;
    field bm: function():int = b.val;
    field cm: function():int = c.val;

    method main(arg: int): int {
	if ( arg == 1 ) return am();
	if ( arg == 2 ) return bm();
	if ( arg == 3 ) return cm();

        local m = m42;
	if ( arg == 5 ) m = a.val;
	if ( arg == 6 ) m = b.val;
	if ( arg == 7 ) m = c.val;

	return m();
    }

    method m42(): int {
	return 42;
    }
}
