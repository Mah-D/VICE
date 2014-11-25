// @Harness: v2-init
// @Test: pre/post increment options
// @Result: PASS

component cmpassn04 {
    field foo: int = 6;
    field bar: int = foo /= 2;
}

/* 
heap {
    record #0:2:cmpassn04 {
        field foo: int = int:3;
        field bar: int = int:3;
    }
}*/
