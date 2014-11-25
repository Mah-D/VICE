// @Harness: v2-init
// @Test: order of operators in evaluating bit updates
// @Result: PASS

component raw_order02 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    field data: 32 = 0x6;
    field res_01: 1 = data[first(0)] = second(0b1);

    method first(a: int): int {
        order[pos] = 1;
        pos++;
        return a;
    }

    method second(a: 1): 1 {
        order[pos] = 2;
        pos++;
        return a;
    }
}

/* 
heap {
    record #0:4:raw_order02 {
        field order: int[] = #1:int[2];
        field pos: int = int:2;
        field data: raw.32 = raw.32:0x7;
        field res_01: raw.1 = raw.1:0b1;
    }
    record #1:3:int[2] {
	field 0:int = int:1;
	field 1:int = int:2;
    }
} */
