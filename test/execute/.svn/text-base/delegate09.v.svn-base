// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=42, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate09_a {
    method val(): int { return 11; }
}

class delegate09_b extends delegate09_a {
    method val(): int { return 21; }
}

class delegate09_c extends delegate09_a {
    method val(): int { return 31; }
}

component delegate09 {
    field a: delegate09_a = new delegate09_a();
    field b: delegate09_a = new delegate09_b();
    field c: delegate09_a = new delegate09_c();
    field am: function():int = a.val;
    field bm: function():int = b.val;
    field cm: function():int;

    method main(arg: int): int {
	if ( arg == 1 ) return am();
	if ( arg == 2 ) return bm();
	//if ( arg == 3 ) return cm();

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
