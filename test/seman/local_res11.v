// @Harness: v2-seman
// @Test: global identifier resolution
// @Result: UnresolvedIdentifier @ 7:13

class local_res11 {
    
    method testm(a: int) {
        local b: int = a;
        local i: int = foo + 2;
    }
}
