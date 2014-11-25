// @Harness: v2-seman
// @Test: Lvalue correctness
// @Result: NotAnLvalue @ 7:9

class lvalue2 {
    
    method testm() {
        local foo: int = 0;
        local bar: int = 0;
        foo + bar = 2;
    }
}
