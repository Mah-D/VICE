// @Harness: v2-init
// @Test: virtual method invocations
// @Result: PASS

class dg15_a {
    method val(): int { return 1; }
}

class dg15_b extends dg15_a {
    method val(): int { return 2; }
}

component dg15 {
    field b: bool[];

    constructor() {
        b = new bool[9];
        local ao = new dg15_a();
        local bo = new dg15_b();

	local af = ao.val;
	local bf = bo.val;
	local cf = bar;

	local f = { ao.val, bo.val, bar, bo.val, ao.val, null, 
	            new dg15_a().val, new dg15_b().val, bar };

	b[0] = compare(af, f[0]);
	b[1] = compare(bf, f[1]);
	b[2] = compare(cf, f[2]);
	b[3] = compare(af, f[3]);
	b[4] = compare(bf, f[4]);
	b[5] = compare(cf, f[5]);
	b[6] = compare(af, f[6]);
	b[7] = compare(bf, f[7]);
	b[8] = compare(cf, f[8]);
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
    record #0:1:dg15 {
	field b: bool[] = #1:bool[9];
    }
    record #1:9:bool[9] {
	field 0: bool = bool:true;
	field 1: bool = bool:true;
	field 2: bool = bool:true;
	field 3: bool = bool:false;
	field 4: bool = bool:false;
	field 5: bool = bool:false;
	field 6: bool = bool:false;
	field 7: bool = bool:false;
	field 8: bool = bool:true;
    }
} */
