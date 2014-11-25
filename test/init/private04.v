// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class pv04_a {
    method getf(): function(): int { return this.val; }
    private method val(): int { return 1; }
}

class pv04_b extends pv04_a {
    private method val(): int { return 2; }
}

class pv04_c extends pv04_a {
    private method val(): int { return 3; }
}

component pv04 {
    field a: function():int = new pv04_a().getf();
    field b: function():int = new pv04_b().getf();
    field c: function():int = new pv04_c().getf();
    field av: int = a();
    field bv: int = b();
    field cv: int = c();
}

/* 
heap {
    record #0:6:pv04 {
        field a: function():int = [#1:pv04_a,pv04_a:val()];
        field b: function():int = [#2:pv04_b,pv04_a:val()];
        field c: function():int = [#3:pv04_c,pv04_a:val()];
        field av: int = int:1;
        field bv: int = int:1;
        field cv: int = int:1;
    }
    record #1:0:pv04_a {
    }
    record #2:0:pv04_b {
    }
    record #3:0:pv04_c {
    }
} */
