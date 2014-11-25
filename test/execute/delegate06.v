// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate06_a {
    method val(): int { return 4; }
}

class delegate06_b extends delegate06_a {
    method val(): int { return 14; }
}

component delegate06 {
    field am: function():int = new delegate06_a().val;
    field bm: function():int = new delegate06_b().val;
    field cm: function():int = m24;

    method main(arg: int): int {
	if ( arg == 1 ) return apply(am);
	if ( arg == 2 ) return apply(bm);
	if ( arg == 3 ) return apply(cm);

        local m = m35;
	if ( arg == 5 ) m = am;
	if ( arg == 6 ) m = bm;
	if ( arg == 7 ) m = cm;

	return apply(m);
    }

    method m35(): int {
	return 35;
    }

    method apply(f: function(): int): int {
	return 7 + f();
    }

    method m24(): int {
	return 24;
    }
}
