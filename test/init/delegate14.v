// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg14_a {
    method val(): int { return 1; }
}

class dg14_b extends dg14_a {
    method val(): int { return 2; }
}

component dg14 {
    field a01: bool;
    field a02: bool;
    field a03: bool;
    field a04: bool;
    field a05: bool;
    field a06: bool;
    field a07: bool;
    field a08: bool;
    field a09: bool;

    constructor() {
        local ao = new dg14_a();
        local bo = new dg14_b();

	local af = ao.val;
	local bf = bo.val;
	local cf = bar;

	a01 = compare(af, ao.val);
	a02 = compare(bf, bo.val);
	a03 = compare(cf, bar);
	a04 = compare(af, bo.val);
	a05 = compare(bf, ao.val);
	a06 = compare(cf, null);
	a07 = compare(af, new dg14_a().val);
	a08 = compare(bf, new dg14_b().val);
	a09 = compare(cf, bar);
    }

    method compare(f: function(): int, g: function(): int): bool {
	return f == g;
    }

    method bar(): int {
	return 3;
    }
}

/* 
heap {
    record #0:9:dg14 {
	field a01: bool = bool:true;
	field a02: bool = bool:true;
	field a03: bool = bool:true;
	field a04: bool = bool:false;
	field a05: bool = bool:false;
	field a06: bool = bool:false;
	field a07: bool = bool:false;
	field a08: bool = bool:false;
	field a09: bool = bool:true;
    }
} */
