// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 6:5

class local_res15 {
    
    method testm() {
        foo = 2;
        while ( true ) {
            local foo: int;
        }
    }
}
