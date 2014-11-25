// @Harness: v2-exec
// @Test: order of evaluation
// @Result: 0=0, 1=1, 2=2, 3=3, 4=0

component order02 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    method f(a: int, b: int): int {
	return a + b;
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
        local result = f(first(1), second(2));
	if ( arg == 1 ) return order[0];
	if ( arg == 2 ) return order[1];
	if ( arg == 3 ) return result;
	return 0;
    }

}
