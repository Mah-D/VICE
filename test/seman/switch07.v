// @Harness: v2-seman
// @Test: switch statements should have non-overlapping values
// @Result: DuplicateCase @ 7:15

class switch07 {
    
    method testm(a: int) {
        switch ( a ) {
            case ( 2, 1 + 1 ) ;
            case ( 0 ) ;
        }
    }
}
