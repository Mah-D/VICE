// @Harness: v2-exec
// @Test: order of evaluation
// @Result: 0=0, 1=1, 2=2, 3=3, 4=0

class order03_a {
    method foo(a: int): int { return a + 2; }
}

component order03 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;
    field obj: order03_a = new order03_a();

    method first(): order03_a {
        order[pos] = 1;
        pos++;
        return obj;
    }

    method second(a: int): int {
        order[pos] = 2;
        pos++;
        return a;
    }

    method main(arg: int): int {
        local result = first().foo(second(1));
	if ( arg == 1 ) return order[0];
	if ( arg == 2 ) return order[1];
	if ( arg == 3 ) return result;
	return 0;
    }
}
