// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate13_a {
    method val(): int { return 11; }
}

class delegate13_b extends delegate13_a {
    method val(): int { return 21; }
}

class delegate13_c extends delegate13_a {
    method val(): int { return 31; }
}

component delegate13 {
    field a: delegate13_a = new delegate13_a();
    field b: delegate13_a = new delegate13_b();
    field c: delegate13_a = new delegate13_c();
    field am: function():int = a.val;
    field bm: function():int;
    field cm: function():int = c.val;

    method main(arg: int): int {
        local m = m42;
	if ( arg == 1 ) m = am;
	if ( arg == 2 ) m = null;
	if ( arg == 3 ) m = cm;

	if ( arg == 5 ) m = a.val;
	if ( arg == 6 ) m = b.val;
	if ( arg == 7 ) m = c.val;

	return exec(m);
    }

    method exec(f: function(): int): int {
	if ( f == am ) return 11;
	if ( f == bm ) return 21;
	if ( f == cm ) return 31;
	if ( f == null ) return 42;
	return f();
    }

    method m42(): int {
	return 42;
    }
}
