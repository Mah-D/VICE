// @Harness: v2-seman
// @Test: field resolution
// @Result: UnresolvedIdentifier @ 11:5

class field_res11_a {
    private field priv: int;
}
class field_res11_b extends field_res11_a {
    
    method testm() {
        priv = 0;
    }
}
