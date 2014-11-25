// @Harness: v2-seman
// @Test: Lvalue correctness
// @Result: NotAnLvalue @ 6:5

class lvalue5 {
    
    method testm() {
        this = null;
    }
}
