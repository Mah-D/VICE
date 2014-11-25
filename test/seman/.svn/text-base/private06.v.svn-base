// @Harness: v2-seman
// @Test: private methods with overridden return type
// @Result: PASS

class pv06_a {
    method getf(): function(): int { return val; }
    private method val(): int { return 1; }
}

class pv06_b extends pv06_a {
    private method val(): bool { return true; }
}

class pv06_c extends pv06_a {
    private method val(): char { return '3'; }
}

component pv06 {
    field a: function():int = new pv06_a().getf();
    field b: function():int = new pv06_b().getf();
    field c: function():int = new pv06_c().getf();
    field av: int = a();
    field bv: int = b();
    field cv: int = c();
}
