// @Harness: v2-init
// @Test: pre/post increment options
// @Result: PASS

component cmpassn02 {
    field foo: int = 1;
    field bar: int = foo -= 2;
}

/* 
heap {
    record #0:2:cmpassn02 {
        field foo: int = int:-1;
        field bar: int = int:-1;
    }
}*/
