// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=11, 3=11, 4=42

class private04_a {
    method getf(): function(): int { return this.val; }
    private method val(): int { return 11; }
}

class private04_b extends private04_a {
    private method val(): int { return 21; }
}

class private04_c extends private04_a {
    private method val(): int { return 31; }
}

component private04 {
    field a: function():int = new private04_a().getf();
    field b: function():int = new private04_b().getf();
    field c: function():int = new private04_c().getf();

    method main(arg: int): int {
	if ( arg == 1 ) return a();
	if ( arg == 2 ) return b();
	if ( arg == 3 ) return c();
	return 42;
    }
}
