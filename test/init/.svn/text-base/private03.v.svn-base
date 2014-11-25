// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class pv03_a {
    method getf(): int { return val; }
    private field val: int = 1;
}

class pv03_b extends pv03_a {
    private field val: int = 2;
}

class pv03_c extends pv03_a {
    private field val: int = 3;
}

component pv03 {
    field av: int = new pv03_a().getf();
    field bv: int = new pv03_b().getf();
    field cv: int = new pv03_c().getf();
}

/* 
heap {
    record #0:3:pv03 {
        field av: int = int:1;
        field bv: int = int:1;
        field cv: int = int:1;
    }
} */
