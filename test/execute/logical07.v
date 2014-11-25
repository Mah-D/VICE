// @Harness: v2-exec
// @Test: logical operators
// @Result: 0=0, 1=5, 2=5, 3=7, 4=6, 5=0

class orer {
    field a: bool; // true if A() evaluated
    field b: bool; // true if B() evaluated
    field R: bool;

    method OR(v1: bool, v2: bool) {
	R = A(v1) or B(v2);
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

component logical07 {
    field a: orer = new orer();

    method main(arg: int): int {
	if ( arg == 1 ) a.OR(true, false);
	if ( arg == 2 ) a.OR(true, true);
	if ( arg == 3 ) a.OR(false, true);
	if ( arg == 4 ) a.OR(false, false);
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
