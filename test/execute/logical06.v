// @Harness: v2-exec
// @Test: logical operators
// @Result: 0=0, 1=7, 2=6, 3=4, 4=4, 5=0

class ander {
    field a: bool; // true if A() evaluated
    field b: bool; // true if B() evaluated
    field R: bool;

    method AND(v1: bool, v2: bool) {
	R = A(v1) and B(v2);
    }

    method A(v: bool): bool {
	a = true;
	return v;
    }

    method B(v: bool): bool {
	b = true;
	return v;
    }
}

component logical06 {
    field a: ander = new ander();

    method main(arg: int): int {
	if ( arg == 1 ) a.AND(true, true);
	if ( arg == 2 ) a.AND(true, false);
	if ( arg == 3 ) a.AND(false, true);
	if ( arg == 4 ) a.AND(false, false);
	return add();
    }

    method add(): int {
	local res = 0;
	if ( a.a ) res += 4;
	if ( a.b ) res += 2;
	if ( a.R ) res += 1;
	return res;
    }

}
