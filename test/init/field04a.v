// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component field04 {
    field foo: bool = true;
}

/* 
heap {
    record #0:1:field04 {
        field foo: bool = bool:true;
    }
} */
