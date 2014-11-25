// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate01_a {
    method val(): int { return 11; }
}

class delegate01_b extends delegate01_a {
    method val(): int { return 21; }
}

class delegate01_c extends delegate01_a {
    method val(): int { return 31; }
}

component delegate01 {
    field a: delegate01_a = new delegate01_a();
    field b: delegate01_a = new delegate01_b();
    field c: delegate01_a = new delegate01_c();
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
