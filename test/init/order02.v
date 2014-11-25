// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component order02 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    field res_01: int = op(1, 2);

    method op(a: int, b: int): int {
	return f(first(a), second(b));
    }

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
}

/* 
heap {
    record #0:10:order02 {
        field order: int[] = #1:int[2];
        field pos: int = int:2;
        field res_01: int = int:3;
    }
    record #1:2:int[2] {
	field 0:int = int:1;
	field 1:int = int:2;
    }
} */
