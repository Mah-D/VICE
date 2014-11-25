// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 7:7

class local_res04 {
    
    method testm() {
        for ( ; ; ) {
            foo = 0;
        }
    }
}
