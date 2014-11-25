// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

component prepost05 {
    field foo: int = foo++;
}

/* 
heap {
    record #0:1:prepost05 {
        field foo: int = int:0;
    }
}*/
