// @Harness: v2-exec
// @Test: order of evaluation
// @Result: 0=0, 1=1, 2=2, 3=3, 4=0

component order08 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    method first(): function(int): int {
        order[pos] = 1;
        pos++;
        return add;
    }

    method second(a: int): int {
        order[pos] = 2;
        pos++;
        return a;
    }

    method add(a: int): int { return a + 2; }

    method main(arg: int): int {
        local result = first()(second(1));
	if ( arg == 1 ) return order[0];
	if ( arg == 2 ) return order[1];
	if ( arg == 3 ) return result;
	return 0;
    }
}
