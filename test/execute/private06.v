// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=11, 3=11, 4=42

class private06_a {
    method getf(): function(): int { return val; }
    private method val(): int { return 11; }
}

class private06_b extends private06_a {
    private method val(): bool { return true; }
}

class private06_c extends private06_a {
    private method val(): char { return '3'; }
}

component private06 {
    field a: function():int = new private06_a().getf();
    field b: function():int = new private06_b().getf();
    field c: function():int = new private06_c().getf();
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
