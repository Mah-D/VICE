// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class pv02_a {
    method getf(): int { return val(); }
    private method val(): int { return 1; }
}

class pv02_b extends pv02_a {
    private method val(): int { return 2; }
}

class pv02_c extends pv02_a {
    private method val(): int { return 3; }
}

component pv02 {
    field av: int = new pv02_a().getf();
    field bv: int = new pv02_b().getf();
    field cv: int = new pv02_c().getf();
}

/* 
heap {
    record #0:3:pv02 {
        field av: int = int:1;
        field bv: int = int:1;
        field cv: int = int:1;
    }
} */
