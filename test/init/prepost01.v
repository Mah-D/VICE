// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

component prepost01 {
    field foo: int = 1;
    field bar: int = foo++;
}

/* 
heap {
    record #0:2:prepost01 {
        field foo: int = int:2;
        field bar: int = int:1;
    }
}*/
