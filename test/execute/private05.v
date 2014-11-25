// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=11, 3=11, 4=42

class private05_a {
    method getf(): int { return val; }
    private field val: int = 11;
}

class private05_b extends private05_a {
    private field val: bool = true;
}

class private05_c extends private05_a {
    private field val: char = '3';
}

component private05 {
    field a: private05_a = new private05_a();
    field b: private05_a = new private05_b();
    field c: private05_a = new private05_c();

    method main(arg: int): int {
	if ( arg == 1 ) return a.getf();
	if ( arg == 2 ) return b.getf();
	if ( arg == 3 ) return c.getf();
	return 42;
    }
}
