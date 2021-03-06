// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=42, 3=31, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate11_a {
    method val(): int { return 11; }
}

class delegate11_b extends delegate11_a {
    method val(): int { return 21; }
}

class delegate11_c extends delegate11_a {
    method val(): int { return 31; }
}

component delegate11 {
    field a: delegate11_a = new delegate11_a();
    field b: delegate11_a = new delegate11_b();
    field c: delegate11_a = new delegate11_c();
    field am: function():int = a.val;
    field bm: function():int;
    field cm: function():int = c.val;

    method main(arg: int): int {
        local m = m42;
	if ( arg == 1 ) m = am;
	if ( arg == 2 ) m = bm;
	if ( arg == 3 ) m = cm;

	if ( arg == 5 ) m = a.val;
	if ( arg == 6 ) m = b.val;
	if ( arg == 7 ) m = c.val;

	return exec(m);
    }

    method exec(f: function(): int): int {
	if ( f == null ) return 42;
	return f();
    }

    method m42(): int {
	return 42;
    }
}
