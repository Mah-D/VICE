// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg05_a {
    method foo(): int { return 1; }
    method bar(): int { return 2; }
}

component dg05 {
    field a: function():int = new dg05_a().foo;
    field b: function():int = new dg05_a().bar;
    field av: int = a();
    field bv: int = b();
}

/* 
heap {
    record #0:4:dg05 {
        field a: function():int = [#1:dg05_a,dg05_a:foo()];
        field b: function():int = [#2:dg05_a,dg05_a:bar()];
        field av: int = int:1;
        field bv: int = int:2;
    }
    record #1:0:dg05_a {
    }
    record #2:0:dg05_a {
    }
} */
