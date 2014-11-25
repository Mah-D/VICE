// @Harness: v2-exec
// @Test: virtual method invocations
// @Result: 0=0, 1=1, 2=1, 3=1, 4=0, 5=0, 6=0, 7=0, 8=0, 9=1, 10=0

class delegate15_a {
    method val(): int { return 1; }
}

class delegate15_b extends delegate15_a {
    method val(): int { return 2; }
}

component delegate15 {
    field oa1: delegate15_a = new delegate15_a();
    field ob1: delegate15_a = new delegate15_b();
    field oa2: delegate15_a = new delegate15_a();
    field ob2: delegate15_a = new delegate15_b();

    method main(arg: int): bool {

	local af = oa1.val;
	local bf = ob1.val;
	local cf = bar;

	if ( arg == 1 ) return compare(af, oa1.val);
	if ( arg == 2 ) return compare(bf, ob1.val);
	if ( arg == 3 ) return compare(cf, bar);
	if ( arg == 4 ) return compare(af, ob1.val);
	if ( arg == 5 ) return compare(bf, oa1.val);
	if ( arg == 6 ) return compare(cf, null);
	if ( arg == 7 ) return compare(af, oa2.val);
	if ( arg == 8 ) return compare(bf, ob2.val);
	if ( arg == 9 ) return compare(cf, bar);

	return false;
    }

    method compare(f: function(): int, g: function(): int): bool {
	return f == g;
    }

    method bar(): int {
	return 3;
    }
}
