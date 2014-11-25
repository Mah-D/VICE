// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component order08 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    field res_01: int = first()(second(1));

    method first(): function(int): int {
        order[pos] = 1;
        pos++;
        return add;
    }

    method second(a: int): int {
        order[pos] = 2;
        pos++;
        return a;
    }

    method add(a: int): int { return a + 2; }
}

/* 
heap {
    record #0:10:order08 {
        field order: int[] = #1:int[2];
        field pos: int = int:2;
        field res_01: int = int:3;
    }
    record #1:2:int[2] {
	field 0:int = int:1;
	field 1:int = int:2;
    }
} */
