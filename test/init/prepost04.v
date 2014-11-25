// @Harness: v2-init
// @Test: pre/post increment options
// @Result: PASS

component prepost04 {
    field foo: int = 6;
    field bar: int = --foo;
}

/* 
heap {
    record #0:2:prepost04 {
        field foo: int = int:5;
        field bar: int = int:5;
    }
}*/
