// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=11, 3=11, 4=42

class private07_a {
    method getf(): function(): int { return val; }
    private method val(): int { return 11; }
}

class private07_b extends private07_a {
    private method val(a: int): bool { return true; }
}

class private07_c extends private07_a {
    private method val(a: int, b: int): char { return '3'; }
}

component private07 {
    field a: function():int = new private07_a().getf();
    field b: function():int = new private07_b().getf();
    field c: function():int = new private07_c().getf();
    field av: int = a();
    field bv: int = b();
    field cv: int = c();

    method main(arg: int): int {
	if ( arg == 1 ) return a();
	if ( arg == 2 ) return b();
	if ( arg == 3 ) return c();
	return 42;
    }
}
