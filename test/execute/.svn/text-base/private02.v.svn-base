// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=11, 3=11, 4=42

class private02_a {
    method getf(): int { return val(); }
    private method val(): int { return 11; }
}

class private02_b extends private02_a {
    private method val(): int { return 21; }
}

class private02_c extends private02_a {
    private method val(): int { return 31; }
}

component private02 {
    field ao: private02_a = new private02_a();
    field bo: private02_a = new private02_b();
    field co: private02_a = new private02_c();

    method main(arg: int): int {
	if ( arg == 1 ) return ao.getf();
	if ( arg == 2 ) return bo.getf();
	if ( arg == 3 ) return co.getf();
	return 42;
    }
}
