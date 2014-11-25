// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component order14 {
    field order: int[] = { 0, 0, 0, 0 };
    field pos: int = 0;

    field res_01: int = op(1, 2, 3, 4);

    method op(a: int, b: int, c: int, d: int): int {
	return eval(a, 1) * eval(b, 2) + eval(c, 3) * eval(d, 4);
    }

    method eval(a: int, n: int): int {
        order[pos] = n;
        pos++;
        return a;
    }
}

/* 
heap {
    record #0:10:order14 {
        field order: int[] = #1:int[4];
        field pos: int = int:4;
        field res_01: int = int:14;
    }
    record #1:4:int[4] {
	field 0:int = int:1;
	field 1:int = int:2;
	field 2:int = int:3;
	field 3:int = int:4
    }
} */
