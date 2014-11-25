// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

component prepost20 {
    field ind: int = 0;
    field foo: int[] = { 1 };
    field bar: int = ++foo[ind++];
}

/* 
heap {
    record #0:3:prepost20 {
	field ind: int = int:1;
        field foo: int[] = #1:int[1];
        field bar: int = int:2;
    }
    record #1:1:int[1] {
	field 0: int = int:2;
    }
}*/
