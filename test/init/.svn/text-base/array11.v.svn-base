// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array11 {
    field a_01: int[] = array();
    field a_02: int[] = array();

    method array(): int[] {
	local arr = {1, 2};
        return arr;
    }
}

/* 
heap {
    record #0:2:array11 {
        field a_01: int[] = #1:int[2];
        field a_02: int[] = #2:int[2];
    }
    record #1:2:int[2] {
        field 0:int = int:1;
        field 1:int = int:2;
    }
    record #2:2:int[2] {
        field 0:int = int:1;
        field 1:int = int:2;
    }
} */
