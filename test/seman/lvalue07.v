// @Harness: v2-seman
// @Test: Lvalue correctness
// @Result: NotAnLvalue @ 6:11

class lvalue7 {
    
    method testm() {
        for ( testm() = 2; ; ) ;
    }
}
