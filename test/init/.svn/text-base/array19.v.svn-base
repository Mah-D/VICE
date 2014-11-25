// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component array19 {
    field a_01: int[] =  {1, 2, 3};
    field len: int =  a_01.length;
}

/* 
heap {
    record #0:2:array19 {
        field a_01: int[] = #1:int[3];
        field len: int = int:3;
    }
    record #1:3:int[3] {
        field 0:int = int:1;
        field 1:int = int:2;
        field 2:int = int:3;
    }
} */
