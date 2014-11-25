// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 6:6

class local_res17 {
    
    method testm() {
        foo = 1;
        {
            local foo: int;
        }
    }
}
