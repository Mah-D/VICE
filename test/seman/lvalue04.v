// @Harness: v2-seman
// @Test: Lvalue correctness
// @Result: NotAnLvalue @ 6:5

class lvalue4 {
    
    method testm() {
        2 = 1;
    }
}
