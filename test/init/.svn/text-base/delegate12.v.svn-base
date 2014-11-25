// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg12_a {
    method val(): int { return 1; }
}

class dg12_b extends dg12_a {
    method val(): int { return 2; }
}

component dg12 {
    field a: function():int = new dg12_a().val;
    field b: function():int = new dg12_b().val;
    field c: function():int = bar;
    field av: int;
    field bv: int;
    field cv: int;
    field dv: int;

    constructor() {
	local f: function(): int;
	f = a; av = val(f);
	f = b; bv = val(f);
	f = c; cv = val(f);
	f = null; dv = val(f);
    }

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
    record #0:7:dg12 {
        field a: function():int = [#1:dg12_a,dg12_a:val()];
        field b: function():int = [#2:dg12_b,dg12_b:val()];
        field c: function():int = [#0:dg12,dg12:bar()];
        field av: int = int:11;
        field bv: int = int:21;
        field cv: int = int:31;
        field dv: int = int:42;
    }
    record #1:0:dg12_a {
    }
    record #2:0:dg12_b {
    }
} */
