// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42, 5=11, 6=21, 7=31, 8=42

class delegate07_a {
    method getf(): function(): int { return val; }
    method val(): int { return 11; }
}

class delegate07_b extends delegate07_a {
    method val(): int { return 21; }
}

class delegate07_c extends delegate07_a {
    method val(): int { return 31; }
}

component delegate07 {
    field am: function(): function(): int = new delegate07_a().getf;
    field bm: function(): function(): int = new delegate07_b().getf;
    field cm: function(): function(): int = new delegate07_c().getf;

    method main(arg: int): int {
	if ( arg == 1 ) return am()();
	if ( arg == 2 ) return bm()();
	if ( arg == 3 ) return cm()();

        local m = gm42;
	if ( arg == 5 ) m = am;
	if ( arg == 6 ) m = bm;
	if ( arg == 7 ) m = cm;

	return m()();
    }

    method gm42(): function(): int {
	return m42;
    }

    method m42(): int {
	return 42;
    }

}
