// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate05_a {
    method foo(): int { return 11; }
    method bar(): int { return 21; }
    method baz(): int { return 31; }
}

component delegate05 {
    field am: function():int = new delegate05_a().foo;
    field bm: function():int = new delegate05_a().bar;
    field cm: function():int = new delegate05_a().baz;

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

    method m42(): int {
	return 42;
    }
}
