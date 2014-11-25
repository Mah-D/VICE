// @Harness: v2-exec
// @Test: arithmetic operators
// @Result: 0=0, 1=2, 2=3, 3=0, 4=12, 5=12, 6=0

component order15 {
    field order: int[] = { 0, 0, 0 };
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

    method third(a: int): int {
        order[pos] = 3;
        pos++;
        return a;
    }

    method main(arg: int): int {
        local result = data[second(0)] = third(12);
	if ( arg == 1 ) return order[0];
	if ( arg == 2 ) return order[1];
	if ( arg == 3 ) return order[2];
	if ( arg == 4 ) return result;
	if ( arg == 5 ) return data[0];
	return 0;
    }
}
