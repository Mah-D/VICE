// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

component cmpassn19 {
    field ind: int = 0;
    field foo: int[] = { 1 };
    field bar: int = foo[ind++] += 4;
}

/* 
heap {
    record #0:3:cmpassn19 {
	field ind: int = int:1;
        field foo: int[] = #1:int[1];
        field bar: int = int:5;
    }
    record #1:1:int[1] {
	field 0: int = int:5;
    }
}*/
