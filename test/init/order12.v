// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component order12 {
    field order: int[] = { 0, 0, 0 };
    field pos: int = 0;

    field data: int[] = { 7 };
    field res_01: int = first()[second(0)] = third(12);

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
}

/* 
heap {
    record #0:10:order12 {
        field order: int[] = #1:int[3];
        field pos: int = int:3;
        field data: int[] = #2:int[1];
        field res_01: int = int:12;
    }
    record #1:3:int[3] {
	field 0:int = int:1;
	field 1:int = int:2;
	field 2:int = int:3;
    }
    record #2:1:int[1] {
	field 0:int = int:12;
    }
} */
