// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class pv01_a {
    method getf(): function(): int { return val; }
    private method val(): int { return 1; }
}

class pv01_b extends pv01_a {
    private method val(): int { return 2; }
}

class pv01_c extends pv01_a {
    private method val(): int { return 3; }
}

component pv01 {
    field a: function():int = new pv01_a().getf();
    field b: function():int = new pv01_b().getf();
    field c: function():int = new pv01_c().getf();
    field av: int = a();
    field bv: int = b();
    field cv: int = c();
}

/* 
heap {
    record #0:6:pv01 {
        field a: function():int = [#1:pv01_a,pv01_a:val()];
        field b: function():int = [#2:pv01_b,pv01_a:val()];
        field c: function():int = [#3:pv01_c,pv01_a:val()];
        field av: int = int:1;
        field bv: int = int:1;
        field cv: int = int:1;
    }
    record #1:0:pv01_a {
    }
    record #2:0:pv01_b {
    }
    record #3:0:pv01_c {
    }
} */
