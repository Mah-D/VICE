// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 6:5

class local_res13 {
    
    method testm() {
        foo = 0;
        local foo: int;
    }
}
