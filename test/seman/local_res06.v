// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 6:11

class local_res06 {
    
    method testm() {
        for ( foo = 0; ; ) ;
    }
}
