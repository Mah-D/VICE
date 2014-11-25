// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg04_a {
    method val(): int { return 1; }
}

class dg04_b extends dg04_a {
    method val(): int { return 2; }
}

component dg04 {
    field a: function():int = new dg04_a().val;
    field b: function():int = new dg04_b().val;
    field c: function():int = val;
    field av: int = a();
    field bv: int = b();
    field cv: int = c();
    method val(): int {
	return 3;
    }
}

/* 
heap {
    record #0:6:dg04 {
        field a: function():int = [#1:dg04_a,dg04_a:val()];
        field b: function():int = [#2:dg04_b,dg04_b:val()];
        field c: function():int = [#0:dg04,dg04:val()];
        field av: int = int:1;
        field bv: int = int:2;
        field cv: int = int:3;
    }
    record #1:0:dg04_a {
    }
    record #2:0:dg04_b {
    }
} */
