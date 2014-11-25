// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 6:12

class local_res12 {
    
    method testm(a: int) {
        testm(foo);
    }
}
