// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component order06 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    field res_01: int[] = { first(6), second(7) };

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
    record #0:10:order06 {
        field order: int[] = #1:int[2];
        field pos: int = int:2;
        field res_01: int[] = #2:int[2];
    }
    record #1:2:int[2] {
	field 0:int = int:1;
	field 1:int = int:2;
    }
    record #2:2:int[2] {
	field 0:int = int:6;
	field 1:int = int:7;
    }
} */
