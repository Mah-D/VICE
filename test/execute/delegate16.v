// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=0, 1=1, 2=1, 3=1, 4=0, 5=0, 6=0, 7=0, 8=0, 9=1, 10=0

class delegate16_a {
    method val(): int { return 1; }
}

class delegate16_b extends delegate16_a {
    method val(): int { return 2; }
}

component delegate16 {
    field oa1: delegate16_a = new delegate16_a();
    field ob1: delegate16_a = new delegate16_b();
    field oa2: delegate16_a = new delegate16_a();
    field ob2: delegate16_a = new delegate16_b();

    method main(arg: int): bool {

	local af = oa1.val;
	local bf = ob1.val;
	local cf = bar;

	if ( arg == 1 ) return compare(af, f1());
	if ( arg == 2 ) return compare(bf, f2());
	if ( arg == 3 ) return compare(cf, f3());
	if ( arg == 4 ) return compare(af, f4());
	if ( arg == 5 ) return compare(bf, f5());
	if ( arg == 6 ) return compare(cf, f6());
	if ( arg == 7 ) return compare(af, f7());
	if ( arg == 8 ) return compare(bf, f8());
	if ( arg == 9 ) return compare(cf, f9());

	return false;
    }

    method f1(): function(): int { return oa1.val; }
    method f2(): function(): int { return ob1.val; }
    method f3(): function(): int { return bar; }
    method f4(): function(): int { return ob1.val; }
    method f5(): function(): int { return oa1.val; }
    method f6(): function(): int { return null; }
    method f7(): function(): int { return oa2.val; }
    method f8(): function(): int { return ob2.val; }
    method f9(): function(): int { return bar; }

    method compare(f: function(): int, g: function(): int): bool {
	return f == g;
    }

    method bar(): int {
	return 3;
    }
}
