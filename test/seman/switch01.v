// @Harness: v2-seman
// @Test: switch statements should have non-overlapping values
// @Result: DuplicateCase @ 8:7

class switch01 {
    
    method testm(a: int) {
        switch ( a ) {
            case ( 0 ) ;
            case ( 0 ) ;
        }
    }
}
