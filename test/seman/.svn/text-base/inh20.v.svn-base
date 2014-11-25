// @Harness: v2-seman
// @Test: virtual method invocations
// @Result: PASS

class inh20_a {
    method getf(): function(): int { return this.val; }
    private method val(): int { return 1; }
}

class inh20_b extends inh20_a {
    private method val(): int { return 2; }
}

component inh20 {
    field a: function():int = new inh20_a().getf();
    field b: function():int = new inh20_b().getf();
    field av: int = a();
    field bv: int = b();
}
