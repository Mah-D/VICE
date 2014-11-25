// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array04 {
    field a_02: int[][] =  { {1, 2}, {-2} };
}

/* 
heap {
    record #0:1:array04 {
        field a_02: int[][] = #1:int[][2];
    }
    record #1:2:int[][2] {
        field 0:int[] = #2:int[2];
        field 1:int[] = #3:int[1];
    }
    record #2:2:int[2] {
        field 0:int = int:1;
        field 1:int = int:2;
    }
    record #3:1:int[1] {
        field 0:int = int:-2;
    }
} */
