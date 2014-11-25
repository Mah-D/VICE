// @Harness: v2-seman
// @Test: Lvalue correctness
// @Result: NotAnLvalue @ 6:13

class lvalue10 {
    
    method testm() {
        for ( ; testm() = 0; ) ;
    }
}
