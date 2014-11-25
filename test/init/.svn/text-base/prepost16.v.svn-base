// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

component prepost16 {
    field foo: int[] = { 1 };
    field bar: int = ++foo[0];
}

/* 
heap {
    record #0:2:prepost16 {
        field foo: int[] = #1:int[1];
        field bar: int = int:2;
    }
    record #1:1:int[1] {
	field 0: int = int:2;
    }
}*/
