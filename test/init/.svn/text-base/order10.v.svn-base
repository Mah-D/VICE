// @Harness: v2-init
// @Test: arithmetic operators
// @Result: PASS

component order10 {
    field order: int[] = { 0, 0 };
    field pos: int = 0;

    field res_01: int[][] = new int[first(1)][second(2)];

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
    record #0:10:order10 {
        field order: int[] = #1:int[2];
        field pos: int = int:2;
        field res_01: int = #2:int[][1];
    }
    record #1:2:int[2] {
	field 0:int = int:1;
	field 1:int = int:2;
    }
    record #2:1:int[][1] {
	field 0: int[] = #3:int[2];
    }
    record #3:2:int[2] {
	field 0: int = int:0;
	field 1: int = int:0;
    }
} */
