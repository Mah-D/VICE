// @Harness: v2-init
// @Test: order of operations in bit manipulation operators
// @Result: PASS

component raw_order01 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    field data: 32 = 0x7;
    field res_01: 1 = first()[second(0)];

    method first(): 32 {
        order[pos] = 1;
        pos++;
        return data;
    }

    method second(a: int): int {
        order[pos] = 2;
        pos++;
        return a;
    }
}

/* 
heap {
    record #0:4:raw_order01 {
        field order: int[] = #1:int[2];
        field pos: int = int:2;
        field data: 32 = raw.32:0x7;
        field res_01: 1 = raw.1:0b1;
    }
    record #1:2:int[2] {
	field 0:int = int:1;
	field 1:int = int:2;
    }
} */
