// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42

class private10_a {
    method getf(): int { return val; }
    private field val: int = 11;
}

class private10_b extends private10_a {
    field val: int = 21;
}

class private10_c extends private10_a {
    field val: int = 31;
}

component private10 {
    field a: private10_a = new private10_a();
    field b: private10_b = new private10_b();
    field c: private10_c = new private10_c();

    method main(arg: int): int {
	if ( arg == 1 ) return a.getf();
	if ( arg == 2 ) return b.val;
	if ( arg == 3 ) return c.val;
	return 42;
    }
}
