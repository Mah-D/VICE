// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 7:8

class local_res16 {
    
    method testm() {
        {
            foo = 1;
        }
        local foo: int;
    }
}
