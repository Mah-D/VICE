// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 7:12

class local_res10 {
    
    method testm() {
        switch ( 0 ) {
            case ( foo ) {
            }
        }
    }
}
