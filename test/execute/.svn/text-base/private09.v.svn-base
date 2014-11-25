// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=11, 3=11, 4=42

class private09_a {
    method getf(): int { return val; }
    private field val: int = 11;
}

class private09_b extends private09_a {
    field val: int = 21;
}

class private09_c extends private09_a {
    field val: int = 31;
}

component private09 {
    field a: private09_a = new private09_a();
    field b: private09_a = new private09_b();
    field c: private09_a = new private09_c();

    method main(arg: int): int {
	if ( arg == 1 ) return a.getf();
	if ( arg == 2 ) return b.getf();
	if ( arg == 3 ) return c.getf();
	return 42;
    }
}
