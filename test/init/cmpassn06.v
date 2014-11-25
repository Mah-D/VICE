// @Harness: v2-init
// @Test: pre/post increment operations
// @Result: PASS

component cmpassn06 {
    field foo: 16 = 0x4;
    field bar: 16 = foo >>= 1;
}

/* 
heap {
    record #0:2:cmpassn06 {
        field foo: int = raw.16:0x2;
        field bar: int = raw.16:0x2;
    }
}*/
