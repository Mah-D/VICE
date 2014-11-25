// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component default04a {
    field foo: int[] = new int[1];
    field bar: int = foo[0];
}

/* 
heap {
    record #0:2:default04a {
        field foo: int[] = #1:int[1];
        field bar: int = int:0;
    }
    record #1:1:int[1] {
        field 0: int = int:0;
    }
} */
