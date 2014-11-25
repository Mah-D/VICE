// @Harness: v2-seman
// @Test: Lvalue correctness
// @Result: NotAnLvalue @ 6:13

class lvalue6 {
    
    method testm() {
        local i: int = testm() = 2;
    }
}
