// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=42, 1=11, 2=21, 3=31, 4=42

class private08_a {
    private method val(): int { return 11; }
}

class private08_b extends private08_a {
    method val(a: int): int { return 21; }
}

class private08_c extends private08_a {
    method val(a: int): int { return 31; }
}

component private08 {
    field b: function(int):int = new private08_b().val;
    field c: function(int):int = new private08_c().val;

    method main(arg: int): int {
	if ( arg == 1 ) return 11;
	if ( arg == 2 ) return b(0);
	if ( arg == 3 ) return c(0);
	return 42;
    }
}
