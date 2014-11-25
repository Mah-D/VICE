// @Harness: v2-seman
// @Test: virtual method invocations
// @Result: PASS

class inh21_a {
    private field val: int = 1;
}

class inh21_b extends inh21_a {
    field val: int = 2;
}

component inh21 {
    field bv: int = new inh21_b().val;
}
