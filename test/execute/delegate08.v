// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate08_a {
    method getf(): function(): int { return val; }
    method val(): int { return 11; }
}

class delegate08_b extends delegate08_a {
    method val(): int { return 21; }
}

class delegate08_c extends delegate08_a {
    method val(): int { return 31; }
}

component delegate08 {
    field am: function(): function(): int = new delegate08_a().getf;
    field bm: function(): function(): int = new delegate08_b().getf;
    field cm: function(): function(): int = new delegate08_c().getf;

    method main(arg: int): int {
	if ( arg == 1 ) return am()();
	if ( arg == 2 ) return bm()();
	if ( arg == 3 ) return cm()();

        local m = gm42();
	if ( arg == 5 ) m = am();
	if ( arg == 6 ) m = bm();
	if ( arg == 7 ) m = cm();

	return m();
    }

    method gm42(): function(): int {
	return m42;
    }

    method m42(): int {
	return 42;
    }

}
