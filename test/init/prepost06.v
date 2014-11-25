// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

component prepost06 {
    field foo: int = ++foo;
}

/* 
heap {
    record #0:1:prepost06 {
        field foo: int = int:1;
    }
}*/
