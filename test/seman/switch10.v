// @Harness: v2-seman
// @Test: switch statements should have compile-time computable values
// @Result: TypeMismatch @ 9:24

class switch10 {
    method testm(a: int): int {
        switch(a) {
            case(0x01) ;
            default a = false;
        }
        return 0;
    }
}    
