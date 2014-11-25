// @Harness: v2-seman
// @Test: Lvalue correctness
// @Result: NotAnLvalue @ 7:12

class lvalue9 {
    
    method testm(): int {
        switch ( 0 ) {
            case ( testm() = 2 ) {
            }
        }
        return 0;
    }
}
