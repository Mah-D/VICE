// @Harness: v2-init
// @Test: order of operations in bit manipulation operators
// @Result: PASS

component raw_order03 {
    field order: int[] = { 0, 0, 0 };
    field pos: int = 0;

    field array: 32[] = { 0x7 }; 
    field res_01: 1 = first()[second(0)][third(0)];

    method first(): 32[] {
        order[pos] = 1;
        pos++;
        return array;
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
    record #0:4:raw_order03 {
        field order: int[] = #1:int[3];
        field pos: int = int:3;
        field array: raw.32[] = #2:raw.32[1];
        field res_01: 1 = raw.1:0b1;
    }
    record #1:2:int[3] {
	field 0:int = int:1;
	field 1:int = int:2;
	field 2:int = int:3;
    }
    record #2:1:raw.32[1] {
	field 0:raw.32 = raw.32:0x7;
    }
} */
