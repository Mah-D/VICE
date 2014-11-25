// @Harness: v2-exec
// @Test: order of evaluation
// @Result: 0=0, 1=1, 2=2, 3=7, 4=7, 5=0

component order11 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    field data: int[] = { 7 };

    method first(): int[] {
        order[pos] = 1;
        pos++;
        return data;
    }

    method second(a: int): int {
        order[pos] = 2;
        pos++;
        return a;
    }

    method main(arg: int): int {
        local result = first()[second(0)];
	if ( arg == 1 ) return order[0];
	if ( arg == 2 ) return order[1];
	if ( arg == 3 ) return result;
	if ( arg == 4 ) return data[0];
	return 0;
    }

}
