// @Harness: v2-seman
// @Test: private fields
// @Result: PASS

class pv05_a {
    method getf(): int { return val; }
    private field val: int = 1;
}

class pv05_b extends pv05_a {
    private field val: bool = true;
}

class pv05_c extends pv05_a {
    private field val: char = '3';
}

component pv05 {
    field av: int = new pv05_a().getf();
    field bv: int = new pv05_b().getf();
    field cv: int = new pv05_c().getf();
}
