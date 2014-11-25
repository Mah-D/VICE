// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component order09 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    field res_01: bool = op(1, 2);

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
}

/* 
heap {
    record #0:10:order09 {
        field order: int[] = #1:int[2];
        field pos: int = int:2;
        field res_01: bool = bool:false;
    }
    record #1:2:int[2] {
	field 0:int = int:1;
	field 1:int = int:2;
    }
} */
