// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component default01a {
    field foo: int;
    field bar: int = foo + 1;
}

/* 
heap {
    record #0:2:default01a {
        field foo: int = int:0;
        field bar: int = int:1;
    }
} */
