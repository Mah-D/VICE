// @Harness: v2-exec
// @Test: order of evaluation
// @Result: 0=0, 1=1, 2=2, 3=21, 4=0

component order09 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    method op(a: int, b: int): bool {
	return first(a) == second(b);
    }

    method first(a: int): int {
        order[pos] = 1;
        pos++;
        return a;
    }

    method second(a: int): int {
        order[pos] = 2;
        pos++;
        return a;
    }

    method main(arg: int): int {
        local result = op(1, 2);
	if ( arg == 1 ) return order[0];
	if ( arg == 2 ) return order[1];
	if ( arg == 3 ) return result ? 11 : 21;
	return 0;
    }

}
