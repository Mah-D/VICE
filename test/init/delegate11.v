// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg11_a {
    method val(): int { return 1; }
}

class dg11_b extends dg11_a {
    method val(): int { return 2; }
}

component dg11 {
    field a: function():int = new dg11_a().val;
    field b: function():int = new dg11_b().val;
    field c: function():int = bar;
    field d: function():int = null;
    field av: int = val(a);
    field bv: int = val(b);
    field cv: int = val(c);
    field dv: int = val(d);

    method val(f: function(): int): int {
	if ( f == a ) return 11;
	if ( f == b ) return 21;
	if ( f == c ) return 31;
	if ( f == null ) return 42;
	return 77;
    }
    method bar(): int {
	return 3;
    }

}

/* 
heap {
    record #0:8:dg11 {
        field a: function():int = [#1:dg11_a,dg11_a:val()];
        field b: function():int = [#2:dg11_b,dg11_b:val()];
        field c: function():int = [#0:dg11,dg11:bar()];
        field d: function():int = #null;
        field av: int = int:11;
        field bv: int = int:21;
        field cv: int = int:31;
        field dv: int = int:42;
    }
    record #1:0:dg11_a {
    }
    record #2:0:dg11_b {
    }
} */
