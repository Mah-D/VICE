// @Harness: v2-seman
// @Test: switch statements should have compile-time computable values
// @Result: NotComputable @ 8:15

class switch05 {
    method testm(a: int): int {
        switch(a) {
            case(testm(a)) ;
        }
        return 0;
    }
}    
