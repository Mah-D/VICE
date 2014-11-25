// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg10_a {
    method val(): int { return 1; }
}

class dg10_b extends dg10_a {
    method val(): int { return 2; }
}

component dg10 {
    field a: function():int = new dg10_a().val;
    field b: function():int = new dg10_b().val;
    field c: function():int = bar;
    field d: function():int;
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
    record #0:8:dg10 {
        field a: function():int = [#1:dg10_a,dg10_a:val()];
        field b: function():int = [#2:dg10_b,dg10_b:val()];
        field c: function():int = [#0:dg10,dg10:bar()];
        field d: function():int = #null;
        field av: int = int:11;
        field bv: int = int:21;
        field cv: int = int:31;
        field dv: int = int:42;
    }
    record #1:0:dg10_a {
    }
    record #2:0:dg10_b {
    }
} */
