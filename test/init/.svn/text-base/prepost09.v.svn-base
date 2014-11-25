// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

class prepost09_obj {
    field foo: int = 1;
    field bar: int = ++foo;
}

component prepost09 {
    field foo: prepost09_obj = new prepost09_obj();
}

/* 
heap {
    record #0:1:prepost09 {
        field foo: prepost09_obj = #1:prepost09_obj;
    }
    record #1:2:prepost09_obj {
        field foo: int = int:2;
	field bar: int = int:2;
    }
}*/
