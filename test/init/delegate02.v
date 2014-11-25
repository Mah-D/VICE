// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg02_a {
    method val(): int { return 1; }
}

class dg02_b extends dg02_a {
}

class dg02_c extends dg02_a {
    method val(): int { return 3; }
}

component dg02 {
    field a: function():int = new dg02_a().val;
    field b: function():int = new dg02_b().val;
    field c: function():int = new dg02_c().val;
    field av: int = a();
    field bv: int = b();
    field cv: int = c();
}

/* 
heap {
    record #0:6:dg02 {
        field a: function():int = [#1:dg02_a,dg02_a:val()];
        field b: function():int = [#2:dg02_b,dg02_a:val()];
        field c: function():int = [#3:dg02_c,dg02_c:val()];
        field av: int = int:1;
        field bv: int = int:1;
        field cv: int = int:3;
    }
    record #1:0:dg02_a {
    }
    record #2:0:dg02_b {
    }
    record #3:0:dg02_c {
    }
} */
