// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

component cmpassn16 {
    field foo: int[] = { 1 };
    field bar: int = foo[0] -= 9;
}

/* 
heap {
    record #0:2:cmpassn16 {
        field foo: int[] = #1:int[1];
        field bar: int = int:-8;
    }
    record #1:1:int[1] {
	field 0: int = int:-8;
    }
}*/
