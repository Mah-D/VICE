// @Harness: v2-seman
// @Test: method resolution
// @Result: UnresolvedMember @ 11:10

component method_res08_a {
    
    private method priv() {
    }
}
class method_res08_b {
    
    method testm() {
        method_res08_a.priv();
    }
}
