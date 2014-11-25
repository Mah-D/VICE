// @Harness: v2-seman
// @Test: switch statements should have compile-time computable values
// @Result: NotComputable @ 7:15

class switch03 {
    
    method testm(a: int) {
        switch ( a ) {
            case ( a ) ;
        }
    }
}
