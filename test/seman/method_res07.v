// @Harness: v2-seman
// @Test: method resolution
// @Result: UnresolvedMember @ 11:10

class method_res07_a {
    
    private method priv() {
    }
}
class method_res07_b extends method_res07_a {
    
    method testm() {
        this.priv();
    }
}
