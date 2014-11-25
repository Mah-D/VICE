// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg06_a {
    method val(): int { return 1; }
}

class dg06_b extends dg06_a {
    method val(): int { return 2; }
}

component dg06 {
    field a: function():int = new dg06_a().val;
    field b: function():int = new dg06_b().val;
    field c: function():int = bar;
    field av: int = val(a);
    field bv: int = val(b);
    field cv: int = val(c);
    method val(f: function(): int): int {
	return 3 + f();
    }
    method bar(): int {
	return 3;
    }

}

/* 
heap {
    record #0:6:dg06 {
        field a: function():int = [#1:dg06_a,dg06_a:val()];
        field b: function():int = [#2:dg06_b,dg06_b:val()];
        field c: function():int = [#0:dg06,dg06:bar()];
        field av: int = int:4;
        field bv: int = int:5;
        field cv: int = int:6;
    }
    record #1:0:dg06_a {
    }
    record #2:0:dg06_b {
    }
} */
