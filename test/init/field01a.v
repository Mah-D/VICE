// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component field01a {
    field foo: int = 1;
}

/* 
heap {
    record #0:1:field01a {
        field foo: int = int:1;
    }
} */
