// @Harness: v2-seman
// @Test: virtual method invocations
// @Result: PASS

class inh18_a {
    method getf(): int { return val(); }
    private method val(): int { return 1; }
}

class inh18_b extends inh18_a {
    private method val(): int { return 2; }
}

component inh18 {
    field av: int = new inh18_a().getf();
    field bv: int = new inh18_b().getf();
}
