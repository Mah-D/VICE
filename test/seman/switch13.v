// @Harness: v2-seman
// @Test: switch statements should have compile-time computable values
// @Result: UnexpectedException @ 8:20

class switch13 {
    method testm(a: int): int {
        switch(a) {
            case(1 % 0) ;
            default a = 1;
        }
        return 0;
    }
}    
