// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

component cmpassn18 {
    field foo: int[];
    field bar: int = na()[0] -= 18;
    
    // this method should only be called once
    method na(): int[] {
        local arr = { 1 };
	return foo = arr;
    }
}

/* 
heap {
    record #0:2:cmpassn18 {
        field foo: int[] = #1:int[1];
        field bar: int = int:-17;
    }
    record #1:1:int[1] {
	field 0: int = int:-17;
    }
}*/
