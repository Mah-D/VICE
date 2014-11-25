// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate14_a {
    method val(): int { return 11; }
}

class delegate14_b extends delegate14_a {
    method val(): int { return 21; }
}

class delegate14_c extends delegate14_a {
    method val(): int { return 31; }
}

component delegate14 {
    field a: delegate14_a = new delegate14_a();
    field b: delegate14_a = new delegate14_b();
    field c: delegate14_a = new delegate14_c();
    field am: function():int = a.val;
    field bm: function():int;
    field cm: function():int = c.val;

    method main(arg: int): int {
	if ( arg == 1 ) return exec(am);
	if ( arg == 2 ) return exec(null);
	if ( arg == 3 ) return exec(cm);

	if ( arg == 5 ) return exec(a.val);
	if ( arg == 6 ) return exec(b.val);
	if ( arg == 7 ) return exec(c.val);

	return exec(m42);
    }

    method exec(f: function(): int): int {
	if ( f == am ) return 11;
	if ( f == bm ) return 21;
	if ( f == cm ) return 31;
	if ( f == null ) return 42;
	return f();
    }

    method m42(): int { return 42; }
}
