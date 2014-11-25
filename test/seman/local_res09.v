// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 6:14

class local_res09 {
    
    method testm() {
        switch ( foo ) {
            case ( 0 ) {
            }
        }
    }
}
