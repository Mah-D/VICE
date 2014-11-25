// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component field07 {
    field foo: int[] = { 0, 1 };
}

/* 
heap {
    record #0:1:field07 {
        field foo: int[] = #1:int[2];
    }
    record #1:2:int[2] {
        field 0: int = int:0;
        field 1: int = int:1;
    }
} */
