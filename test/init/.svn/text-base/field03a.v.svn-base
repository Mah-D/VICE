// @Harness: v2-init
// @Test: field initialization
// @Result: PASS

component field03 {
    field foo: int = bar();
    method bar(): int { return 1; }
}

/* 
heap {
    record #0:1:field03 {
        field foo: int = int:1;
    }
} */
