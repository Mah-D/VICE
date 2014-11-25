// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg09_a {
    method val(): int { return 1; }
}

class dg09_b extends dg09_a {
    method val(): int { return 2; }
}

component dg09 {
    field a: function():int = new dg09_a().val;
    field b: function():int = new dg09_b().val;
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
	if ( f == d ) return 42;
	return 77;
    }
    method bar(): int {
	return 3;
    }

}

/* 
heap {
    record #0:8:dg09 {
        field a: function():int = [#1:dg09_a,dg09_a:val()];
        field b: function():int = [#2:dg09_b,dg09_b:val()];
        field c: function():int = [#0:dg09,dg09:bar()];
        field d: function():int = #null;
        field av: int = int:11;
        field bv: int = int:21;
        field cv: int = int:31;
        field dv: int = int:42;
    }
    record #1:0:dg09_a {
    }
    record #2:0:dg09_b {
    }
} */
