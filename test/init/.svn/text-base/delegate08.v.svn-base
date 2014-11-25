// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg08_a {
    method val(): int { return 1; }
}

class dg08_b extends dg08_a {
    method val(): int { return 2; }
}

component dg08 {
    field a: function():int = new dg08_a().val;
    field b: function():int = new dg08_b().val;
    field c: function():int = bar;
    field d: function():int;
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
    record #0:7:dg08 {
        field a: function():int = [#1:dg08_a,dg08_a:val()];
        field b: function():int = [#2:dg08_b,dg08_b:val()];
        field c: function():int = [#0:dg08,dg08:bar()];
        field d: function():int = #null;
        field av: int = int:4;
        field bv: int = int:5;
        field cv: int = int:6;
    }
    record #1:0:dg08_a {
    }
    record #2:0:dg08_b {
    }
} */
