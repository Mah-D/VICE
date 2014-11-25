// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg07_a {
    method getf(): function(): int { return val; }
    method val(): int { return 1; }
}

class dg07_b extends dg07_a {
    method val(): int { return 2; }
}

class dg07_c extends dg07_a {
    method val(): int { return 3; }
}

component dg07 {
    field a: function():int = new dg07_a().getf();
    field b: function():int = new dg07_b().getf();
    field c: function():int = new dg07_c().getf();
    field av: int = a();
    field bv: int = b();
    field cv: int = c();
}

/* 
heap {
    record #0:6:dg07 {
        field a: function():int = [#1:dg07_a,dg07_a:val()];
        field b: function():int = [#2:dg07_b,dg07_b:val()];
        field c: function():int = [#3:dg07_c,dg07_c:val()];
        field av: int = int:1;
        field bv: int = int:2;
        field cv: int = int:3;
    }
    record #1:0:dg07_a {
    }
    record #2:0:dg07_b {
    }
    record #3:0:dg07_c {
    }
} */
