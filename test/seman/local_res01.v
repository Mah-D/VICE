// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 6:6

class local_res01 {
    
    method testm() {
        foo = 1;
    }
}
