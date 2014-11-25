// @Harness: v2-seman
// @Test: virtual method invocations
// @Result: PASS

class inh17_a {
    method getf(): function(): int { return val; }
    private method val(): int { return 1; }
}

class inh17_b extends inh17_a {
    private method val(): int { return 2; }
}

component inh17 {
    field a: function():int = new inh17_a().getf();
    field b: function():int = new inh17_b().getf();
}
