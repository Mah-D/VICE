// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=11, 3=11, 4=42

class private03_a {
    method getf(): int { return val; }
    private field val: int = 11;
}

class private03_b extends private03_a {
    private field val: int = 21;
}

class private03_c extends private03_a {
    private field val: int = 31;
}

component private03 {
    field a: private03_a = new private03_a();
    field b: private03_a = new private03_b();
    field c: private03_a = new private03_c();

    method main(arg: int): int {
	if ( arg == 1 ) return a.getf();
	if ( arg == 2 ) return b.getf();
	if ( arg == 3 ) return c.getf();
	return 42;
    }
}
