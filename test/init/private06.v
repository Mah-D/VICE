// @Harness: v2-init
// @Test: virtual method invocations
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

/* 
heap {
    record #0:6:pv06 {
        field a: function():int = [#1:pv06_a,pv06_a:val()];
        field b: function():int = [#2:pv06_b,pv06_a:val()];
        field c: function():int = [#3:pv06_c,pv06_a:val()];
        field av: int = int:1;
        field bv: int = int:1;
        field cv: int = int:1;
    }
    record #1:0:pv06_a {
    }
    record #2:0:pv06_b {
    }
    record #3:0:pv06_c {
    }
} */
