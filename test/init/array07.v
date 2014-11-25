// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array07 {
    field a_01: int[] =  new int[1];
    field a_02: int[] =  new int[2];
}

/* 
heap {
    record #0:2:array07 {
        field a_01: int[] = #1:int[1];
        field a_02: int[] = #2:int[2];
    }
    record #1:1:int[1] {
        field 0:int = int:0;
    }
    record #2:2:int[2] {
        field 0:int = int:0;
        field 1:int = int:0;
    }
} */
