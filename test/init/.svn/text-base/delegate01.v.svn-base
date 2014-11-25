// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg01_a {
    method val(): int { return 1; }
}

class dg01_b extends dg01_a {
    method val(): int { return 2; }
}

class dg01_c extends dg01_a {
    method val(): int { return 3; }
}

component dg01 {
    field a: function():int = new dg01_a().val;
    field b: function():int = new dg01_b().val;
    field c: function():int = new dg01_c().val;
    field av: int = a();
    field bv: int = b();
    field cv: int = c();
}

/* 
heap {
    record #0:6:dg01 {
        field a: function():int = [#1:dg01_a,dg01_a:val()];
        field b: function():int = [#2:dg01_b,dg01_b:val()];
        field c: function():int = [#3:dg01_c,dg01_c:val()];
        field av: int = int:1;
        field bv: int = int:2;
        field cv: int = int:3;
    }
    record #1:0:dg01_a {
    }
    record #2:0:dg01_b {
    }
    record #3:0:dg01_c {
    }
} */
